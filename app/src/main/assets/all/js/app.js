var aasule = new Vue({
    el: '#app',
    data: {
        questions: [],
        errored: false,
        loading: true,
        msg: '',
        nextPage: 1,
        showButton: true,
        trials: 5,
        department: undefined,
        departmentExist: false,
        departments: []
    },
    methods: {
        setDepartment(e) {
            var dept = e.target.innerHTML.trim() || undefined
            this.department = dept
            if (dept) this.fetchPosts()
        },
        fetchPosts() {
            // console.log(this.errored);
            var self = this
            // var postsUrl = 'http://localhost/pq/?req=get-questions&page='+self.nextPage+'&dept='+self.department
            var postsUrl = 'https://nmwrrd.000webhostapp.com/pq/?req=get-questions&page='+self.nextPage+'&dept='+self.department

            axios.get(postsUrl)
                .then(function (response) {
                    // handle success
                    var newQuestion = (response.data.message.questions && typeof response.data.message.questions === 'object')
                      ? response.data.message.questions : [];
                    self.loading = false
                    self.errored = false

                    var concatedArray = (self.questions.length > 0)
                      ? self.questions.concat(newQuestion) : newQuestion;
                    self.questions = concatedArray;
                    self.showButton = (self.questions.length <= Number(response.data.message.questionTotal))
                    // console.log(self.questions)

                    if(self.questions[0] && self.questions[0].id) {
                        self.cachePosts(self.questions)
                        self.departmentExist = true
                    }

                    self.msg = (self.departmentExist) ? '' : response.data.message;
                })
                .catch(function (error) {
                    // handle error
                    self.loading = false
                    self.errored = true
                    self.msg = 'Sorry, couldnt load posts'

                    setTimeout(function() {
                        // switch to cached mode if network still fails
                        self.trials--;
                        self.fetchPosts();
                    }, 50);

                    if(self.trials <= 0) window.location = 'cached.html'
                });
            return;
        },

        isDepartmentExist () {
        },
        getDepartments () {
            // console.log(this.errored);
            var self = this
            // var departmentsUrl = 'http://localhost/pq/?req=get-departments'
            var departmentsUrl = 'https://nmwrrd.000webhostapp.com/pq/?req=get-departments'

            axios.get(departmentsUrl)
                .then(function (response) {
                    // handle success
                    self.departments = (response.data.message && response.data.message.departments && typeof response.data.message.departments === 'object')
                      ? response.data.message.departments : [];
                    self.loading = false
                    // self.errored = false

                    self.msg = (self.departments.length <= 0) ? 'Sorry, no departments exists yet' : ''
                    self.errored = (self.departments.length <= 0)
                })
                .catch(function (error) {
                    // handle error
                    self.loading = false
                    self.errored = true
                    self.msg = 'Something went wrong. Couldnt load departments'

                    setTimeout(function() {
                        // switch to cached mode if network still fails
                        self.trials--;
                        self.fetchPosts();
                    }, 50);

                    if(self.trials <= 0) window.location = 'cached.html'
                });

            return;
        },
        goToNextPage(){
            this.nextPage++;
            this.fetchPosts();
        },
        cachePosts(posts){
            // store posts in local storage
            if (typeof(Storage) === "undefined") return '';
            localStorage.setItem("cachedPosts", JSON.stringify(posts));
            // console.log(localStorage.getItem("cachedPosts"))
        }
    },
    mounted: function () {
        // do the fetching
        // var dept  = window.location.href.split('?d=')[1]
        // this.departmentExist = (dept) ? this.isDepartmentExist(dept) : undefined
        // console.log(this.departmentExist)
        this.getDepartments()
        // this.fetchPosts();
    }
});

Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: 'images/error.jpg',
  loading: 'images/loading.gif',
  attempt: 3
})
var post = new Vue({
    el: '#app',
    data: {
        post: {},
        errored: false,
        loading: true,
        msg: ''
    },
    methods: {
        fetchPost() {
            // console.log(this.errored);
            var id  = window.location.href.split('?p=')[1]
            var self = this
            // var postUrl = 'http://localhost/pq/?req=get-question&id='+id
            var postUrl = 'https://nmwrrd.000webhostapp.com/pq/?req=get-question&id='+id
            
            axios.get(postUrl)
                .then(function (response) {
                    // handle success
                    self.post = (response.data.message && typeof response.data.message === 'object')
                      ? response.data.message : {};
                      // console.log(self.post.post_id)
                    if (self.post['id'] === undefined) {
                        self.errored =  true
                        self.msg = 'Question not found'
                    } else {
                        self.cachePost(id)
                    }

                    self.loading = false
                })
                .catch(function (error) {
                    // handle error
                    if(!self.getFromCached(id)){
                        self.errored = true
                        self.msg = 'Sorry, couldnt load question'
                    }
                    self.loading = false
                });
            return;
        },
        cachePost(id) {
            if (typeof(Storage) === "undefined") return '';
            localStorage.setItem("cachedPost"+id, JSON.stringify(this.post));
        },
        getFromCached(id){
            if (typeof(Storage) === "undefined") return false;
            if (localStorage.getItem('cachedPost'+id) === undefined) return false
            
            var cachedContent = localStorage.getItem('cachedPost'+id);
            cachedContent = (cachedContent !== null && cachedContent.length > 1)
              ? JSON.parse(cachedContent) : {}

            if (cachedContent.id) {
                this.post = cachedContent;
                console.log(this.post)
                return true
            }

            return false;

        }
    },
    mounted: function () {
        // do the fetching
        this.fetchPost();
    }
});

Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: 'images/error.jpg',
  loading: 'images/loading.gif',
  attempt: 3
})
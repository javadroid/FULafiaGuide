let calculations = new Vue({
    el: '#app',
    data: {
        posts: [],
        errored: false,
        loading: true,
        msg: '',
        nextPage: 1,
        showButton: true
    },
    methods: {
        fetchPosts() {
            let allPosts = localStorage.getItem("cachedPosts");
            allPosts = (allPosts !== null && allPosts.length > 1)
              ? JSON.parse(allPosts) : []
            this.posts = (typeof allPosts === 'object')
              ? allPosts : [];
            this.errored = (this.posts.length < 1)
            this.loading = false
            this.msg = 'Unable to find any cached content. Kindly connect to the internet and load content'
        },
        goToNextPage(){
            this.nextPage++;
            this.fetchPosts();
        },
        cachePosts(posts){
            // store posts in local storage
            if (typeof(Storage) === "undefined") return '';
            localStorage.setItem("cachedPosts", JSON.stringify(posts));
        }
    },
    mounted: function () {
        // do the fetching
        this.fetchPosts();
    }
});

Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: 'images/error.jpg',
  loading: 'images/loading.gif',
  attempt: 3
})
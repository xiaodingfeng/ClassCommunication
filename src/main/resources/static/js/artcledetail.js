var vueDetail = new Vue({
    el: '#vueDetail',
    data() {
        return {
            session: null,
            Article: [],
            PageArticle:[],
            currentPage: 1,//当前页号
            pagesize: 10 //每页大小
        }
    },
    computed:{
        page_arrs(){
            var that=this;
            let {currentPage,pagesize} = this
            return that.PageArticle.slice((currentPage-1)*pagesize,currentPage*pagesize)
        },
        current_page(){
            var that=this;
            return that.currentPage
        }
    },
    methods:{
        primaryPage() {
            var that=this;
            that.currentPage = 1
        },
        prePage(){
            var that=this;
            if(that.currentPage === 1){
                return
            }
            that.currentPage = that.currentPage - 1
        },
        nextPage(){
            var that=this;
            if(that.currentPage === Math.ceil(that.PageArticle.length/that.pagesize)){
                return
            }
            that.currentPage = that.currentPage + 1
        },
        lastPage(){
            var that=this;
            that.currentPage = Math.ceil(that.PageArticle.length/that.pagesize)
        }
    },
    mounted:function () {
        var that=this;
        layui.use(['layer','jquery'],function () {
            var layer = layui.layer,
                $=layui.jquery;
            var index = layer.load();
            $.ajax({
                async: false,
                type: 'get',
                dataType: 'json',
                data: {},
                url: "/getMyArticle",
                success: function(res){
                    if(res.status === 0) {
                        that.Article= res.data;
                        layer.close(index);
                    } else {
                        layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                    }
                }, error: function(e){
                    layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                }
            });
        })

    },


});


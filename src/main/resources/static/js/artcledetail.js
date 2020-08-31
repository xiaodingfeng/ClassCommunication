var vueDetail = new Vue({
    el: '#vueDetail',
    data() {
        return {
            Article: [],
            ArticleTop15:[],
            Advertising:[],
            currentPage: 1,//当前页号
            pagesize: 10, //每页大小
            ArticleCount:25,
            ArticlePageCount:0
        }
    },
    computed:{
        page_arrs(){
            var that=this;
            let {currentPage,pagesize} = this
            return that.Article.slice((currentPage-1)*pagesize,currentPage*pagesize)
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
            if(that.currentPage == 1){
                return
            }
            that.currentPage = that.currentPage - 1
        },
        nextPage(){
            var that=this;
            if(that.currentPage === Math.ceil(that.Article.length/that.pagesize)){
                return
            }
            that.currentPage = that.currentPage + 1
        },
        lastPage(){
            var that=this;
            that.currentPage = Math.ceil(that.Article.length/that.pagesize)
        },
        getQueryVariable(variable)
        {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){return pair[1];}
            }
            return(false);
        },
        formatDate(date1) {
            var that=this;
            if (date1==null)
                return "2020-08-08 00:00:00"
            let date=new Date(parseInt(date1));
            var d=date.getFullYear() +
                "-" +
                that.appendZero(date.getMonth() + 1) +
                "-" +
                that.appendZero(date.getDate()) +
                " " +
                that.appendZero(date.getHours()) +
                ":" +
                that.appendZero(date.getMinutes()) +
                ":" +
                that.appendZero(date.getSeconds());
            return d;
        },
        //过滤加0
        appendZero(obj) {
            if (obj < 10) {
                return "0" + obj;
            } else {
                return obj;
            }
        },
        getArtPage(pageNum,pageSize,who) {
            var that = this;
            if (pageNum<1||pageNum>that.ArticlePageCount)
                return false;

            layui.use(['layer', 'jquery'], function () {
                var layer = layui.layer,
                    $ = layui.jquery;
                var index = layer.load();
                if (who==1)
                    that.currentPage=1;
                if (who==2)
                    that.currentPage-=1;
                if (who==3)
                    that.currentPage+=1;
                if (who==4)
                    that.currentPage=pageNum
                let Columns = that.getQueryVariable("id");
                let OrderBy=that.getQueryVariable("OrderBy")

                if (Columns==false)
                    return;
                let url = "/getMyArticleColomnPage?PageNum=" + pageNum + "&PageSize=" + pageSize + "&Columns=" + Columns;


                $.ajax({
                    async: false,
                    type: 'get',
                    dataType: 'json',
                    data: {},
                    url: url,
                    success: function (res) {
                        if (res.status === 0) {
                            that.Article = res.data;
                        } else {
                            layer.msg("数据获取失败，请刷新重试！", {shift: 6});
                        }
                        layer.close(index);
                    }, error: function (e) {
                        layer.msg("数据获取失败，请刷新重试！", {shift: 6});
                        layer.close(index);
                    }
                });

            })
        }
    },
    mounted:function () {
        var that=this;
        layui.use(['layer','jquery'],function () {
            var layer = layui.layer,
                $=layui.jquery;
            var index = layer.load();
            let Columns=that.getQueryVariable("id");
            let Search=that.getQueryVariable("seach");
            let url="/getMyArticle?id="+Columns;
            let url1="/getArticleTop15byColumns?id="+Columns;
            if (Columns==false){
                url="/getMyArticle";
                url1="/getArticleTop15byColumns";
            }
            if (Search!=false)
                url="/getMyArticle?seach="+Search;
            $.ajax({
                async: false,
                type: 'post',
                dataType: 'json',
                data: {},
                url: url,
                success: function(res){
                    if(res.status === 0) {
                        that.Article= res.data;
                        if (res.data1!=null)
                            that.ArticleCount=res.data1;
                            that.ArticlePageCount=Math.ceil(that.ArticleCount/that.pagesize);
                    } else {
                        layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                    }
                    layer.close(index);
                }, error: function(e){
                    layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                    layer.close(index);
                }
            });

            $.ajax({
                async: false,
                type: 'post',
                dataType: 'json',
                data: {},
                url: url1,
                success: function(res){
                    if(res.status === 0) {
                        that.ArticleTop15= res.data;
                    } else {
                        layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                    }
                    layer.close(index);
                }, error: function(e){
                    layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                    layer.close(index);
                }
            });
        })

    },


});


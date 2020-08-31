var artdetail = new Vue({
    el: '#artdetail',
    data() {
        return {
            session:null,
            Article: [],
            Advertising:[],
            ArticleTopNowWeek:[],
            ArticleReply:[]
        }
    },
    computed:{
    },
    methods:{
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
        deleteArt(id){
            layui.use(['layer','jquery'],function () {
                var layer = layui.layer,
                    $ = layui.jquery;
                $.ajax({
                    async: false,
                    type: 'post',
                    dataType: 'json',
                    data: {id:id},
                    url: "/Art/delete",
                    success: function(res){
                        if(res.status == 0) {
                            layer.msg(res.msg,{icon: 6},function(){
                                location.href="/index";
                            });
                        } else {
                            layer.msg(res.msg,{icon: 5},{shift: 6});
                        }
                    }, error: function(e){
                        layer.msg("出错",{icon: 5},{shift: 6});
                    }
                })
            })
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
                url: "/user/info",
                success: function(res){
                    if(res.status == 0) {
                        that.session= res.data;
                    } else {
                        that.session=null;
                    }
                }, error: function(e){
                }
            });



            let id=that.getQueryVariable("id");
            let url="/getArticleDetail?id="+id;
            if (id==false)
                url="/getArticleDetail";
            $.ajax({
                async: false,
                type: 'post',
                dataType: 'json',
                data: {},
                url: url,
                success: function(res){
                    if(res.status === 0) {
                        that.Article= res.data;
                        that.ArticleTopNowWeek=res.data1;
                        that.ArticleReply=res.data2;
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


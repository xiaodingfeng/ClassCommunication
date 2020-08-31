var indexvue = new Vue({
    el: '#indexvue',
    data() {
        return {
            ArticlePlacedTop:[],
            ArticleTop15:[],
            Advertising:[],
            ArticleTopNowWeek:[],
            ArticleTopTime15:[],
            articles:[]
        }
    },
    methods:{
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

        change(who){
            var that=this;
            if (who==1){
                $('#timeA').addClass("layui-this");
                $('#timeB').removeClass("layui-this");
                that.articles=that.ArticleTopTime15;
            }

            else{
                $('#timeB').addClass("layui-this");
                $('#timeA').removeClass("layui-this");
                that.articles=that.ArticleTop15;
            }

        }

    },
    mounted:function () {
        var that=this;
        layui.use(['layer','jquery'],function () {
            var layer = layui.layer,
                $=layui.jquery;
            var index = layer.load();
            let url="/getIndexArticle";
            $.ajax({
                async: false,
                type: 'post',
                dataType: 'json',
                data: {},
                url: url,
                success: function(res){
                    if(res.status === 0) {
                        that.ArticleTopTime15= res.data2;
                        that.ArticleTop15=res.data1;
                        that.ArticlePlacedTop=res.data3;
                        that.ArticleTopNowWeek=res.data4;
                        that.articles=that.ArticleTopTime15;
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
    }
});


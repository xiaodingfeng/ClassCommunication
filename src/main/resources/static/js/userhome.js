var userhomevue = new Vue({
    el: '#userhomevue',
    data() {
        return {
            session4:[],
            articles:[]
        }
    },
    methods:{
        formatDate(date1) {
            var that=this;
            if (date1==null)
                return "2020-08-08"
            let date=new Date(parseInt(date1));
            var d=date.getFullYear() +
                "-" +
                that.appendZero(date.getMonth() + 1) +
                "-" +
                that.appendZero(date.getDate()) ;
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
        getQueryVariable(variable)
        {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){return pair[1];}
            }
            return(false);
        }
    },
    mounted:function () {
        var that = this;
        layui.use(['layer'],function () {
            var layer = layui.layer;
            let id=that.getQueryVariable("userID");
            $.ajax({
                async: false,
                type: 'get',
                dataType: 'json',
                data: {},
                url: ("/user/home?userid="+id),
                success: function (res) {
                    if (res.status == 0) {
                        that.session4 = res.data;
                        that.articles=res.data1;
                    } else {
                        layer.msg("数据获取失败！",{shift: 6});
                    }
                }, error: function (e) {
                    layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                }
            });
        })
    }
});
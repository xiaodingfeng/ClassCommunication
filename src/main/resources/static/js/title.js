var titlevue = new Vue({
    el: '#titlevue',
    data() {
        return {
            session2:null,
            Colomn:[]
        }
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
        generateClassName (id) {
            var that=this;
            if (that.getQueryVariable("id")==id)
            {
                return "layui-this";
            }

            else
                return '';
        }
    },
    mounted:function () {
        var that=this;
            $.ajax({
                async: false,
                type: 'get',
                dataType: 'json',
                data: {},
                url: "/getMyArticleColomn",
                success: function(res){
                    if(res.status == 0) {
                        that.Colomn= res.data;
                    } else {
                    }
                }, error: function(e){
                }
            });
        $.ajax({
            async: false,
            type: 'get',
            dataType: 'json',
            data: {},
            url: "/user/info",
            success: function(res){
                if(res.status == 0) {
                    that.session2= res.data;
                } else {
                }
            }, error: function(e){
            }
        });
    }


});


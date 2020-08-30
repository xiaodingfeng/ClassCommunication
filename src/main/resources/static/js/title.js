var titlevue = new Vue({
    el: '#titlevue',
    data() {
        return {
            Colomn:[]
        }
    },
    methods:{
        changepage:function (id) {
            top.location=('\'/jie/indexhtml?'+"id="+id);
        }
    },
    mounted:function () {
        var that=this;
            $.ajax({
                async: true,
                type: 'get',
                dataType: 'json',
                data: {},
                url: "/getMyArticleColomn",
                success: function(res){
                    if(res.status === 0) {
                        that.Colomn= res.data;
                        $('#pageChanges').setAttribute("display","inline")
                    } else {
                    }
                }, error: function(e){
                }
            });

    }


});


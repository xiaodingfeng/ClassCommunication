var uservue = new Vue({
    el: '#uservue',
    data() {
        return {
            session:[]
        }
    },
    mounted:function () {
        var that=this;
        $('#uservue').css('display','none');
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
                    that.session= null;
                }
                $('#uservue').css('display','block');
            }, error: function(e){
                that.session= null;
                $('#uservue').css('display','block');
            }
        });
    }
});
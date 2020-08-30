
var vm = new Vue({
    el: '#session',
    data: {
        session:null,
    },
    mounted:function () {
        this.session=sessionStorage.getItem("LoginUser");
    }

})
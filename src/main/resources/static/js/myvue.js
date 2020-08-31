
var vm = new Vue({
    el: '#vue',
    data() {
        return {
            timer: "",//定义一个定时器的变量
            currentTime: new Date().getFullYear() +
                "-" +
                this.appendZero(new Date().getMonth() + 1) +
                "-" +
                this.appendZero(new Date().getDate()) +
                " " +
                this.appendZero(new Date().getHours()) +
                ":" +
                this.appendZero(new Date().getMinutes()) +
                ": " +
                this.appendZero(new Date().getSeconds()), // 获取当前时间
        }
    },
    computed:{
        returnTime:function () {
            return this.currentTime;
        }
    },
    methods:{
        //过滤加0
        appendZero(obj) {
            if (obj < 10) {
                return "0" + obj;
            } else {
                return obj;
            }
        }
    },
    mounted:function () {
        var that=this;
        this.timer = setInterval(function() {
            that.currentTime = //修改数据date
                new Date().getFullYear() +
                "-" +
                that.appendZero(new Date().getMonth() + 1) +
                "-" +
                that.appendZero(new Date().getDate()) +
                " " +
                that.appendZero(new Date().getHours()) +
                ":" +
                that.appendZero(new Date().getMinutes()) +
                ":" +
                that.appendZero(new Date().getSeconds());
        }, 1000);
    },
    beforeDestroy() {
        if (this.timer) {
            clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
        }
    }

})


/**
 * 邮箱验证表达式
 */
window.validateEmail =function (email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

//------------------------------------------------
/**
 * 倒计时
 */
//60s倒计时实现逻辑
var countdown = 60;
window.setTime = function(obj) {
    try{
        if (countdown === 0) {
            obj.removeAttr("disabled");
            obj.text("点击获取验证码");
            countdown = 60;//60秒过后button上的文字初始化,计时器初始化;
            return;
        } else {
            obj.attr("disabled", true);
            obj.text("(" + countdown + "s)后重新发送");
            countdown--;
        }

    }catch (e) {
        // alert(e)
    }
    setTimeout(function () {
        setTime(obj)
    }, 1000) //每1000毫秒执行一次
}
/**
 * 验证码
 */
window.draw=function (show_num) {
    var canvas_width=$('#canvas').width();
    var canvas_height=$('#canvas').height();
    var canvas = document.getElementById("canvas");//获取到canvas的对象，演员
    var context = canvas.getContext("2d");//获取到canvas画图的环境，演员表演的舞台
    canvas.width = canvas_width;
    canvas.height = canvas_height;
    var sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
    var aCode = sCode.split(",");
    var aLength = aCode.length;//获取到数组的长度

    for (var i = 0; i <= 3; i++) {
        var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
        var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
        var txt = aCode[j];//得到随机的一个内容
        show_num[i] = txt.toLowerCase();
        var x = 10 + i * 20;//文字在canvas上的x坐标
        var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
        context.font = "bold 23px 微软雅黑";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
    }
    for (var i = 0; i <= 5; i++) { //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.stroke();
    }
    for (var i = 0; i <= 30; i++) { //验证码上显示小点
        context.strokeStyle = randomColor();
        context.beginPath();
        var x = Math.random() * canvas_width;
        var y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
    }
}
/**
 * 随机颜色
 */
window.randomColor=function ()
{//得到随机的颜色值
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}
/**
 * layui
 */
layui.use(['form','jquery','util'], function(){
    var form = layui.form
        ,layer = layui.layer;
    var $ = layui.jquery;
    var util=layui.util;

    var show_num = [];
    try{
        draw(show_num);
    }catch (e) {

    }

    $("#canvas").on('click',function(){
        draw(show_num);
    })
    var session1=null;
    $.ajax({
        async: false,
        type: 'get',
        dataType: 'json',
        data: {},
        url: "/user/info",
        success: function(res){
            if(res.status == 0) {
                session1= res.data;
            } else {
            }
        }, error: function(e){
        }
    });
    //监听提交
    form.on('submit(*)', function(data){
        var url = $(data.form).attr('action'),type=$(data.form).attr('method'), button = $(data.elem);
        if (url==="/user/register")
            if($('#L_pass').val()!==$('#L_repass').val()){
                layer.msg("密码不一致！",{shift: 6});
                return false;
            }
        if (url==="/user/login"||url==="/user/forget"){
            if (!validateEmail($('#L_email').val())){
                layer.msg("邮箱格式错误！",{shift: 6});
                return false;
            }
            var val = $("#L_vercode").val().toLowerCase();
            var num = show_num.join("");
            if (val!==num){
                layer.msg("验证码不正确！！",{shift: 6});
                draw(show_num);
                return false;
            }
        }
        if(url==="/insertActicle"){
            if (session1==null){
                layer.msg("未登录！",{shift: 6});
                return false;
            }
            url+=("?LoginUser="+session1.email);
            var val = $("#L_vercode").val().toLowerCase();
            var num = show_num.join("");
            if (val!==num){
                layer.msg("验证码不正确！！",{shift: 6});
                $("#L_vercode").val("")
                draw(show_num);
                return false;
            }
        }
        if(url==="/jie/reply") {
            if (session1 == null) {
                layer.msg("未登录！", {shift: 6});
                return false;
            }
            url+=("?LoginUser="+session1.email);
            if ($('#contentEd').val()==""||$('#contentEd').val()==null){
                layer.msg("请填写内容！", {shift: 6});
                return false;
            }
        }
        try {
            var index = layer.load();
            $.ajax({
                async: false,
                type: type,
                dataType: 'json',
                data: data.field,
                url: url,
                success: function(res){
                    if(res.status === 0) {
                        layer.msg(res.msg,{icon: 6},function(){
                            if (res.action=="/")
                                location.href="/index";
                            else if(res.action=="/this"){
                                location.reload();
                            }
                        });
                    } else {
                        layer.msg(res.msg,{icon: 5},{shift: 6});
                    }
                    layer.close(index);
                }, error: function(e){
                    layer.close(index);
                    layer.msg('请求异常，请重试', {shift: 6},{icon: 5});
                }
            });
        }catch (e) {
            layer.msg(e, {shift: 6},{icon: 5});
        }
        return false;
    });
    //搜索
    $('.fly-search').on('click', function(){
        layer.open({
            type: 1
            ,title: false
            ,closeBtn: false
            //,shade: [0.1, '#fff']
            ,shadeClose: true
            ,maxWidth: 10000
            ,skin: 'fly-layer-search'
            ,content: ['<form action="/jie/indexhtml?">'
                ,'<input autocomplete="off" placeholder="搜索内容，回车跳转" type="text" name="seach">'
                ,'</form>'].join('')
            ,success: function(layero){
                var input = layero.find('input');
                input.focus();

                layero.find('form').submit(function(){
                    var val = input.val();
                    if(val.replace(/\s/g, '') === ''){
                        return false;
                    }
                    input.val(input.val());
                });
            }
        })
    });


    /*锚点动效*/
    $('a[href*=#],area[href*=#]').click(function() {
        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
            var $target = $(this.hash);
            $target = $target.length && $target || $($.parseHTML('[name=' + this.hash.slice(1) + ']'));
            if ($target.length) {
                var targetOffset = $target.offset().top;
                $('html,body').animate({
                        scrollTop: targetOffset
                    },
                    250);
                return false;
            }
        }
    });
    /**
     * 属性事件
     */
    $("#CodeEmail").on('click', function(){
        var email=$("#L_email").val();
        if (!validateEmail(email)){
            layer.msg('请正确填写邮箱！', {shift: 6});
            return false;
        }
        $.ajax({
            type: 'post',
            dataType: 'json',
            data: {email:email},
            url: "/user/Verification",
            success: function(res){
                if(res.status === 0) {

                } else {
                    layer.msg(res.data, {shift: 6});
                }
            }, error: function(e){
                layer.msg('请求异常，请重试', {shift: 6});
            }
        });
        setTime($("#CodeEmail"));
    });
    //固定Bar
    util.fixbar({
        bar1: '&#xe642;'
        ,bgcolor: '#009688'
        ,click: function(type){
            if(type === 'bar1'){
                location.href = '/jie/add';
            }
        }
    });
});
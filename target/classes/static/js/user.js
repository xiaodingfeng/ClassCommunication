var uservue1 = new Vue({
  el: '#uservue1',
  data() {
    return {
      session3:[],
      articles:[]
    }
  },
  methods: {
    formatDate(date1) {
      var that = this;
      if (date1 == null)
        return "2020-08-08"
      let date = new Date(parseInt(date1));
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
    }
  },
  mounted:function () {
    var that=this;
    $.ajax({
      async: true,
      type: 'get',
      dataType: 'json',
      data: {},
      url: "/user/info",
      success: function(res){
        if(res.status == 0) {
          that.session3= res.data;
          that.articles=res.data1;
        } else {
        }
      }, error: function(e){
      }
    });
  }
});

layui.use(['element','upload'], function(){
  var $ = layui.jquery;
  var layer = layui.layer;
  var element = layui.element;
  var upload = layui.upload;
//上传图片
  upload.render({
    elem: '#upload-imguser'
    , url: '/user/upload/'
    , size: 3*1024
    ,before: function(){
      $('.avatar-add').find('.loading').show();
    }
    , done: function (res) {
      if (res.errno == 0) {
        layer.msg("成功", {icon: 6});
        $("#userimage").attr('src',res.data);
        $("#userimage1").attr('src',res.data);
      } else {
        layer.msg(res.msg, {icon: 5});
      }
      $('.avatar-add').find('.loading').hide();
    }
    , error: function () {
      $('.avatar-add').find('.loading').hide();
    }
  });
  //显示当前tab
  if(location.hash){
    element.tabChange('user', location.hash.replace(/^#/, ''));
  }

  element.on('tab(user)', function(){
    var othis = $(this), layid = othis.attr('lay-id');
    if(layid){
      location.hash = layid;
    }
  });

});
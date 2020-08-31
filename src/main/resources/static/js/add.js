var addvue = new Vue({
    el: '#addvue',
    data() {
        return {
            Article:[]
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
        }
    },
    mounted:function () {

        var that = this;
        layui.use(['layer'],function () {
            var layer = layui.layer;
            let id=that.getQueryVariable("id");
            let url=""
            var E = window.wangEditor
            var editor = new E('#editor')
            //开启debug模式
            editor.customConfig.debug = true;
            // 自定义字体
            editor.customConfig.fontNames = [
                '宋体',
                '微软雅黑',
                'Arial',
                'Tahoma',
                'Verdana'
            ]
            var $contentEd = $('#contentEd')
            editor.customConfig.onchange = function () {
                // 监控变化，同步更新到 textarea
                $contentEd.val(editor.txt.html())
            }
            // 关闭粘贴内容中的样式
            editor.customConfig.pasteFilterStyle = false
            editor.customConfig.zIndex = 100
            // 忽略粘贴内容中的图片
            editor.customConfig.pasteIgnoreImg = true
            // 使用 base64 保存图片
            //editor.customConfig.uploadImgShowBase64 = true
            // 上传图片到服务器
            editor.customConfig.uploadFileName = 'file'; //设置文件上传的参数名称
            editor.customConfig.uploadImgServer = '/api/upload/'; //设置上传文件的服务器路径
            editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024; // 将图片大小限制为 3M
            editor.customConfig.uploadImgMaxLength = 5;
            editor.customConfig.uploadImgHooks = {

                success: function (xhr, editor, result) {
                    // 图片上传并返回结果，图片插入成功之后触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
                },
                fail: function (xhr, editor, result) {
                    // 图片上传并返回结果，但图片插入错误时触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
                },
                error: function (xhr, editor) {
                    // 图片上传出错时触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
                }

            }

            editor.create()
            if (id!=false){
                url="/getArticleDetail?id="+id;
                $.ajax({
                    async: false,
                    type: 'post',
                    dataType: 'json',
                    data: {},
                    url: url,
                    success: function (res) {
                        if (res.status == 0) {
                            that.Article = res.data;
                            editor.txt.html(that.Article.content)
                        } else {
                            layer.msg("数据获取失败！",{shift: 6});
                        }
                    }, error: function (e) {
                        layer.msg("数据获取失败，请刷新重试！",{shift: 6});
                    }
                });
            }
        })
    }
});
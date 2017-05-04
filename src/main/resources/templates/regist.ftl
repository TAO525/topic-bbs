<!DOCTYPE html>
<html>
<head>
    <title>301Topic</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="一起发言吧">
    <link rel="shortcut icon" href="${ctxPath}/static/images/favicon.ico">
    <!-- 引入 Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/regist.css" rel="stylesheet">

    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>
<body>


<div class="row">
    <div class="col-sm-6 col-sm-offset-3">
        <form class="form-horizontal m-t" id="signupForm" action="${ctxPath}/bbs/user/doRegister" method="POST" onsubmit="return ajaxSubmit(this)">
            <div class="alert alert-danger alert-dismissable" id="tip"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">用户名：</label>
                <div class="col-sm-8">
                    <input id="username" name="userName" class="form-control" type="text" aria-required="true" aria-invalid="true" value="" class="error">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">密码：</label>
                <div class="col-sm-8">
                    <input id="password" name="password" class="form-control" type="text" aria-required="true" aria-invalid="true" class="error" value="">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">E-mail：</label>
                <div class="col-sm-8">
                    <input id="email" name="email" class="form-control" type="email" value=>
                </div>
            </div>
           <#-- <div class="form-group">
                <label class="col-sm-3 control-label">公司：</label>
                <div class="col-sm-8">
                    <input id="corp" name="corp" class="form-control" type="text" aria-required="true" aria-invalid="false" class="valid" value=>
                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>留下你的公司名字，以方便beetl统计使用者，除非被黑客攻击，网站不会在任何情况下泄露你个人信息</span>
                </div>
            </div>-->
            <div class="form-group">
                <label class="col-sm-3 control-label">验证码：</label>
                <div class="col-sm-8" style="position:relative;">
                    <input id="code" name="code" class="form-control" type="text" aria-required="true" aria-invalid="false" class="valid">
                    <img src="${ctxPath}/bbs/common/authImage" onclick="this.src='${ctxPath}/bbs/common/authImage?_='+Date.now()" title="点击刷新验证码" style="position:absolute;height:32px;top:1px;right:16px;cursor:pointer;border-radius:0 4px 4px 0">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-8 col-sm-offset-3">
                    <button class="btn btn-primary" type="submit">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    //全局表单ajax提交(onsubmit="return ajaxSubmit(this,reload)")
    function ajaxSubmit(form,reload){
        form = $(form);
        $.post(form.attr('action'),form.serialize(),function(json,status){
            <#--json.err?$.alert(json.msg):reload?location.reload():location.replace('${ctxPath}'+json.msg);-->
            if(json.err){
                $('#tip').show().empty().append(json.msg);
            }else{
                reload?location.reload():location.replace('${ctxPath}'+json.msg);
            }
        })
        return false;
    }
</script>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <title>301Topic</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="一起发言吧">
    <link rel="shortcut icon" href="${ctxPath}/static/images/favicon.ico">
    <!-- 引入 Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/signin.css" rel="stylesheet">

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

<div class="container">

    <form class="form-signin" action="${ctxPath}/bbs/user/login" onsubmit="return ajaxSubmit(this)">
        <h2 class="form-signin-heading">欢迎登录</h2>
        <div class="alert alert-danger alert-dismissable" id="tip"></div>

        <label for="inputEmail" class="sr-only">用户名</label>
        <input type="text" id="inputEmail" name="userName" class="form-control" placeholder="用户名" required autofocus>

        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="密码" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="true" name="remember" checked="checked"> 记住我
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
    </form>

</div>
<#include "common/bottom.ftl">
<script>
    //全局表单ajax提交(onsubmit="return ajaxSubmit(this,reload)")
    function ajaxSubmit(form,reload){
        form = $(form);
        $.post(form.attr('action'),form.serialize(),function(json,status){
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
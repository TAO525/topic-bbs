<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap 模板</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <h2 class="form-signin-heading">Please sign in</h2>
        <div class="alert alert-danger alert-dismissable" id="tip"></div>

        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="text" id="inputEmail" name="userName" class="form-control" placeholder="Email address" required autofocus>

        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

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
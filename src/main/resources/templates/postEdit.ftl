<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap 模板</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/js/simditor/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/js/simditor/simditor.min.css" />
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/plugin/layer.js"></script>
    <script src="//cdn.bootcss.com/marked/0.3.6/marked.min.js"></script>
    <script src="//cdn.bootcss.com/to-markdown/3.0.3/to-markdown.min.js"></script>
    <script type="text/javascript" src="/js/simditor/simditor.min.js"></script>
    <script type="text/javascript" src="/js/post.js"></script>
</head>
<body>

<#include "common/nav.ftl">

<form action="${ctxPath}/bbs/admin/post/update" method="post" onsubmit="return ajaxSubmit(this)">
    <input type="hidden" name="id" value="${post.id}"/>
    <h3>主题：${topic.content}</h3>
    <div class="form-group">
        <textarea id="postContent" name="content">${post.content}</textarea>
    </div>
    <div class="btn-group pull-right">
        <button class="btn btn-sm btn-primary submit-btn"  type="submit"><i class="fa fa-check"></i> 提交</button>
        <button class="btn btn-sm btn-default preview-btn" onclick="preview()" type="button"><i class="fa fa-eye"></i> 预览</button>
    </div>
</form>


</body>
</html>
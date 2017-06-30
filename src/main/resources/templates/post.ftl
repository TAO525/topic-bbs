<!DOCTYPE html>
<html>
<head>
    <title>301Topic</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="一起发言吧">
    <link rel="shortcut icon" href="${ctxPath}/static/images/favicon.ico">
    <!-- 引入 Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/js/simditor/simditor.css" />
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
    <script src="/js/main.js"></script>
</head>
<body>

<#include "common/nav.ftl">

<form action="${ctxPath}/bbs/topic/save" method="post" id="post-form" onsubmit="return ajaxSubmit(this)">
    <fieldset>
        <legend>发表主题</legend>
        <div class="form-group">
            <div class="row">
                <div class="col-sm-4">
                    <select class="form-control" name="moduleId">
                        <#list moduleList as moudle>
                        <option value="${moudle.id}">${moudle.name} </option>
                        </#list>
                    </select>
                </div>
                <div class="col-sm-8">
                    <input type="text" class="form-control " name="title" placeholder="请输入主题标题">
                </div>
            </div>
        </div>
        <div class="form-group">
            <textarea id="postContent" name="postContent" class="form-control"></textarea>
        </div>
        <div class="btn-group pull-right">
            <button class="btn btn-sm btn-primary submit-btn" title="Send" type="submit"><i class="fa fa-check"></i> 提交</button>
            <button class="btn btn-sm btn-default preview-btn" onclick="preview()" type="button"><i class="fa fa-eye"></i> 预览</button>
        </div>
    </fieldset>
</form>


</body>
</html>
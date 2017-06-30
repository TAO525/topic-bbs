<!DOCTYPE html>
<html>
<head>
    <title>301Topic</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="一起发言吧">
    <link rel="shortcut icon" href="${ctxPath}/static/images/favicon.ico">
    <!-- 引入 Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/searchIndex.css" rel="stylesheet">

    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/plugin/layer.js"></script>
    <script src="/js/main.js"></script>
</head>
<body>


<#include "common/nav.ftl">

<h5><span class="result">${pagename!} </span>的搜索结果：<span class="result">${resultnum!}</span> 条</h5>
<hr/>
<div id="searcher-list">
    <#list searcherPage.content! as searcher>
        <div class="searcher">
            <div>匹配度：<small>#{searcher.score!;M2}</small></div>
            <a href="${ctxPath}/bbs/topic/${searcher.topicId!}-1.html">
            ${searcher.content!}
            </a>
        </div>
    </#list>
</div>

<@paginator.page query= searcherPage pageUrl="/bbs/index/" pageUrlParameter="?keyword=${keyword!}"/>

</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <title>301Topic</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="一起发言吧">
    <link rel="shortcut icon" href="${ctxPath}/static/images/favicon.ico">
    <!-- 引入 Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css" />
    <link href="/css/index.css" rel="stylesheet">

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
    <script src="/js/baidu.js"></script>
</head>
<body>
<#include "common/background.ftl">
<#include "common/nav.ftl">

<h4>${pagename!}</h4>
<div id="post-list">
        <#list topics.content as topic>
            <div class="topic">
                <div class="div1">
                    <div>${topic.postCount}</div>
                    <div>回答</div>
                </div>
                <div class="div2">
                    <div>${topic.pv}</div>
                    <div>浏览</div>
                </div>
                <div class="div3">
                    <div>
                        <#if (topic.isUp > 0 )>
                            <span class="label label-danger">重要</span>
                        </#if>
                        <#if (topic.isNice > 0 )>
                            <span class="label label-warning ">精品</span>
                        </#if>
                        <a href="javascript:;">
                            ${topic.bbsUser.userName}
                        </a>
                        &nbsp;&nbsp;${printTime(topic.createTime)}</div>
                    <div><a href="${ctxPath}/bbs/topic/${topic.id}-1.html">${topic.content}</a>&nbsp;&nbsp;<a href="${ctxPath}/bbs/topic/module/${topic.bbsModule.id}-1.html">${topic.bbsModule.name}</a></div>
                </div>
                <#if isAdmin?? && isAdmin>
                    <div class="div4" data-topic=${topic.id}>
                        <a href="javascript:;"  class="btn btn-default btn-xs nice-btn">精华</a>
                        <a href="javascript:;"  class="btn btn-default btn-xs top-btn">置顶</a>
                        <a href="javascript:;"  class="btn btn-default btn-xs del-btn">删除</a>
                    </div>
                </#if>
            </div>
        </#list>

</div>
<#if pageUrl??>
    <@paginator.page query= topics pageUrl="${pageUrl}" pageUrlParameter=""/>
    <#else >
    <@paginator.page query= topics pageUrl="/bbs/index/" pageUrlParameter=""/>
</#if>

<#include "common/bottom.ftl">
</body>
</html>
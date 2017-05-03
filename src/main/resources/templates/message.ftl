<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap 模板</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css" />
    <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
    <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/plugin/layer.js"></script>
</head>
<body>

<#include "common/nav.ftl">

<h3>我的消息</h3>
<div class="well well-sm">
    <table class="table table-hover table-striped" id="post-list">
        <thead>
        <tr>
            <th></th>
            <th style="width:40%">标题</th>
            <th>浏览</th>
            <th>回复</th>
            <th>日期</th>
            <th >管理</th>

        </tr>
        </thead>
        <tbody>
        <#list list as topic>
        <tr data-topic="${topic.id}">
            <td>
                <#if topic.isUp gt 0>
                    <span class="label label-danger">重要</span>
                </#if>
                <#if topic.isNice gt 0>
                    <span class="label label-warning ">精品</span>
                </#if>
            </td>

            <td><a href="${ctxPath}/bbs/my/${topic.id}.html">${topic.content!}</a></td>
            <td><span >${topic.pv!}</span></td>
            <td><span class="label label-warning ">${topic.postCount!}</span></td>
            <td class="text-left ">${printTime(topic.createTime)}</td>

            <td class="text-left mail-date">
                <div class="btn-group" id="admin">
                    <a href="javascript:;" onclick="delMessage('${topic.id!}')" class="btn btn-default btn-xs read-btn">已读</a>
                </div>
            </td>

        </tr>
        </#list>
        </tbody>
    </table>
</div>
<script>
    function delMessage(topicId) {
        $.post('/bbs/admin/message/update',
                {topicId:topicId},
                function(json){
                    json.err||location.reload();

            })
    }
</script>


</body>
</html>
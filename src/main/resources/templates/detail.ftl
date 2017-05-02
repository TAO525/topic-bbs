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
   <#-- <script type="text/javascript" src="/js/marked.min.js"></script>
    <script type="text/javascript" src="/js/to-markdown.min.js"></script>-->
    <script src="//cdn.bootcss.com/marked/0.3.6/marked.min.js"></script>
    <script src="//cdn.bootcss.com/to-markdown/3.0.3/to-markdown.min.js"></script>
    <script type="text/javascript" src="/js/simditor/simditor.min.js"></script>
    <script type="text/javascript" src="/js/post.js"></script>
    <style>.panel-body img{max-width:100% !important;height:auto !important}</style>
</head>
<body>

<#include "common/nav.ftl">

<h3>主题：${topic.content}</h3>
<#list posts as post>
    <div class="panel panel-default" data-post="${post.id}">
        <div class="panel-heading">
            <span class="label label-primary">${post.bbsUser.userName}</span>
            <span class="label label-default">${printTime(post.createTime)}</span>
            <#if post_index == 0>
            <span class="label label-default">${topic.pv}</span>
            </#if>
            <#if user??>
                <div class="btn-group btn-group-xs pull-right">
                    <#if (isAdmin) || (user.id == post.bbsUser.id) >
                        <a class="btn btn-default" href="${ctxPath}/bbs/admin/post/edit/${post.id}.html"><i class="fa fa-edit"></i> 编辑</a>
                        <a class="btn btn-default" href="javascript:;" onclick="deletePost(this)"><i class="fa fa-trash"></i> 删除</a>
                    </#if>
                    <a class="btn btn-default" href="javascript:;" onclick="showReplyDialog('${post.id!}','${topic.id!}')"><i class="fa fa-reply"></i> 回复</a>
                </div>
            </#if>
        </div>
        <div class="panel-body">${post.content}</div>
        <#if post.replys?size != 0>
            <ul class="list-group">
                <#list post.replys as reply>
                    <li class="list-group-item">
                        <#--@include("/common/replyItem.html",{"reply":reply}){}-->
                        <span class="text-info">${reply.bbsUser.userName}</span><span class="text-muted">：${reply.content}（${printTime(reply.createTime)}）</span>
                    </li>
                </#list>
            </ul>
        </#if>
    </div>
</#list>

<form id="postForm" action="${ctxPath}/bbs/post/save" method="POST"  onsubmit="return ajaxSubmit(this,'reload')">
    <input type="hidden" name="topicId" value="${topic.id}">
    <div class="form-group">
        <textarea id="postContent" name="content"></textarea>
    </div>
    <button type="submit" class="btn btn-sm btn-primary pull-right"><i class="fa fa-check"></i> 提 交</button>
</form>

<!-- 回复弹层 -->
<div class="modal fade" id="reply-dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                <form class="form-search" action="${ctxPath}/bbs/reply/save" onsubmit="return ajaxSubmit(this,'reload')" >
                    <input type="hidden" name="postId" value="${(post.id)!}"/>
                    <input type="hidden" name="topicId" value="${(topic.id)!}"/>
                    <div class="form-group">
                        <textarea placeholder="请输入回复内容" class="form-control" name="content" style="height:100px;resize:none;" maxlength="100"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">确定提交</button>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
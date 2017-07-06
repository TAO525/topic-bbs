<!-- 页面头部 -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/bbs/index">
                301-Topic
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${ctxPath}/bbs/index/1.html">首页综合</a></li>
            <li class="dropdown">
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">板块 <span class="caret"></span></a>
                 <ul class="dropdown-menu" role="menu">
                     <#list moduleList! as module>
                        <li><a href="${ctxPath}/bbs/topic/module/${module.id!}-1.html">${module.name!} </a></li>
                     </#list>
                 </ul>
            </li>
            </ul>
            <form class="navbar-form navbar-left">
                <div class="form-group">
                    <input type="text" class="form-control" id="keyword" name="keyword" placeholder="关键字搜索">
                </div>
                <button type="button" class="btn btn-default" id="serach-btn">搜索</button>
            </form>

            <ul class="nav navbar-nav navbar-right">
            <#if !user??>
                <li><a href="${ctxPath}/bbs/user/login.html" data-toggle="modal"><i class="fa fa-sign-in"></i> 登录</a></li>
                <li><a href="${ctxPath}/bbs/user/regist.html" data-toggle="modal"><i class="fa fa-user-plus"></i> 注册</a></li>
            </#if>
            <#if user??>
                <li><a href="/bbs/topic/add.html"><i class="fa fa-plus"></i> 发帖</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user-circle-o"></i> ${user.userName!} <#--${mcount > 0?'<span class="badge mcount">'+mcount+'</span> '}-->
                        <#if msgCount gt 0 >
                            <span class="badge mcount">${msgCount}</span>
                        </#if>
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="javascript:;">你已有${user.score!}积分</a></li>
                        <li><a href="javascript:;">等级${level(user.level!)}</a></li>
                        <li class="divider"></li>
                        <li><a href="${ctxPath}/bbs/myMessage.html">给我的回复</a></li>
                        <#if isAdmin>
                            <li><a href="javascript:;" onclick="$.post('${ctxPath}/bbs/admin/clearModuleList',function(){location.reload()})">更新模板</a></li>
                        </#if>
                    </ul>
                </li>
                <li><a href="javascript:;" onclick="$.post('${ctxPath}/bbs/user/logout',function(){location.reload()})"><i class="fa fa-sign-out"></i> 注销</a></li>
            </#if>

            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
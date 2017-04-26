<#assign ctxPath = "${request.contextPath}"/>
<#assign ftl_version = "1.1.0"/>

<!-- 导入自定义ftl 用于分页-->
<#import "paginator.ftl" as paginator/>

<!-- 自定义函数 -->
<#assign level = "com.wayne.common.LevelMethod"?new()/>

<!-- 是否是admin用户 -->
<#if (user.id)?? && (user.id == 1) >
        <#assign isAdmin = true/>
    <#else>
        <#assign isAdmin = false/>
</#if>
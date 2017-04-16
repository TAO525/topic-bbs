<#-- 参数说明：query 封装的分页对象-->
<#-- 参数说明：pageNo当前的页码，totalPage总页数， showPages显示的页码个数，callFunName回调方法名（需在js中自己定义）-->
<#macro page pageNo totalPage showPages callFunName>
<div class="page_list clearfix">
    <#if pageNo!=1>
        <a href="javascript:${callFunName+'('+1+')'};" class="top_page">首页</a>
        <a href="javascript:${callFunName+'('+(pageNo-1)+')'};" class="page_prev">上一页</a>
    </#if>
    <#if pageNo-showPages/2 gt 0>
        <#assign start = pageNo-(showPages-1)/2/>
        <#if showPages gt totalPage>
            <#assign start = 1/>
        </#if>
    <#else>
        <#assign start = 1/>
    </#if>
    <#if totalPage gt showPages>
        <#assign end = (start+showPages-1)/>
        <#if end gt totalPage>
            <#assign start = totalPage-showPages+1/>
            <#assign end = totalPage/>
        </#if>
    <#else>
        <#assign end = totalPage/>
    </#if>
    <#assign pages=start..end/>
    <#list pages as page>
        <#if page==pageNo>
            <a href="javascript:${callFunName+'('+page+')'};" class="current">${page}</a>
        <#else>
            <a href="javascript:${callFunName+'('+page+')'};">${page}</a>
        </#if>
    </#list>
    <#if pageNo!=totalPage>
        <a href="javascript:${callFunName+'('+(pageNo+1)+')'};" class="page_next">下一页</a>
        <a href="javascript:${callFunName+'('+totalPage+')'};" class="end_page">尾页</a>
    </#if>
</div>
<nav>
    <ul class="pagination">
        <#--@if(query.totalPage<=10){//小于10页直接全部输出
        @for(var i in range(1,query.totalPage+1)){
        <li class="${i==query.pageNumber?'active'}"><a href="${ctxPath}${pageUrl}${i}.html${pageUrlParameter}">${i}</a></li>
        @}
        @}else{
        @var maxdiff = query.totalPage-query.pageNumber;
        @var start = query.pageNumber<=4?1:maxdiff<=3?(query.totalPage - 7):(query.pageNumber-3);
        @var end = query.pageNumber<=4?7:maxdiff<=3?query.totalPage:(query.pageNumber+3);

        @if(start>1){
        <li><a href="${ctxPath}${pageUrl}1.html${pageUrlParameter}">首页</a></li>
        <li class="disabled"><a href="javascript:;">···</a></li>
        @}

        @for(var i in range(start,end+1)){
        <li class="${i==query.pageNumber?'active'}"><a href="${ctxPath}${pageUrl}${i}.html${pageUrlParameter}">${i}</a></li>
        @}

        @if(end<query.totalPage){
        <li class="disabled"><a href="javascript:;">···</a></li>
        <li><a href="${ctxPath}${pageUrl}${query.totalPage}.html${pageUrlParameter}">尾页</a></li>
        @}
        @}-->

        <#if query.totalPage<=10><#--小于10页直接全部输出-->
            <#assign pages=1..(query.totalPage+1)/>
            <#list pages as i>
                <li class="${i==query.pageNumber?string('active','')}"><a href="${ctxPath}${pageUrl}${i}.html${pageUrlParameter}">${i}</a></li>
            </#list>
        <#else>
            <#assign maxdiff = (query.totalPage-query.pageNumber)/>
            <#if query.pageNumber<=4>
                <#assign start = 1/>
                <#assign end = 7/>
            <#else>
                <#if maxdiff<=3>
                    <#assign start = (query.totalPage - 7)/>
                    <#assign end = query.totalPage/>
                <#else >
                    <#assign start = (query.pageNumber-3)/>
                    <#assign end = (query.pageNumber+3)/>
                </#if>
            </#if>
           <#-- <#assign start = query.pageNumber<=4?1:maxdiff<=3?(query.totalPage - 7):(query.pageNumber-3)/>-->
            <#if start > 1){
                <li><a href="${ctxPath}${pageUrl}1.html${pageUrlParameter}">首页</a></li>
                <li class="disabled"><a href="javascript:;">···</a></li>
            </#if>
            <#assign pages=start..(end+1)/>
            <#list pages as i>
                <li class="${i==query.pageNumber?string('active','')}"><a href="${ctxPath}${pageUrl}${i}.html${pageUrlParameter}">${i}</a></li>
            </#list>

            <#if end<query.totalPage>
                <li class="disabled"><a href="javascript:;">···</a></li>
                <li><a href="${ctxPath}${pageUrl}${query.totalPage}.html${pageUrlParameter}">尾页</a></li>
            </#if>
        </#if>
    </ul>
</nav>
</#macro>
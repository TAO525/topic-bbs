<#-- 参数说明：query 封装的分页对象 跳转路径url 跳转url参数-->
<#macro page query pageUrl pageUrlParameter>
<nav>
    <ul class="pagination">
        <#assign totalPage = query.totalPages/>
        <#assign pageNumber = (query.number + 1 )/>
        <#if totalPage != 0 >
            <#if totalPage<=10><#--小于10页直接全部输出-->
                <#assign pages=1..(totalPage)/>
                <#list pages as i>
                    <li class="${(i==pageNumber)?string('active','')}"><a href="${ctxPath!}${pageUrl!}${i}.html${pageUrlParameter!}">${i}</a></li>
                </#list>
            <#else>
                <#assign maxdiff = (totalPage-pageNumber)/>
                <#if pageNumber<=4>
                    <#assign start = 1/>
                    <#assign end = 7/>
                <#else>
                    <#if maxdiff<=3>
                        <#assign start = (totalPage - 7)/>
                        <#assign end = totalPage/>
                    <#else >
                        <#assign start = (pageNumber-3)/>
                        <#assign end = (pageNumber+3)/>
                    </#if>
                </#if>
                <#if (start > 1)>
                    <li><a href="${ctxPath!}${pageUrl!}1.html${pageUrlParameter!}">首页</a></li>
                    <li class="disabled"><a href="javascript:;">···</a></li>
                </#if>
                <#assign pages=start..end/>
                <#list pages as i>
                    <li class="${(i==pageNumber)?string('active','')}"><a href="${ctxPath!}${pageUrl!}${i}.html${pageUrlParameter!}">${i}</a></li>
                </#list>

                <#if end<totalPage>
                    <li class="disabled"><a href="javascript:;">···</a></li>
                    <li><a href="${ctxPath!}${pageUrl!}${totalPage!}.html${pageUrlParameter!}">尾页</a></li>
                </#if>
            </#if>
        </#if>
    </ul>
</nav>
</#macro>
<!DOCTYPE html>
<html>
<head>
    <title>错误${error.code!'Error'}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link href="/css/error.css" rel="stylesheet">
</head>
<body>
<div id="error">
    <div id="logo" onclick="document.querySelector('#error-detail').classList.toggle('hide')">!</div>
    <div id="code">${error.code!'Error'}</div>
</div>
<div id="error-detail" class="hide">
    <table>
        <#if (error.uri)??>
            <tr>
                <td>请求地址</td>
                <td>${error.uri}</td>
            </tr>
        </#if>

        <#if (error.code)??>
            <tr>
                <td>错误代码</td>
                <td>${error.code}</td>
            </tr>
        </#if>

        <#if (error.exception)??>
            <tr>
                <td>错误类型</td>
                <td>${error.exception}</td>
            </tr>
        </#if>

        <#if (error.msg)??>
            <tr>
                <td>错误消息</td>
                <td>${error.msg}</td>
            </tr>
        </#if>

        <#if (error.servlet)??>
            <tr>
                <td>Servlet</td>
                <td>${error.servlet}</td>
            </tr>
        </#if>

        <tr>
            <td colspan="2">如有疑问或需要帮助，请将此信息完整的截图发送给管理员。</td>
        </tr>
    </table>
</div>
</body>
</html>
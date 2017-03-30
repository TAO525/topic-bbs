<#include "common/webHead.ftl"/>


<form action="${ctxPath}/bbs/user/login" onsubmit="return ajaxSubmit(this,'reload')">
    <div class="form-group">
        <label>用户名</label>
        <input type="text" placeholder="请输入您用户名" name="userName"  class="form-control">
    </div>
    <div class="form-group">
        <label>密码</label>
        <input type="password" placeholder="请输入密码" name="password" class="form-control">
    </div>
    <button class="btn btn-primary btn-block" type="submit">登 录</button>
</form>

<script>
    //全局表单ajax提交(onsubmit="return ajaxSubmit(this,reload)")
    function ajaxSubmit(form,reload){
        form = $(form);
        $.post(form.attr('action'),form.serialize(),function(data,status){
            <#--json.err?$.alert(json.msg):reload?location.reload():location.replace('${ctxPath}'+json.msg);-->
            alert("111");
        })
        return false;
    }
</script>

<#include "common/webEnd.ftl"/>
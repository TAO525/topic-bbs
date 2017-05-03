$(function () {
    /* 管理员增删改查 */
    $('#post-list').on('click','.nice-btn',function(){
        $.post('/bbs/admin/topic/nice/'+$(this).parents('div').data('topic'),function(json){
            json.err||location.reload();
        })
    }).on('click','.top-btn',function(){
        $.post('/bbs/admin/topic/up/'+$(this).parents('div').data('topic'),function(json){
            json.err||location.reload();
        })
    }).on('click','.del-btn',function(){
        var topicid = $(this).parents('div').data('topic');
        layer.confirm(
            '确定要删除么？',
            {btn:["确定","取消"]},
            function(){
                $.post('/bbs/admin/topic/delete/'+topicid,function(json){
                json.err||location.reload();
            })
        })
    })

    /* 查询 */
    $('#serach-btn').on('click',function(){
        var keyword = $('#keyword').val().trim();
        location.href = '/bbs/index/1.html?keyword='+keyword;
    })
    $("#keyword").on('keydown',function (e) {
        if (e.keyCode == 13) {
            stopDefault(e);
            $('#serach-btn').trigger('click');
        }
    });


})

function stopDefault(e) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if(e && e.preventDefault) {
        //阻止默认浏览器动作(W3C)
        e.preventDefault();
    } else {
        //IE中阻止函数器默认动作的方式
        window.event.returnValue = false;
    }
    return false;
}
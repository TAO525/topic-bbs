var editor;
$(function () {
        editor = new Simditor({
        textarea: $('#postContent'),
        defaultImage:'/images/cat.png',
        pasteImage:true,
        cleanPaste:true,
        toolbar:['title','bold','italic','underline','strikethrough','fontScale','color','ol','ul','blockquote','code','table','link','image','hr','indent','outdent','alignment'],
        //按Beetl论坛的用处来看，暂时只需要如下几个语言（HTML、XML、json、java、javascript、markdown、sql）,如有需要再扩展
        codeLanguages:[{name:'HTML,XML',value:'html'},{name:'JSON',value:'json'},{name:'Java',value:'java'},{name:'JavaScript',value:'js'},{name:'SQL',value:'sql'}],
        upload:{
            url: '/bbs/common/upload',
            fileKey: 'editormd-image-file',
            connectionCount: 3,
            leaveConfirm: '上传正在进行中，确定要离开当前页面吗？'
        }
    });


})

function ajaxSubmit(form,reload){
    form = $(form);
    $.post(form.attr('action'),form.serialize(),function(json){
        json.err?layer.msg(json.msg):reload?location.reload():location.replace(json.msg);
    })
    return false;
}


//删除post
function deletePost(el){
    var p = $(el).parents('.panel-default[data-post]');

    layer.confirm('确定要删除该内容吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index){
        $.post('/bbs/admin/post/delete/'+p.data('post'),function(json){
//                    json.err?layer.msg(json.msg):p.remove();
            console.log(p.data('post'))
            if(json.err){
                layer.msg(json.msg);
                layer.close(index)
            }else {
                p.remove();
                layer.close(index)
            }
        })
    });
}


function showReplyDialog(postid,topicid){
    var dialog = $('#reply-dialog').modal('show');
    dialog.find('textarea').val('')
    dialog.find('[name=postId]').val(postid);
    dialog.find('[name=topicId]').val(topicid);
}

function preview(){
    var preWin = window.open('about:blank','preview');
    preWin.document.write('<meta name="google" value="notranslate"><link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"><style>.panel img{max-width:100% !important;heightLauto !important;}.panel{margin-top:20px;}</style>');
    preWin.document.write('<div class="container-fluid"><div class="row"><div class="col-sm-8 col-sm-offset-2"><div class="panel panel-default"><div class="panel-heading">内容提交预览</div><div class="panel-body">'+editor.sync()+'</div><div class="panel-footer"><a href="javascript:;" class="btn btn-link" onclick="window.close();">关闭预览</a></div></div></div></div></div>');
    preWin.document.write('<link href="http://cdn.bootcss.com/highlight.js/9.8.0/styles/github-gist.min.css" rel="stylesheet"><script src="http://cdn.bootcss.com/highlight.js/9.8.0/highlight.min.js"></scr'+'ipt><script>hljs.initHighlightingOnLoad();</scr'+'ipt>');
    preWin.document.close();
}
package com.wayne.action;

import com.alibaba.fastjson.JSONObject;
import com.wayne.common.WebUtils;
import com.wayne.model.BbsPost;
import com.wayne.model.BbsTopic;
import com.wayne.model.BbsUser;
import com.wayne.service.BbsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员
 * @Author TAO
 * @Date 2017/4/11 14:57
 */
@Controller
@RequestMapping("/bbs/admin")
public class BbsAdminController extends BaseController{


    @Resource
    private BbsService bbsService;

    @ResponseBody
    @PostMapping("/topic/nice/{id}")
    public JSONObject editNiceTopic(@PathVariable int id, HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        if(!WebUtils.isAdmin(request, response)){
            //如果有非法使用，不提示具体信息，直接返回null
            result.put("err", 1);
            result.put("msg", "呵呵~~");
        }else{
            BbsTopic db = bbsService.getById(id);
            if(db == null){
                result.put("err", 1);
                result.put("msg", "id不存在");
            }else {
                Integer nice = db.getIsNice();
                nice = nice > 0 ? 0 : 1;
                bbsService.updateTopicNice(nice,id);
                result.put("err", 0);
                result.put("msg", "success");
            }
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/topic/up/{id}")
    public JSONObject editUpTopic(@PathVariable int id,HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        if(!WebUtils.isAdmin(request, response)){
            //如果有非法使用，不提示具体信息，直接返回null
            result.put("err", 1);
            result.put("msg", "呵呵~~");
        }else{
            BbsTopic db = bbsService.getById(id);
            if(db == null){
                result.put("err", 1);
                result.put("msg", "id不存在");
            }else {
                Integer up = db.getIsUp();
                up = up > 0 ? 0 : 1;
                bbsService.updateTopicUp(up, id);
                result.put("err", 0);
                result.put("msg", "success");
            }
        }
        return result;
    }


    @ResponseBody
    @PostMapping("/topic/delete/{id}")
    public JSONObject deleteTopic(@PathVariable int id,HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        if(!WebUtils.isAdmin(request, response)){
            //如果有非法使用，不提示具体信息，直接返回null
            result.put("err", 1);
            result.put("msg", "呵呵~~");
        }else{
            bbsService.deleteTopic(id);
            result.put("err", 0);
            result.put("msg", "success");
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/post/delete/{id}")
    public JSONObject deletePost(@PathVariable int id,HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        BbsPost bbsPost = bbsService.getPostById(id);
        if(!canUpdatePost(bbsPost,request,response)){
            result.put("err", 1);
            result.put("msg", "不是自己发表的内容无法删除");
        }else{
            bbsService.deletePost(id);
            result.put("err", 0);
            result.put("msg", "success");
        }
        return result;
    }


    private boolean canUpdatePost(BbsPost post, HttpServletRequest request, HttpServletResponse response){

        BbsUser user = WebUtils.currentUser(request, response);
        if(user==null){
            return false;
        }
        if(post.getBbsUser().getId().equals(user.getId())){
            return true ;
        }
        //如果是admin
        if(user.getUserName().equals("admin")){
            return true;
        }

        return false;
    }
}

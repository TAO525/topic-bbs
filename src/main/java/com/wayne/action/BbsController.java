package com.wayne.action;

import com.alibaba.fastjson.JSONObject;
import com.wayne.common.WebUtils;
import com.wayne.common.lucene.LuceneUtil;
import com.wayne.common.lucene.entity.IndexObject;
import com.wayne.config.Const;
import com.wayne.interceptor.Auth;
import com.wayne.model.*;
import com.wayne.service.BbsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @Author TAO
 * @Date 2017/4/11 14:57
 */
@Controller
@RequestMapping("/bbs")
public class BbsController extends BaseController{

    @Resource
    private LuceneUtil luceneUtil;

    @Resource
    private BbsService bbsService;

    @ModelAttribute("moduleList")
    public List<BbsModule> moduleList(){
        return bbsService.getModuleList();
    }

    @ModelAttribute("msgCount")
    public Integer msgCount(HttpServletRequest request,HttpServletResponse response){
        BbsUser user = WebUtils.currentUser(request, response);
        if(user != null) {
            return bbsService.getMessageCount(user.getId(),1);
        }else {
            return 0;
        }
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        logger.info("首页访问");
        return new ModelAndView( "forward:/bbs/index/1.html");
    }

    @RequestMapping("/index/{p}.html")
    public ModelAndView  index(@PathVariable int p, String keyword){
        ModelAndView view = new ModelAndView();
        if (StringUtils.isBlank(keyword)) {
            view.setViewName("index");
            Page<BbsTopic> topics = bbsService.getTopics(p, Const.TOPIC_PAGE_SIZE);
            view.addObject("topics", topics);
            view.addObject("pagename", "首页综合");
        }else{
            keyword = keyword.replaceAll("\\(|\\)", "");
            //查看索引文件最后修改日期
            File file = new File(luceneUtil.getIndexDer());
            Date fileupdateDate = null;
            if(file.exists() && file.listFiles().length  > 0 ){fileupdateDate = new Date(file.lastModified());}
            //获取索引的数据 ：主题和回复
            List<IndexObject> bbsContentList = bbsService.getBbsTopicPostList(luceneUtil,fileupdateDate);

            //创建索引
            luceneUtil.createDataIndexer(bbsContentList);
            //查询索引
            Page<IndexObject> searcherKeywordPage = luceneUtil.searcherKeyword(keyword,Const.TOPIC_PAGE_SIZE, p);
            view.setViewName("/searchIndex");
            view.addObject("searcherPage", searcherKeywordPage);
            view.addObject("pagename", keyword);
            view.addObject("resultnum", searcherKeywordPage.getTotalElements());
            view.addObject("keyword",keyword);
            logger.info("===================number"+searcherKeywordPage.getNumber());
            logger.info("===================totalpages"+searcherKeywordPage.getTotalPages());
        }
        return view;
    }

    @RequestMapping("/topic/module/{id}-{p}.html")
    public ModelAndView module(@PathVariable final int id, @PathVariable int p){
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        Page<BbsTopic> topics = bbsService.getTopicsByModuleId(id, p, Const.TOPIC_PAGE_SIZE);

        if(topics.getContent().size() >0){
            BbsTopic bbsTopic = (BbsTopic) topics.getContent().get(0);
            view.addObject("pagename",bbsTopic.getBbsModule().getName());
        }
        view.addObject("topics", topics);
        view.addObject("pageUrl","/bbs/topic/module/"+id+"-");
        return view;
    }


    @RequestMapping("/topic/{id}-{p}.html")
    public ModelAndView topic(@PathVariable final int id, @PathVariable int p){
        ModelAndView view = new  ModelAndView();
        view.setViewName("detail");
        List<BbsPost> posts = bbsService.getPostByTopicId(id);

        bbsService.increasePv(id);
        BbsTopic topic = bbsService.getById(id);
        view.addObject("posts",posts);
        view.addObject("topic",topic);
        return view;
    }


    /**
     * 文章发布改为Ajax方式提交更友好
     * @param topic
     * @param post
     * @param title
     * @param postContent
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping("/topic/save")
    public JSONObject saveTopic(BbsTopic topic, BbsPost post, String title, String postContent,HttpServletRequest request, HttpServletResponse response){
        //@TODO， 防止频繁提交
        BbsUser user = WebUtils.currentUser(request, response);
//		Date lastPostTime = bbsService.getLatestPost(user.getId());
//		long now = System.currentTimeMillis();
//		long temp = lastPostTime.getTime();
//		if(now-temp<1000*10){
//			//10秒之内的提交都不处理
//			throw new RuntimeException("提交太快，处理不了，上次提交是 "+lastPostTime);
//		}
        JSONObject result = new JSONObject();
        result.put("err", 1);
        if(user==null){
            result.put("msg", "请先登录后再继续！");
        }else if(title.length()<Const.TITLE_MIN_SIZE||postContent.length()<Const.CONTENT_MIN_SIZE){
            //客户端需要完善
            result.put("msg", "标题或内容太短！");
        }else{
            BbsModule module = new BbsModule();
            module.setId(topic.getModuleId());
            topic.setIsNice(0);
            topic.setIsUp(0);
            topic.setPv(1);
            topic.setPostCount(1);
            topic.setReplyCount(0);
            post.setHasReply(0);
            topic.setContent(title);
            post.setContent(postContent);
            topic.setBbsModule(module);
            bbsService.saveTopic(topic, post, user);

            result.put("err", 0);
            result.put("msg", "/bbs/topic/"+topic.getId()+"-1.html");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/post/save")
    public JSONObject savePost(BbsPost post, HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        result.put("err", 1);
        if(post.getContent().length()<Const.CONTENT_MIN_SIZE){
            result.put("msg", "内容太短，请重新编辑！");
        }else{
            post.setHasReply(0);
            post.setCreateTime(new Date());
            BbsUser user =  WebUtils.currentUser(request, response);
            post.setBbsUser(user);
            bbsService.savePost(post);
            //通知消息
            bbsService.notifyParticipant(post.getTopicId(),user.getId());

            result.put("msg", "/bbs/topic/"+post.getTopicId()+"-"+1+".html");
            result.put("err", 0);
        }
        return result;
    }


    /**
     * 回复评论改为Ajax方式提升体验
     * @param reply
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping("/reply/save")
    public JSONObject saveReply(BbsReply reply, HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        result.put("err", 1);
        BbsUser user = WebUtils.currentUser(request, response);
        if(user==null){
            result.put("msg", "未登录用户！");
        }else if(reply.getContent().length()<Const.REPLY_MIN_SIZE){
            result.put("msg", "回复内容太短，请修改!");
        }else{
            reply.setBbsUser(user);
            reply.setPostId(reply.getPostId());
            reply.setCreateTime(new Date());
            bbsService.saveReply(reply);
            //通知消息
            bbsService.notifyParticipant(reply.getTopicId(),user.getId());
            result.put("msg", "评论成功！");
            result.put("err", 0);
        }
        return result;
    }

    @Auth
    @RequestMapping("/topic/add.html")
    public ModelAndView addTopic(ModelAndView view){
        view.setViewName("post");
        return view;
    }


    @Auth
    @RequestMapping("/myMessage.html")
    public ModelAndView  myPage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView();
        view.setViewName("message");
        BbsUser user = WebUtils.currentUser(request, response);
        List<BbsTopic> list = bbsService.getMyMsgTopics(user.getId());
        view.addObject("list", list);
        return view;
    }

    @Auth
    @RequestMapping("/post/edit/{id}.html")
    public ModelAndView editPost(ModelAndView view, @PathVariable int id, HttpServletRequest request, HttpServletResponse response){
        view.setViewName("postEdit");
        BbsPost post = bbsService.getPostById(id);
        Integer tipicId = post.getTopicId();
        view.addObject("post",post);
        view.addObject("topic", bbsService.getById(tipicId));
        return view;
    }

    @RequestMapping("/my/{p}.html")
    public RedirectView openMyTopic(@PathVariable int p, HttpServletRequest request, HttpServletResponse response){
        BbsUser user = WebUtils.currentUser(request, response);
        bbsService.updateMsgStatus(user.getId(), p,0);
        return  new RedirectView( request.getContextPath()+"/bbs/topic/"+p+"-1.html");
    }
}

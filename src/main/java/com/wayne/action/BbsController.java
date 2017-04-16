package com.wayne.action;

import com.wayne.common.lucene.LuceneUtil;
import com.wayne.common.lucene.entity.IndexObject;
import com.wayne.config.Const;
import com.wayne.model.BbsTopic;
import com.wayne.model.PageQuery;
import com.wayne.service.BbsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
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
            PageQuery<IndexObject> searcherKeywordPage = luceneUtil.searcherKeyword(keyword,Const.TOPIC_PAGE_SIZE, p);
            view.setViewName("/lucene/index.html");
            view.addObject("searcherPage", searcherKeywordPage);
            view.addObject("pagename", keyword);
            view.addObject("resultnum", searcherKeywordPage.getTotalRow());
        }
        return view;
    }
}

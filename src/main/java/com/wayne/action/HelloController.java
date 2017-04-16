package com.wayne.action;

import com.wayne.model.BbsTopic;
import com.wayne.service.BbsService;
import com.wayne.service.BbsUserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @Author TAO
 * @Date 2017/3/22 18:13
 */
@RestController
public class HelloController {
    @Resource
    private BbsUserService bbsUserService;

    @Resource
    private BbsService bbsService;

//    @Auth
    @RequestMapping("/hello")
    public ModelAndView say(){
        ModelAndView index = new ModelAndView("index");
      /*  PageQuery query = new PageQuery(2,null,215);
        query.setPageNumber(10);
        List<String> list = new ArrayList<>();
        for(int i=0;i<50;i++){
            list.add(i+"");
        }
        query.setList(list);*/
        Page<BbsTopic> topics = bbsService.getTopics(1, 2);
        System.out.println(topics.getNumber()); //当前页 从0开始
        System.out.println(topics.getNumberOfElements());
        System.out.println(topics.getTotalPages()); //总页数
//        PageQuery query = new PageQuery();
        index.addObject("query",topics);

        return index;
    }

}

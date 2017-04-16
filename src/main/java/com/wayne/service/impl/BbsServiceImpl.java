package com.wayne.service.impl;


import com.wayne.common.lucene.LuceneUtil;
import com.wayne.common.lucene.entity.IndexObject;
import com.wayne.dao.BbsPostRepository;
import com.wayne.dao.BbsTopicRepository;
import com.wayne.model.BbsPost;
import com.wayne.model.BbsTopic;
import com.wayne.service.BbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author TAO
 * @Date 2017/3/23 18:32
 */
@Service
public class BbsServiceImpl implements BbsService {

    @Autowired
    private BbsPostRepository postDao;

    @Autowired
    private BbsTopicRepository topicDao;

    @Override
    @Cacheable("TOPIC")
    public Page<BbsTopic> getTopics(int pageNumber,int pageSize) {
        PageRequest request = this.buildPageRequest(pageNumber,pageSize);
        Page<BbsTopic> sourceCodes= this.topicDao.findAll(request);
        return sourceCodes;
    }

    //构建PageRequest
    private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
        List<Sort.Order> orders=new ArrayList<>();
        orders.add( new Sort.Order(Sort.Direction.DESC, "isUp"));
        orders.add( new Sort.Order(Sort.Direction.DESC, "createTime"));
        return new PageRequest(pageNumber - 1, pagzSize, new Sort(orders));
    }

    @Override
    public List<IndexObject> getBbsTopicPostList(LuceneUtil luceneUtil, Date fileupdateDate){
        List<IndexObject>  indexObjectsList = new ArrayList<>();
        //获取主题和回复最后的提交时间
        List<IndexObject> bbsTopics = null;
        List<IndexObject> bbsPosts = null;
        try {
//            Map<String,Date> lastPostDate = postDao.getLastPostDate();
            Date lastPostDate = postDao.getLastPostDate();
            Date lastTopicDate = topicDao.getLastTopicDate();

            Date topiclastupdate = luceneUtil.getDateFormat().parse(luceneUtil.getDateFormat().format(lastTopicDate));
            Date postlastupdate = luceneUtil.getDateFormat().parse(luceneUtil.getDateFormat().format(lastPostDate));
            if (fileupdateDate != null)
                fileupdateDate = luceneUtil.getDateFormat().parse(luceneUtil.getDateFormat().format(fileupdateDate));

            if (fileupdateDate == null || (topiclastupdate != null && luceneUtil.dateCompare(topiclastupdate, fileupdateDate))) {
                List<BbsTopic> topicsPo = topicDao.findByCreateTimeBetween(fileupdateDate, topiclastupdate);
                if (null != topicsPo && !topicsPo.isEmpty()) {
                    for (BbsTopic item : topicsPo) {
                        IndexObject indexObject = new IndexObject();
                        indexObject.setTopicId(String.valueOf(item.getId()));
                        indexObject.setContent(item.getContent());
                        bbsTopics.add(indexObject);
                    }
                }
            }
            if (fileupdateDate == null || (postlastupdate != null && luceneUtil.dateCompare(postlastupdate, fileupdateDate))) {
                List<BbsPost> postPos = postDao.findByCreateTimeBetween(fileupdateDate, postlastupdate);
                if (null != postPos && !postPos.isEmpty()) {
                    for (BbsPost item : postPos) {
                        IndexObject indexObject = new IndexObject();
                        indexObject.setTopicId(String.valueOf(item.getTopicId()));
                        indexObject.setContent(item.getContent());
                        bbsTopics.add(indexObject);
                    }
                }
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        if(bbsTopics!=null)indexObjectsList.addAll(bbsTopics);
        if(bbsPosts!=null)indexObjectsList.addAll(bbsPosts);
//		System.out.println("================");
//		System.out.println(JSONObject.toJSONString(indexObjectsList));
        return indexObjectsList;
    }

    @Override
    public BbsTopic getById(Integer id) {
        return topicDao.findOne(id);
    }
}
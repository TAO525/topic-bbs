package com.wayne.service.impl;


import com.wayne.common.lucene.LuceneUtil;
import com.wayne.common.lucene.entity.IndexObject;
import com.wayne.dao.*;
import com.wayne.model.*;
import com.wayne.service.BbsService;
import com.wayne.service.BbsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private BbsReplyRepository replyDao;

    @Autowired
    private BbsModuleRepository moduleDao;

    @Autowired
    private BbsMessageRepository messageDao;

    @Autowired
    private BbsUserService bbsUserService;

    @Autowired
    private LuceneUtil luceneUtil;

    @Override
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
        List<IndexObject> bbsTopics = new ArrayList<>();
        List<IndexObject> bbsPosts = new ArrayList<>();
        try {
            Date lastPostDate = postDao.getLastPostDate();
            Date lastTopicDate = topicDao.getLastTopicDate();

            Date topiclastupdate = luceneUtil.getDateFormat().parse(luceneUtil.getDateFormat().format(lastTopicDate));
            Date postlastupdate = luceneUtil.getDateFormat().parse(luceneUtil.getDateFormat().format(lastPostDate));
            if (fileupdateDate != null)
                fileupdateDate = luceneUtil.getDateFormat().parse(luceneUtil.getDateFormat().format(fileupdateDate));

            if (fileupdateDate == null || (topiclastupdate != null && luceneUtil.dateCompare(topiclastupdate, fileupdateDate))) {
                List<BbsTopic> topicsPo;
                if(null==fileupdateDate){
                    //由于不能传入null 并转化成空字符 所以插入个默认初始时间
                    topicsPo = topicDao.findByCreateTimeBetween(getStartDate(),topiclastupdate);
                }else{
                    topicsPo = topicDao.findByCreateTimeBetween(fileupdateDate, topiclastupdate);
                }
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
                List<BbsPost> postPos;
                if(null==fileupdateDate){
                    postPos = postDao.findByCreateTimeBetween(getStartDate(),topiclastupdate);
                }else{
                    postPos = postDao.findByCreateTimeBetween(fileupdateDate, postlastupdate);
                }
                if (null != postPos && !postPos.isEmpty()) {
                    for (BbsPost item : postPos) {
                        IndexObject indexObject = new IndexObject();
                        indexObject.setTopicId(String.valueOf(item.getTopicId()));
                        indexObject.setContent(item.getContent());
                        indexObject.setPostId(String.valueOf(item.getId()));
                        bbsPosts.add(indexObject);
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

    private Date getStartDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse("2000-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    @Override
    public BbsTopic getById(Integer id) {
        return topicDao.findOne(id);
    }

    @Override
    @Transactional
    public void updateTopicNice(int isNice,int id) {
        topicDao.updateTopicNice(isNice,id);
    }

    @Override
    @Transactional
    public void updateTopicUp(int up, int id) {
        topicDao.updateTopicUp(up,id);
    }

    @Override
    @Transactional
    public void deleteTopic(int id) {
        luceneUtil.delBy("tid",String.valueOf(id));
        topicDao.deleteById(id);
        postDao.deleteByTopicId(id);
        replyDao.deleteByTopicId(id);
    }

    @Override
    @Cacheable("ModuleList")
    public List<BbsModule> getModuleList() {
        return moduleDao.findAll();
    }

    @Override
    public Page<BbsTopic> getTopicsByModuleId(Integer moduleId, int pageNumber, int pageSize) {
        PageRequest request = this.buildPageRequest(pageNumber,pageSize);

        Specification<BbsTopic> specification = new Specification<BbsTopic>() {
            /**
             *
             * @param *root 代表查询的实体类
             * @param criteriaQuery 可以从中获得root 对象，即告知JPA criteria 查询要查询哪一个实体类
             *                      还可以来添加查询条件，还可以结合EntityManager 对象得到最终查询的TypeQuery对象
             * @param *criteriaBuilder 用于创建crite 相关对象的工厂
             * @return *Predicate 代表一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<BbsTopic> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("bbsModule").get("id");
                Predicate predicate = criteriaBuilder.equal(path,moduleId);
                return predicate;
            }
        };

        return topicDao.findAll(specification,request);
    }

    @Override
    @Transactional
    public void increasePv(int id) {
        topicDao.increasePv(id);
    }

    @Override
    public List<BbsPost> getPostByTopicId(int topicId) {
        return postDao.getByTopicId(topicId);
    }

    @Override
    public BbsPost getPostById(int postId) {
        return postDao.findOne(postId);
    }

    @Override
    @Transactional
    public void deletePost(int id) {
        //维护lucene
        luceneUtil.delBy("pid", String.valueOf(id));
        postDao.delete(id);
        replyDao.deleteByPostId(id);
    }

    @Override
    @Transactional
    public void savePost(BbsPost bbsPost) {
        postDao.save(bbsPost);
        bbsUserService.addPostScore(bbsPost.getBbsUser().getId());
        topicDao.increasePostCount(bbsPost.getTopicId());
    }

    @Override
    @Transactional
    public void updatePostContent(BbsPost bbsPost) {
        //维护lucene
        luceneUtil.updateByPost(bbsPost.getTopicId(),bbsPost.getId(),bbsPost.getContent());
        postDao.updatePostContent(bbsPost.getId(),bbsPost.getContent());
    }

    @Override
    public void saveReply(BbsReply bbsReply) {
        replyDao.save(bbsReply);
        bbsUserService.addReplayScore(bbsReply.getBbsUser().getId());
    }

    @Override
    @Transactional
    public void saveTopic(BbsTopic bbsTopic, BbsPost bbsPost, BbsUser bbsUser) {
        bbsTopic.setCreateTime(new Date());
        bbsTopic.setBbsUser(bbsUser);
        BbsTopic topic = topicDao.save(bbsTopic);
        bbsPost.setBbsUser(bbsUser);
        bbsPost.setTopicId(topic.getId());
        bbsPost.setCreateTime(new Date());
        postDao.save(bbsPost);
        bbsUserService.addTopicScore(bbsUser.getId());
    }

    @Override
    @Transactional
    public void notifyParticipant(Integer topicId, Integer ownerId) {
        List<Integer> userIds = postDao.getUserIdsByTopicId(topicId);
        for(Integer userId:userIds){
            if(userId == ownerId){
                continue;
            }
            //TODO,以后改成批处理,但存在insert&update问题
            makeOneBbsMessage(userId,topicId,1);
        }
    }

    private BbsMessage makeOneBbsMessage(Integer userId, int topicId, int status){
        BbsMessage msg = new BbsMessage();
        msg.setUserId(userId);
        msg.setTopicId(topicId);
        List<BbsMessage> list = messageDao.findAllByUserIdAndAndTopicId(userId, topicId);
        if(list.isEmpty()){
            msg.setStatus(status);
            messageDao.save(msg);
            return msg;
        }else{
            msg =  list.get(0);
            if(msg.getStatus()!=status){
                msg.setStatus(status);
                messageDao.updateStatusById(msg.getId(),status);
            }
            return msg;
        }
    }

    @Override
    public Integer getMessageCount(Integer userId, int i) {
        return messageDao.countBbsMessageByUserIdAndStatus(userId,i);
    }

    @Override
    public List<BbsTopic> getMyMsgTopics(Integer id) {
        return topicDao.getMyMsgTopics(id);
    }

    @Override
    @Transactional
    public void updateMsgStatus(Integer userId, int topicId, int status) {
        messageDao.updateMsgStatus(userId, topicId, status);
    }
}
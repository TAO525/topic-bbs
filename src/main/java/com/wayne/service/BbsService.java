package com.wayne.service;

import com.wayne.common.lucene.LuceneUtil;
import com.wayne.common.lucene.entity.IndexObject;
import com.wayne.model.BbsModule;
import com.wayne.model.BbsPost;
import com.wayne.model.BbsReply;
import com.wayne.model.BbsTopic;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface BbsService {

    BbsTopic getById(Integer id);

    Page<BbsTopic> getTopics(int pageNumber, int pageSize);

    /**
     * 根据板块id 获取话题 JPA springData 带分页查询
     * @param moduleId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<BbsTopic> getTopicsByModuleId(Integer moduleId,int pageNumber,int pageSize);

    /**
     * 关键字查询话题
     * @param luceneUtil
     * @param fileupdateDate
     * @return
     */
    List<IndexObject> getBbsTopicPostList(LuceneUtil luceneUtil, Date fileupdateDate);

    void updateTopicNice(int isNice,int id);

    void updateTopicUp(int up,int id);

    void deleteTopic(int id);

    /**
     * 增加话题的阅读量
     * @param id
     */
    void increasePv(int id);

    /**
     * 获取板块列表
     * @return
     */
    List<BbsModule> getModuleList();

    List<BbsPost> getPostByTopicId(int topicId);

    BbsPost getPostById(int postId);

    void deletePost(int id);

    /**
     * 保存回答
     * @param bbsPost
     */
    void savePost(BbsPost bbsPost);

    /**
     * 修改回答
     * @param bbsPost
     */
    void updatePostContent(BbsPost bbsPost);

    /**
     * 保存回复
     * @param bbsReply
     */
    void saveReply(BbsReply bbsReply);
}

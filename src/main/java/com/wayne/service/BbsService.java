package com.wayne.service;

import com.wayne.common.lucene.LuceneUtil;
import com.wayne.common.lucene.entity.IndexObject;
import com.wayne.model.BbsModule;
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

    List<IndexObject> getBbsTopicPostList(LuceneUtil luceneUtil, Date fileupdateDate);

    void updateTopicNice(int isNice,int id);

    void updateTopicUp(int up,int id);

    void deleteTopic(int id);

    List<BbsModule> getModuleList();
}

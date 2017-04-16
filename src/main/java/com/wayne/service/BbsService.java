package com.wayne.service;

import com.wayne.common.lucene.LuceneUtil;
import com.wayne.common.lucene.entity.IndexObject;
import com.wayne.model.BbsTopic;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface BbsService {

    BbsTopic getById(Integer id);

    Page<BbsTopic> getTopics(int pageNumber, int pageSize);

    List<IndexObject> getBbsTopicPostList(LuceneUtil luceneUtil, Date fileupdateDate);
}

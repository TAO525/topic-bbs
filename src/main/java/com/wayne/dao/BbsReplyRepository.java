package com.wayne.dao;

import com.wayne.model.BbsReply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsReplyRepository extends JpaRepository<BbsReply, Integer> {
    void deleteByTopicId(Integer id);
}

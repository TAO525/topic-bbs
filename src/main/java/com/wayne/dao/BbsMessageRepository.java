package com.wayne.dao;

import com.wayne.model.BbsMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsMessageRepository extends JpaRepository<BbsMessage, Integer> {
    List<BbsMessage> findAllByUserIdAndAndTopicId(Integer userId,Integer topicId);


    @Modifying
    @Query("update BbsMessage b set b.status = ?2 where b.id = ?1")
    void updateStatusById(Integer id, int status);

    Integer countBbsMessageByUserIdAndStatus(Integer userId,Integer status);

    @Modifying
    @Query("update BbsMessage b set b.status = ?3 where b.userId = ?1 and b.topicId = ?2")
    void updateMsgStatus(Integer userId, int topicId, int status);
}

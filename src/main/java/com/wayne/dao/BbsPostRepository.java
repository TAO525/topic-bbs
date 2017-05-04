package com.wayne.dao;

import com.wayne.model.BbsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsPostRepository extends JpaRepository<BbsPost, Integer> {

    @Query(value = "SELECT max(p.create_time) as postlastupdate FROM bbs_post p",nativeQuery = true)
    Date getLastPostDate();

    List<BbsPost> findByCreateTimeBetween(Date fileupdateDate, Date topiclastupdate);

    @Query("select b from BbsPost b where b.topicId = ?1 order by b.createTime asc ")
    List<BbsPost> getByTopicId(int topicId);

    void deleteByTopicId(Integer id);

    @Query("update BbsPost b set b.content = ?2,b.updateTime = current_timestamp where b.id = ?1")
    @Modifying
    void updatePostContent(Integer id, String content);

    @Query(value = "SELECT user_id FROM bbs_post WHERE topic_id = ?1",nativeQuery = true)
    List<Integer> getUserIdsByTopicId(Integer topicId);

}

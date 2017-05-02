package com.wayne.dao;

import com.wayne.model.BbsPost;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query("select b from BbsPost b where b.topicId = ?1 order by b.createTime desc ")
    List<BbsPost> getByTopicId(int topicId);

    void deleteByTopicId(Integer id);

    /* 这里名字对了但是类型有错 所以方法不行 */
   /* @Query(value = "SELECT new com.wayne.common.lucene.entity.IndexObject(p.topicId,p.content) FROM BbsPost p WHERE create_time BETWEEN ?1 AND ?2")
    List<IndexObject> getBbsPostListByDate(Date fileupdateDate, Date topiclastupdate);*/
}

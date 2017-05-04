package com.wayne.dao;

import com.wayne.model.BbsTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsTopicRepository extends JpaRepository<BbsTopic, Integer>,JpaSpecificationExecutor<BbsTopic> {
    //利用nativesql
    @Query(value = "SELECT max(p.create_time) as lastupdate FROM bbs_topic p",nativeQuery = true)
    Date getLastTopicDate();

    List<BbsTopic> findByCreateTimeBetween(Date fileupdateDate, Date topiclastupdate);

    //用objet[] 可以接受返回对象
    @Query(value = "SELECT id as topicId,content FROM bbs_topic WHERE create_time BETWEEN ?1 AND ?2",nativeQuery = true)
    @Deprecated
    List<Object[]> getBbsTopicListByDate(Date fileupdateDate, Date topiclastupdate);

    @Modifying
    @Query("update BbsTopic p set p.isNice = ?1 where p.id = ?2")
    void updateTopicNice(int isNice,int id);

    @Modifying
    @Query("update BbsTopic p set p.isUp = ?1 where p.id = ?2")
    void updateTopicUp(int up,int id);

    @Modifying
    @Query("update BbsTopic p set p.pv = p.pv +1 where p.id = ?1")
    void increasePv(int id);

    @Modifying
    @Query("update BbsTopic p set p.postCount = p.postCount +1 where p.id = ?1")
    void increasePostCount(int id);

    void deleteById(int id);

    @Query(value = "SELECT t.* FROM bbs_message m left join bbs_topic t on m.topic_id= t.id where m.user_id= ?1 and status = 1",nativeQuery = true)
    List<BbsTopic> getMyMsgTopics(Integer id);


}

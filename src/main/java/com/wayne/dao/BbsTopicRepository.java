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

    void deleteById(int id);


    /*返回对象属性如何取别名？ 不用as，但是数据类型要对*/
  /*  @Query(value = "SELECT new com.wayne.common.lucene.entity.IndexObject(p.id topicId,p.content) FROM BbsTopic p WHERE create_time BETWEEN ?1 AND ?2")
    List<IndexObject> getBbsTopicListByDate2(Date fileupdateDate, Date topiclastupdate);*/
}

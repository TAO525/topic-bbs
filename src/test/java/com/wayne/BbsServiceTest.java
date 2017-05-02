package com.wayne;

import com.wayne.dao.BbsPostRepository;
import com.wayne.dao.BbsTopicRepository;
import com.wayne.model.BbsPost;
import com.wayne.model.BbsTopic;
import com.wayne.model.BbsUser;
import com.wayne.service.BbsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author TAO
 * @Date 2017/3/24 15:14
 */
public class BbsServiceTest extends BaseServiceTest {

    @Autowired
    private BbsService bbsService;

    @Autowired
    private BbsPostRepository bbsPostRepository;

    @Autowired
    private BbsTopicRepository bbsTopicRepository;

    @Test
    public void test_getBbsTopic() throws Exception {
        BbsTopic topic = bbsService.getById(71);
        if (null != topic) {
            logger.info(topic.getBbsUser().toLineString());
        }
    }


    @Test
    public void test_getBbsTopics() throws Exception {
        Page<BbsTopic> topic = bbsService.getTopics(1,2);
        if (null != topic) {
            logger.info(topic.getTotalPages()+"");
        }
    }

    @Test
    public void test_getlastPostTime() throws Exception {
        Date lastPostDate = bbsPostRepository.getLastPostDate();
        Date lastTopicDate = bbsTopicRepository.getLastTopicDate();
        if (null != lastPostDate) {
            logger.info(lastPostDate.toString());
            logger.info(lastTopicDate.toString());
        }
    }

    @Test
    public void test_getbbsTopicList() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<BbsTopic> bbsTopicList = bbsTopicRepository.findByCreateTimeBetween(simpleDateFormat.parse("2016-01-01"), new Date());
//        List<Object[]> bbsTopicListByDate = bbsTopicRepository.getBbsTopicListByDate(simpleDateFormat.parse("2016-01-01"), new Date());
//        List<IndexObject> bbsTopicListByDate2 = bbsTopicRepository.getBbsTopicListByDate2(simpleDateFormat.parse("2016-01-01"), new Date());
        if (null != bbsTopicList) {
            logger.info(bbsTopicList.get(0).toString());
        }
    }

    @Test
    public void test_getbbsPostList() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        /*List<IndexObject> bbsTopicListByDateistByDate = bbsPostRepository.getBbsPostListByDate(simpleDateFormat.parse("2016-01-01"), new Date());
        if (null != bbsTopicListByDateistByDate) {
            logger.info(bbsTopicListByDateistByDate.get(0).toString());
        }*/
    }

    @Test
    public void test_updateNice(){
        bbsService.updateTopicNice(1,76);
    }

    @Test
    public void test_updateDel(){
        bbsService.deleteTopic(76);
    }

    @Test
    public void test_getTopicsByModuleId(){
        Page<BbsTopic> topicsByModuleId = bbsService.getTopicsByModuleId(2, 1, 10);
        logger.info(topicsByModuleId.toString());
    }

    @Test
    public void test_getPosts(){
        List<BbsPost> postByTopicId = bbsService.getPostByTopicId(69);
        logger.info(postByTopicId.toString());
    }

    @Test
    public void test_deletePosts(){
         bbsService.deletePost(224);
    }

    @Test
    public void test_savePosts(){
        BbsPost bbsPost = new BbsPost();
        BbsUser user = new BbsUser();
        user.setId(1);
        bbsPost.setBbsUser(user);
        bbsPost.setTopicId(61);
        bbsPost.setContent("aaaaaaaaaaaaaaaaaaaaaaa");
        bbsPost.setCreateTime(new Date());
        bbsPost.setHasReply(0);
        bbsPost.setUpdateTime(new Date());
        bbsService.savePost(bbsPost);
    }
}

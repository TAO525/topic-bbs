package com.wayne;

import com.wayne.dao.BbsPostRepository;
import com.wayne.dao.BbsTopicRepository;
import com.wayne.model.BbsTopic;
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
        List<Object[]> bbsTopicListByDate = bbsTopicRepository.getBbsTopicListByDate(simpleDateFormat.parse("2016-01-01"), new Date());
//        List<IndexObject> bbsTopicListByDate2 = bbsTopicRepository.getBbsTopicListByDate2(simpleDateFormat.parse("2016-01-01"), new Date());
        if (null != bbsTopicListByDate) {
            logger.info(bbsTopicListByDate.get(0).toString());
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
}

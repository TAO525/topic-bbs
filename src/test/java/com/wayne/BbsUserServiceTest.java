package com.wayne;

import com.wayne.model.BbsUser;
import com.wayne.service.BbsUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author TAO
 * @Date 2017/3/24 15:14
 */
public class BbsUserServiceTest extends BaseServiceTest {

    @Autowired
    private BbsUserService bbsUserService;

    @Test
    public void test_getUserAccount() throws Exception {
        BbsUser bbsUser = bbsUserService.getUserAccount("xxx", "123");
        if (null != bbsUser) {
            logger.info(bbsUser.toString());
        }
    }

    @Test
    public void test_hasUser() throws Exception {
        Boolean result = bbsUserService.hasUser("xxx");
        logger.info(result.toString());
    }

    @Test
    public void test_setUserAccount() throws Exception {
        BbsUser user = new BbsUser();
        user.setPassword("1234567a");
        BbsUser result = bbsUserService.setUserAccount(user);
        logger.info(result.toString());
    }

    @Test
    public void test_addTopicScore() {
        bbsUserService.addTopicScore(1);
    }
}

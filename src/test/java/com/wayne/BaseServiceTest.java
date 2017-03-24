package com.wayne;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.logging.Logger;

/**
 * @Author TAO
 * @Date 2017/3/24 15:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TopicBbsApplication.class)
public class BaseServiceTest {

    protected static final Logger logger = Logger.getGlobal();
}

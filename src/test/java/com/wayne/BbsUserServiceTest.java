package com.wayne;

import com.wayne.dao.BbsModuleRepository;
import com.wayne.model.BbsModule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author TAO
 * @Date 2017/3/24 15:14
 */
public class BbsUserServiceTest extends BaseServiceTest {

    @Autowired
    private BbsModuleRepository bbsModuleRepository;

    @Test
    public void test_module() throws Exception {
        BbsModule module = bbsModuleRepository.getOne(1);
        logger.info(module.toString());
    }
}

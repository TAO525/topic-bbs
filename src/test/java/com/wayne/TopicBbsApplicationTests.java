package com.wayne;

import com.wayne.dao.BbsModuleRepository;
import com.wayne.dao.BbsUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicBbsApplicationTests {

	@Autowired
	private BbsUserRepository userRepository;

	@Autowired
	private BbsModuleRepository bbsModuleRepository;

	@Test
	public void test() throws Exception {
		System.out.println("================================="+userRepository.findAll().size());
	}

	@Test
	public void test_module

	{

	}
}

package com.wayne.service.impl;


import com.wayne.dao.BbsUserRepository;
import com.wayne.model.BbsUser;
import com.wayne.service.BbsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author TAO
 * @Date 2017/3/23 18:32
 */
@Service
public class BbsUserServiceImpl implements BbsUserService {

    @Autowired
    private BbsUserRepository bbsUserRepository;

    /**
     *
     * @param UserName
     * @return
     */
    @Override
    public BbsUser getUserByName(String UserName) {
        return bbsUserRepository.findByUserName(UserName);
    }
}
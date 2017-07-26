package com.wayne.service.impl;


import com.wayne.common.HashUtil;
import com.wayne.common.ScoreUtil;
import com.wayne.config.LevelConst;
import com.wayne.dao.BbsUserRepository;
import com.wayne.model.BbsUser;
import com.wayne.service.BbsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author TAO
 * @Date 2017/3/23 18:32
 */
@Service
public class BbsUserServiceImpl implements BbsUserService {

    @Autowired
    private BbsUserRepository bbsUserRepository;

    @Override
    @Transactional
    public BbsUser setUserAccount(BbsUser user) {
        String pwd = HashUtil.generatePwd(user.getPassword());
        user.setPassword(pwd);
        return bbsUserRepository.save(user);
    }

    @Override
    public BbsUser getUser(Integer userId) {
        return bbsUserRepository.findOne(userId);
    }

    @Override
    public BbsUser getUserAccount(String userName, String password) {
        String pwd = HashUtil.generatePwd(password);
        return bbsUserRepository.findByUserNameAndPassword(userName, pwd);
    }

    @Override
    public boolean hasUser(String userName) {
        return bbsUserRepository.existsByUserName(userName);
    }


    @Override
    public void addTopicScore(Integer userId) {
        addScore(userId, LevelConst.BBS_TOPIC_SCORE);
    }

    @Override
    public void addPostScore(Integer userId) {
        addScore(userId, LevelConst.BBS_TOPIC_SCORE);
    }

    @Override
    public void addReplayScore(Integer userId) {
        addScore(userId, LevelConst.BBS_TOPIC_SCORE);
    }

    private void addScore(Integer userId, int total) {
        BbsUser user = bbsUserRepository.findOne(userId);
        int score = user.getScore() + total;
        int balance = user.getBalance() + total;
        user.setScore(score);
        user.setBalance(balance);
        user.setLevel(ScoreUtil.getLevel(score));
        bbsUserRepository.save(user);
    }


}
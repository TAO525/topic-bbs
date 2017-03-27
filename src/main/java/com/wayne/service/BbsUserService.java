package com.wayne.service;

import com.wayne.model.BbsUser;
import org.springframework.stereotype.Service;

@Service
public interface BbsUserService {

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    BbsUser setUserAccount(BbsUser user);

    /**
     * 判断用户名是否存在
     *
     * @param userId
     * @return
     */
    BbsUser getUser(Integer userId);

    /**
     * 根据用户名和密码获取用户
     *
     * @param userName
     * @param password
     * @return
     */
    BbsUser getUserAccount(String userName, String password);

    /**
     * 判断用户名是否存在
     *
     * @param userName
     * @return
     */
    boolean hasUser(String userName);

    /**
     * 增加topic积分
     *
     * @param userId
     */
    void addTopicScore(Integer userId);


    /**
     * 增加Post积分
     *
     * @param userId
     */
    void addPostScore(Integer userId);

    /**
     * 增加Replay积分
     *
     * @param userId
     */
    void addReplayScore(Integer userId);

}

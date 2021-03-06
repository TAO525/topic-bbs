package com.wayne.dao;

import com.wayne.model.BbsUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsUserRepository extends JpaRepository<BbsUser, Integer> {

    BbsUser findByUserNameAndPassword(String userName, String password);

    boolean existsByUserName(String userName);
}

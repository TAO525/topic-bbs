package com.wayne.dao;

import com.wayne.model.BbsUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsUserRepository extends JpaRepository<BbsUser,Long>{
    /**
     * Find by name.
     *
     * @param userName the name
     * @return the user
     */
    BbsUser findByUserName(String userName);
}

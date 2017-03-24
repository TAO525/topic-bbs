package com.wayne.dao;

import com.wayne.model.BbsUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsPostRepository extends JpaRepository<BbsUser,Long>{
    
}

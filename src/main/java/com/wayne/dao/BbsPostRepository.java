package com.wayne.dao;

import com.wayne.model.BbsPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsPostRepository extends JpaRepository<BbsPost, Integer> {
    
}

package com.wayne.dao;

import com.wayne.model.BbsMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author TAO
 * @Date 2017/3/23 18:21
 */
public interface BbsMessageRepository extends JpaRepository<BbsMessage, Integer> {
    
}

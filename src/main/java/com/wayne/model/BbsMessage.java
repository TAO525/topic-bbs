package com.wayne.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @Author TAO
 * @Date 2017/3/24 10:40
 */
@Entity
@Table(name = "bbs_message")
public class BbsMessage extends BaseModel {
    @Id
	@GeneratedValue
	private Integer id ;
	private Integer status ;
	private Integer topicId ;
	private Integer userId ;
	
	public BbsMessage() {
	}
	
	public Integer getId(){
		return  id;
	}
	public void setId(Integer id ){
		this.id = id;
	}
	
	public Integer getStatus(){
		return  status;
	}
	public void setStatus(Integer status ){
		this.status = status;
	}
	
	public Integer getTopicId(){
		return  topicId;
	}
	public void setTopicId(Integer topicId ){
		this.topicId = topicId;
	}
	
	public Integer getUserId(){
		return  userId;
	}
	public void setUserId(Integer userId ){
		this.userId = userId;
	}
	
	
	

}

package com.wayne.model;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @Author TAO
 * @Date 2017/3/24 10:38
 */
@Entity
@Table(name = "bbs_reply")
public class BbsReply extends BaseModel {

	private Integer id ;
	private Integer postId ;
	private Integer topicId ;
	private String content ;
	private Date createTime ;

	private BbsUser bbsUser;

	@JoinColumn(name="user_id",unique = true)
	@ManyToOne()
	public BbsUser getBbsUser() {
		return bbsUser;
	}

	public void setBbsUser(BbsUser bbsUser) {
		this.bbsUser = bbsUser;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

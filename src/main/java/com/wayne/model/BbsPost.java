package com.wayne.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "bbs_post")
public class BbsPost extends BaseModel {

	private Integer id ;
	private Integer hasReply ;
	private Integer topicId ;
	private String content ;
	private Date createTime ;
	private Date updateTime ;

	private BbsUser bbsUser;
	private Set<BbsReply> replys;


	@JoinColumn(name="user_id",unique = true)
	@ManyToOne()
	public BbsUser getBbsUser() {
		return bbsUser;
	}

	public void setBbsUser(BbsUser bbsUser) {
		this.bbsUser = bbsUser;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)//级联保存、更新、删除、刷新;取消延迟加载
	@JoinColumn(name="postId")//在book表增加一个外键列来实现一对多的单向关联
	public Set<BbsReply> getReplys() {
		return replys;
	}

	public void setReplys(Set<BbsReply> replys) {
		this.replys = replys;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHasReply() {
		return hasReply;
	}

	public void setHasReply(Integer hasReply) {
		this.hasReply = hasReply;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

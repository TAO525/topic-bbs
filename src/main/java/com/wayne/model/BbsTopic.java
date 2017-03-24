package com.wayne.model;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @Author TAO
 * @Date 2017/3/24 10:37
 */
@Entity
@Table(name = "bbs_topic")
public class BbsTopic extends BaseModel {
    @Id
	@GeneratedValue
	private Integer id ;
	private Integer emotion ;
	private Integer isNice ;
	private Integer isUp ;
	private Integer moduleId ;
	private Integer postCount ;
	private Integer pv ;
	private Integer replyCount ;
	private Integer userId ;
	private String content ;
	private Date createTime ;

    @Transient
    private BbsUser user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmotion() {
		return emotion;
	}

	public void setEmotion(Integer emotion) {
		this.emotion = emotion;
	}

	public Integer getIsNice() {
		return isNice;
	}

	public void setIsNice(Integer isNice) {
		this.isNice = isNice;
	}

	public Integer getIsUp() {
		return isUp;
	}

	public void setIsUp(Integer isUp) {
		this.isUp = isUp;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getPostCount() {
		return postCount;
	}

	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public BbsUser getUser() {
		return user;
	}

	public void setUser(BbsUser user) {
		this.user = user;
	}

}

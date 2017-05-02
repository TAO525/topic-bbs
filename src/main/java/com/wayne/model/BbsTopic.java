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

	private Integer id ;
	private Integer emotion ;
	private Integer isNice ;
	private Integer isUp ;
	private Integer postCount ;
	private Integer pv ;
	private Integer replyCount ;
	private String content ;
	private Date createTime ;
	private Integer moduleId;

    private BbsUser bbsUser;
	private BbsModule bbsModule;

	@Id
	@GeneratedValue
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

	//一对一关系 unique = true
	@JoinColumn(name="user_id")
	@ManyToOne()
	public BbsUser getBbsUser() {
		return bbsUser;
	}

	public void setBbsUser(BbsUser bbsUser) {
		this.bbsUser = bbsUser;
	}

	@JoinColumn(name="module_id")
	@ManyToOne()
	public BbsModule getBbsModule() {
		return bbsModule;
	}

	public void setBbsModule(BbsModule bbsModule) {
		this.bbsModule = bbsModule;
	}

	@Transient
	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}


	/*@Transient
	public String getNiceDate() {
		Date date = this.createTime;
		if (null == date) return "";
		String result = null;
		long currentTime = new Date().getTime() - date.getTime();
		int time = (int)(currentTime / 1000);
		if(time < 60) {
			result = "刚刚";
		} else if(time >= 60 && time < 3600) {
			result = time/60 + "分钟前";
		} else if(time >= 3600 && time < 86400) {
			result = time/3600 + "小时前";
		} else if(time >= 86400 && time < 864000) {
			result = time/86400 + "天前";
		} else{
			result = MY_DATE_FORMAT.format(date);
		}
		return result;
	}*/
}

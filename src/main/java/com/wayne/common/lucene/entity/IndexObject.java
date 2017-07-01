package com.wayne.common.lucene.entity;


public class IndexObject implements Comparable<IndexObject>{
	
	private String topicId;
	private String content;
	private float score;//相似度
	private String postId; //用来标记是post的内容 以便lucene 维护
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public IndexObject() {
		super();
	}

	public IndexObject(String content) {
		this.content = content;
	}

	public IndexObject(String topicId, String content) {
		this.topicId = topicId;
		this.content = content;
	}

	public IndexObject(String topicId, String content, float score) {
		super();
		this.topicId = topicId;
		this.content = content;
		this.score = score;
	}
	@Override
	public int compareTo(IndexObject o) {
		if(this.score < o.getScore()){
			return 1;
		}else if(this.score > o.getScore()){
			return -1;
		}
		return 0;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
}

package com.ibiker.ibiker.Models;

public class UserComment {
	private String userId;
	private Long publishTimeStamp;
	private String commentText;
	private float rating;
	
	public UserComment() {
	}
	
	public UserComment(String userId, Long publishTimeStamp, String commentText, byte rating) {
		super();
		this.userId = userId;
		this.publishTimeStamp = publishTimeStamp;
		this.commentText = commentText;
		this.rating = rating;
	}
	
	public float getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Long getPublishTimeStamp() {
		return publishTimeStamp;
	}
	
	public void setPublishTimeStamp(Long publishTime) {
		this.publishTimeStamp = publishTime;
	}
	
	public String getCommentText() {
		return commentText;
	}
	
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
}

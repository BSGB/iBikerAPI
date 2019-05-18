package com.ibiker.ibiker.Models;

public class UserComment {
	private String userID;
	private Long publishTimeStamp;
	private String commentText;
	private byte rating;
	
	public UserComment() {
	}
	
	public UserComment(String userID, Long publishTimeStamp, String commentText, byte rating) {
		super();
		this.userID = userID;
		this.publishTimeStamp = publishTimeStamp;
		this.commentText = commentText;
		this.rating = rating;
	}
	
	public byte getRating() {
		return rating;
	}
	
	public void setRating(byte rating) {
		this.rating = rating;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public Long getPublishTime() {
		return publishTimeStamp;
	}
	
	public void setPublishTime(Long publishTime) {
		this.publishTimeStamp = publishTime;
	}
	
	public String getCommentText() {
		return commentText;
	}
	
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
}

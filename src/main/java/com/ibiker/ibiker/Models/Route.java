package com.ibiker.ibiker.Models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Document(collection = "routes")
public class Route {
	
	@Id
	@JsonIgnore
	private ObjectId id;
	
	@Transient
	private String stringifiedId;

	private String userId;
	private boolean isPublished;
	private Long startTimeStamp;
	private Long endTimeStamp;
	private Long deltaTime;
	private Float totalDistance;
	private List<LatLng> waypoints;
	private List<Float> averages;
	private List<RouteStop> stops;
	private String description;
	private Float difficulty;
	private String bikeType;
	private List<UserComment> comments;
	
	public Route() {
	}
	
	public Route(ObjectId id, String stringifiedId, String userId, boolean isPublished, Long startTimeStamp, Long endTimeStamp,
			Long deltaTime, Float totalDistance, List<LatLng> waypoints, List<Float> averages, List<RouteStop> stops, String description, Float difficulty,
			String bikeType, List<UserComment> comments) {
		super();
		this.id = id;
		this.stringifiedId = stringifiedId;
		this.userId = userId;
		this.isPublished = isPublished;
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
		this.deltaTime = deltaTime;
		this.totalDistance = totalDistance;
		this.waypoints = waypoints;
		this.averages = averages;
		this.stops = stops;
		this.description = description;
		this.difficulty = difficulty;
		this.bikeType = bikeType;
		this.comments = comments;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getStringifiedId() {
		return stringifiedId;
	}

	public void setStringifiedId(String stringifiedId) {
		this.stringifiedId = stringifiedId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Long getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public Long getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(Long endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	public Long getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(Long deltaTime) {
		this.deltaTime = deltaTime;
	}

	public Float getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Float totalDistance) {
		this.totalDistance = totalDistance;
	}

	public List<LatLng> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<LatLng> waypoints) {
		this.waypoints = waypoints;
	}

	public List<Float> getAverages() {
		return averages;
	}

	public void setAverages(List<Float> averages) {
		this.averages = averages;
	}

	public List<RouteStop> getStops() {
		return stops;
	}

	public void setStops(List<RouteStop> stops) {
		this.stops = stops;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Float difficulty) {
		this.difficulty = difficulty;
	}

	public String getBikeType() {
		return bikeType;
	}

	public void setBikeType(String bikeType) {
		this.bikeType = bikeType;
	}

	public List<UserComment> getComments() {
		return comments;
	}

	public void setComments(List<UserComment> comments) {
		this.comments = comments;
	}
}

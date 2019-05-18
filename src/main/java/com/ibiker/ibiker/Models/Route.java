package com.ibiker.ibiker.Models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "routes")
public class Route {
	
    @Id
    private ObjectId _id;

	private String userID;
	private boolean isPublished;
	private Long startTimeStamp;
	private Long endTimeStamp;
	private Long deltaTime;
	private Float totalDistance;
	private List<LatLng> waypoints;
	private List<Float> averages;
	private List<RouteStop> stops;
	private List<UserComment> comments;
	
	public Route() {
	}
	
	public Route(ObjectId id, String userID, boolean isPublished, Long startTimeStamp, Long endTimeStamp, Long deltaTime, Float totalDistance,
			List<LatLng> waypoints, List<Float> averages, List<RouteStop> stops, List<UserComment> comments) {
		this._id = id;
		this.userID = userID;
		this.isPublished = isPublished;
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
		this.deltaTime = deltaTime;
		this.totalDistance = totalDistance;
		this.waypoints = waypoints;
		this.averages = averages;
		this.stops = stops;
		this.comments = comments;
	}
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public boolean isPublished() {
		return isPublished;
	}
	
	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	
	public Long getStartTime() {
		return startTimeStamp;
	}
	
	public void setStartTime(Long startTime) {
		this.startTimeStamp = startTime;
	}
	
	public Long getEndTime() {
		return endTimeStamp;
	}
	
	public void setEndTime(Long endTime) {
		this.endTimeStamp = endTime;
	}
	
	public Long getTimeDelta() {
		return deltaTime;
	}
	
	public void setTimeDelta(Long timeDelta) {
		this.deltaTime = timeDelta;
	}
	
	public List<LatLng> getWaypoints() {
		return waypoints;
	}
	
	public void setWaypoints(List<LatLng> waypoints) {
		this.waypoints = waypoints;
	}
	
	public List<RouteStop> getStops() {
		return stops;
	}
	
	public void setStops(List<RouteStop> stops) {
		this.stops = stops;
	}
	
	public List<UserComment> getComments() {
		return comments;
	}
	
	public void setComments(List<UserComment> comments) {
		this.comments = comments;
	}

	public List<Float> getAverages() {
		return averages;
	}

	public void setAverages(List<Float> averages) {
		this.averages = averages;
	}

	public Float getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Float totalDistance) {
		this.totalDistance = totalDistance;
	}
}

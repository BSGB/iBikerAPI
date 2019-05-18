package com.ibiker.ibiker.Models;

public class RouteStop {
	
	private String imageString;
	private LatLng geoTag;
	
	public RouteStop() {
	}
	
	public RouteStop(String imageString, LatLng geoTag) {
		super();
		this.imageString = imageString;
		this.geoTag = geoTag;
	}
	
	public String getImageString() {
		return imageString;
	}
	
	public void setImageString(String imageString) {
		this.imageString = imageString;
	}
	
	public LatLng getGeoTag() {
		return geoTag;
	}
	
	public void setGeoTag(LatLng geoTag) {
		this.geoTag = geoTag;
	}
}

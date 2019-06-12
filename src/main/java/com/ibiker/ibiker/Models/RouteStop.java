package com.ibiker.ibiker.Models;

import org.springframework.data.annotation.Transient;

public class RouteStop {
	
	@Transient
	private String imageString;
	private String imageName;
	private LatLng geoTag;
	
	public RouteStop() {
	}
	
	public RouteStop(String imageString, String imageName, LatLng geoTag) {
		super();
		this.imageString = imageString;
		this.imageName = imageName;
		this.geoTag = geoTag;
	}
	
	public String getImageString() {
		return imageString;
	}
	
	public void setImageString(String imageString) {
		this.imageString = imageString;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public LatLng getGeoTag() {
		return geoTag;
	}
	
	public void setGeoTag(LatLng geoTag) {
		this.geoTag = geoTag;
	}
}

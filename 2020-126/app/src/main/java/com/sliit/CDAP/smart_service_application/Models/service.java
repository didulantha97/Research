package com.sliit.CDAP.smart_service_application.Models;

public class service {

    public String username;
    public String category, subCategory;
    public String priority;
    public String description;
    public String longitude;
    public String latitude;

    public service() {
    }

    public service(String username, String category, String subCategory, String priority, String description, String longitude, String latitude) {
        this.username = username;
        this.category = category;
        this.subCategory = subCategory;
        this.priority = priority;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}

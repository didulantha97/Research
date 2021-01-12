package com.sliit.CDAP.smart_service_application.Models;

public class message {
    public String username;
    public String message;
    public int list;
    public String response;
    public boolean left;

    public message() { }

    public message(String username, String message,int list, boolean left) {
        this.username = username;
        this.message = message;
        this.list = list;
        this.left = left;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public int getList() { return list; }

    public void setList(int list) { this.list = list; }
}

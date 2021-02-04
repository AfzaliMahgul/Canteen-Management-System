package com.example.rty.quickmeal;

public class Feedback {
    String userName;
    String userFeedback;

    public Feedback(String userName, String userFeedback) {
        this.userName = userName;
        this.userFeedback = userFeedback;

    }

    public Feedback() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userName) {
        this.userFeedback = userFeedback;
    }
}
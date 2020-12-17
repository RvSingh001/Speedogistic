package com.e.speedogistic.ui.userfeedback;

import java.io.Serializable;

class userFeedbackModel implements Serializable {
    String email,feedback,logemail,name,rate;

    public userFeedbackModel() {
    }

    public userFeedbackModel(String email, String feedback, String logemail, String name, String rate) {
        this.email = email;
        this.feedback = feedback;
        this.logemail = logemail;
        this.name = name;
        this.rate = rate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getLogemail() {
        return logemail;
    }

    public void setLogemail(String logemail) {
        this.logemail = logemail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}

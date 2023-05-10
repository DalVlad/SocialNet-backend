package com.practice.SocialNetback.queris.response;

import java.util.Date;

public class MessageInfoResponse {
    private Long id;
    private String message;
    private Date dateOfDispatch;
    public Long userId;
    public String username;

    public MessageInfoResponse(Long id, String message, Date dateOfDispatch, Long userId, String username) {
        this.id = id;
        this.message = message;
        this.dateOfDispatch = dateOfDispatch;
        this.userId = userId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateOfDispatch() {
        return dateOfDispatch;
    }

    public void setDateOfDispatch(Date dateOfDispatch) {
        this.dateOfDispatch = dateOfDispatch;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }
}

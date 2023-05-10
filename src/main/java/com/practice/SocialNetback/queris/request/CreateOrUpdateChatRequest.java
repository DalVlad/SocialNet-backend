package com.practice.SocialNetback.queris.request;

import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateOrUpdateChatRequest {
    @Size(min = 3, max = 30)
    private String nameChat;

    private List<String> users;

    public String getNameChat() {
        return nameChat;
    }

    public void setNameChat(String nameChat) {
        this.nameChat = nameChat;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}

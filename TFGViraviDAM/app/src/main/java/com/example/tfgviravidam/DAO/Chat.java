package com.example.tfgviravidam.DAO;

import java.io.Serializable;
import java.util.List;

public class Chat implements Serializable {

    private String id;
    private String name;
    private List<String> users;
    private List<Message> messages;

    public Chat() {}

    public Chat(String id, String name, List<String> users, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}


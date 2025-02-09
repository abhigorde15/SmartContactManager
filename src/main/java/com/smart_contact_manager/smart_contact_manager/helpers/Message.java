package com.smart_contact_manager.smart_contact_manager.helpers;

public class Message {
    private String content;
    private MessageType type;

    // ✅ Add this constructor
    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    // ✅ Add getters if needed
    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    // ✅ Optional: Add setters if needed
    public void setContent(String content) {
        this.content = content;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}

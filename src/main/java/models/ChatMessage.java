package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {
    private String sender;
    private String message;
    private LocalDateTime time;

    public ChatMessage(String sender, String message, LocalDateTime time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }
    public boolean isImage() {
        // 假设图片格式以 ".png"、".jpg" 或 ".gif" 结尾
        return message != null && (message.toLowerCase().endsWith(".png") || message.toLowerCase().endsWith(".jpeg") || message.toLowerCase().endsWith(".gif"));
    }
}

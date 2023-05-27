package service;

/**
 * @auther 齿轮
 * @create 2023-05-21-21:20
 */
public interface transmit {
    void init();
    void login(String name, String password);
    void sendMessage(String name,String message);
    void sendFile(String goalname,String filePath);
    void addFriend(String username,String addFriendMessage);
}

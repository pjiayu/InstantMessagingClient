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
    void createGroup(String groupName);
    void joinGroup(String groupName);
    void getAllContentMsg(String send_name,String target_name);
}

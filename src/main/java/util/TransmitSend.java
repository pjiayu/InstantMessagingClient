package util;

/**
 * @auther 齿轮
 * @create 2023-05-20-8:59
 */
public interface TransmitSend {
    void sendMessage(String goalName,String message);
    void sendFile(String goalName,String filePath);
    void addFriend(String friendName,String addFriendMessage);
    void createGroup(String groupName);
    void getAllContentMsg(String send_user,String target_user);
}

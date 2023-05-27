package util;

/**
 * @auther 齿轮
 * @create 2023-05-20-8:58
 */
public interface RollBack {
    void LoginSuccess();
    void LoginFailure();
    void getFriendsSuccess();
    void Receive(String name,String message);
    void ReceiveFile(String name,String filePath);

    void addFriend(String name, String toString);
}

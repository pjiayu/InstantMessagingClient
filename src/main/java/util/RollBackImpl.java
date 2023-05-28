package util;

import java.time.LocalDateTime;

/**
 * @auther 齿轮
 * @create 2023-05-20-10:45
 */
public class RollBackImpl implements RollBack{
    @Override
    public void LoginSuccess() {
        System.out.println("success");
    }

    @Override
    public void LoginFailure() {
        System.out.println("Failure");
    }

    @Override
    public void getFriendsSuccess() {
        Utils.friends.forEach(System.out::println);
    }

    @Override
    public void Receive(String name, String message, LocalDateTime time) {

    }


    @Override
    public void ReceiveFile(String name, String filePath) {

    }

    @Override
    public void addFriend(String name, String toString) {

    }
}

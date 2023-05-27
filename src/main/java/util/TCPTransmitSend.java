package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther 齿轮
 * @create 2023-05-20-19:56
 */
public class TCPTransmitSend implements TransmitSend, Runnable {

    String goalName;
    String message;
    String filePath;
    String addFriendMessage;
    Type type;

    public TCPTransmitSend(String goalName) {
        this.goalName = goalName;
    }

    public void setMessage(String message) {
        type = Type.OneLineMessage;
        this.message = message;
    }

    public void setFileName(String filePath) {
        type = Type.File;
        this.filePath = filePath;
    }
    public void setAddFriendMessage(String addFriendMessage){
        type = Type.addFriend;
        this.addFriendMessage=addFriendMessage;
    }
    @Override
    public void run() {
        switch (type) {
            case OneLineMessage:
                sendMessage(goalName, message);
                break;
            case File:
                sendFile(goalName, filePath);
                break;
            case addFriend:
                addFriend(goalName,addFriendMessage);
        }
    }

    @Override
    public void addFriend(String friendName,String addFriendMessage){
        Utils.pw.println("addFriend");
        Utils.pw.println(friendName);//第二行为目的用户
        Utils.pw.println(addFriendMessage);//添加好友的信息
        Utils.pw.println("bye");//代表发送结束
    };
    @Override
    public void sendMessage(String goalName, String message) {
        Utils.pw.println("OneLineMessage");
        Utils.pw.println(goalName);//第二行为目的用户
        Utils.pw.println(message);//之后的都是信息
        Utils.pw.println("bye");//代表发送结束
    }

    @Override
    public void sendFile(String goalName, String filePath) {
        //获得文件名
        int index = 0;
        for (int i = filePath.length() - 1; i >= 0; i--) {
            if (filePath.charAt(i) == '\\' || filePath.charAt(i) == '/') {
                index = i;
                break;
            }
        }
        //获得文件长度并创建流资源
        File file = null;
        FileInputStream fis = null;
        try {
            file = new File(filePath);
            fis = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String FileName = filePath.substring(index+1);
        Utils.pw.println("File");
        Utils.pw.println(goalName);//第二行为目的用户
        Utils.pw.println(FileName);//第三行为文件名
        Utils.pw.println(file.length());//第四行为文件长度

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //开始发送
        try {
            byte[] buffer = new byte[1024];
            long totalLen = 0;
            int len;
            while ((len = fis.read(buffer)) != -1) {
                Utils.outputStream.write(buffer, 0, len);
                totalLen+=len;
            }
            System.out.println(totalLen);
//            Thread.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.pw.println("bye");//本次会话结束

        try {
            fis.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}

enum Type {
    OneLineMessage("OneLineMessage"),
    File("File"),
    addFriend("addFriend");
    private final String type;

    Type(String type) {
        this.type = type;
    }
}
package service;

import util.*;

/**
 * @auther 齿轮
 * @create 2023-05-21-21:21
 */
public class TransmitImpl implements transmit {
    private RollBack rollBack;

    public TransmitImpl(){}

    public TransmitImpl(RollBack rollBack) {
        this.rollBack = rollBack;
    }

    @Override
    public void init() {
        Utils.init();
    }

    @Override
    public void login(String name, String password) {
        ClientLoginImpl clientLogin = new ClientLoginImpl(name, password, rollBack);
        Thread thread = new Thread(clientLogin);
        thread.start();
    }

    @Override
    public void sendMessage(String name, String message) {
        TCPTransmitSend tcpTransmit = new TCPTransmitSend(name);
        tcpTransmit.setMessage(message);
        Thread thread2 = new Thread(tcpTransmit);
        thread2.start();
    }

    @Override
    public void sendFile(String goalname, String filePath) {
        TCPTransmitSend tcpTransmit = new TCPTransmitSend(goalname);
        tcpTransmit.setFileName(filePath);
        Thread thread2 = new Thread(tcpTransmit);
        thread2.start();
    }

    @Override
    public void addFriend(String friendName, String addFriendMessage) {
        TCPTransmitSend tcpTransmit = new TCPTransmitSend(friendName);
        tcpTransmit.setAddFriendMessage(addFriendMessage);
        Thread thread3 = new Thread(tcpTransmit);
        thread3.start();
    }

    @Override
    public void createGroup(String groupName) {
        TCPTransmitSend tcpTransmit = new TCPTransmitSend();
        tcpTransmit.setCreateGroup(groupName);
        Thread thread4 = new Thread(tcpTransmit);
        thread4.start();
    }

    @Override
    public void joinGroup(String joinGroupName) {
        TCPTransmitSend tcpTransmit = new TCPTransmitSend();
        tcpTransmit.setJoinGroupName(joinGroupName);
        Thread thread5 = new Thread(tcpTransmit);
        thread5.start();
    }


    @Override
    public void getAllContentMsg(String send_name, String target_name) {
        TCPTransmitSend tcpTransmit = new TCPTransmitSend();
        tcpTransmit.setSend_name(send_name);
        tcpTransmit.setTarget_id(target_name);
//        tcpTransmit.getAllContentMsg(send_name,target_name);
        Thread thread5 = new Thread(tcpTransmit);
        thread5.start();
    }


}

package util;

import java.io.*;
import java.net.Socket;

/**
 * @auther 齿轮
 * @create 2023-05-20-10:20
 * 登录目的：建立Socket，准备后续的传输
 * 登录方式：使用Utils的init方法
 * 然后使用ClientLoginImpl的login方法
 * 也可以使用多线程，初始化一个ClientLoginImpl然后thread.start
 * 登录成功会调用RollBack.LoginSuccess()
 * 登录失败会调用RollBack.LoginFailure()
 */
public class ClientLoginImpl implements ClientLogin, Runnable {
    String name;
    String password;
    Socket clientSocket;
    RollBack rollBack;
    BufferedReader br;
    PrintWriter pw;
    boolean flag = true;

    public ClientLoginImpl(String name, String password, RollBack rollBack) {
        this.name = name;
        this.password = password;
        this.rollBack = rollBack;
        clientSocket = Utils.clientSocket;
        br = Utils.br;
        pw = Utils.pw;
    }

    public ClientLoginImpl(String name, String password, RollBack rollBack, Socket clientSocket) throws IOException {
        this.name = name;
        this.password = password;
        this.clientSocket = clientSocket;
        this.rollBack = rollBack;
        this.br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.pw = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void login() {
        try {
            pw.println(name);
            pw.println(password);
            pw.println("bye");

            String s1 = br.readLine();
            if ("Success".equals(s1)) {
                rollBack.LoginSuccess();
            } else if ("Failure".equals(s1)) {
                flag = false;
                rollBack.LoginFailure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFriends() {
        try {
            String line;
            boolean flag = false;
            while ((line = br.readLine()) != null) {
                if ("friends".equals(line) && !flag) {
                    flag = true;
                    continue;
                } else if ("bye".equals(line)) {
                    break;
                }
                if (flag) {
                    Utils.friends.add(line);
                }
            }
            pw.println("friends:bye");
            rollBack.getFriendsSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        login();
        if (flag) {
            getFriends();
            TCPTransmitReceive tcpTransmitReceive = new TCPTransmitReceive(rollBack);
            Thread thread = new Thread(tcpTransmitReceive);
            thread.start();
        }
    }
}

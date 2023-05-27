package util;

import Handler.Handler;
import Handler.HandlerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * @auther 齿轮
 * @create 2023-05-20-23:31
 */
public class TCPTransmitReceive implements TransmitReceive, Runnable {
    Socket socket;
    RollBack rollBack;
    BufferedReader br;
    Handler handler;

    public TCPTransmitReceive(RollBack rollBack) {
        this.socket = Utils.clientSocket;
        this.br = Utils.br;
        this.rollBack = rollBack;
        handler = new HandlerImpl(socket, rollBack, br);
    }

    @Override
    public void run() {
        receive();
    }

    @Override
    public void receive() {
        try {
            handler.handle();

        } catch (IOException e) {//报错意味着连接中断
            System.out.println("连接中断");
            e.printStackTrace();
        }
    }
}

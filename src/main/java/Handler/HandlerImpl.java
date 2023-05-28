package Handler;

import models.contentMsg;
import util.RollBack;
import util.Utils;
import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther 齿轮
 * @create 2023-05-21-22:37
 */
public class HandlerImpl implements Handler {
    Socket socket;
    RollBack rollBack;
    BufferedReader br;

    public HandlerImpl(Socket socket, RollBack rollBack, BufferedReader br) {
        this.socket = socket;
        this.rollBack = rollBack;
        this.br = br;
    }

    public void handle() throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals("OneLineMessage")) {
                OneLineMessage();
            } else if (line.equals("File")) {
                File();
            }else if (line.equals("addFriendMessage")) {
                addFriendMessage();
            }else if (line.equals("getAllContentMsg")) {
                getAllContentMsg();
            }
        }
    }

    private void getAllContentMsg()throws IOException{
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
                if (count == 0) {
                count++;
                Utils.builder.append(line);
            } else if (count == 1 && "bye".equals(line)) {//接收完成，进行处理
                String jsonString = Utils.builder.toString();
                List<contentMsg> tmpcontentMsg = JSON.parseArray(jsonString, contentMsg.class);
                for (contentMsg msg : tmpcontentMsg) {
                    // 访问contentMsg对象的属性和方法
                    rollBack.Receive(String.valueOf(msg.getSend_id()), msg.getContent_msg(),msg.getSend_time());
                    // 进行其他操作
                }
                Utils.builder = new StringBuilder();
                return;
            }
        }
    }

    private void File() throws IOException {
        String line = null;
        String sourceName = null;
        String fileName = null;
        String length = null;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (count == 0) {//获得来源用户名
                count++;
                sourceName = line;
            } else if (count == 1) {//获得文件名
                count++;
                fileName = line;
                System.out.println(fileName);
            } else if (count == 2) {//获得文件长度
                count++;
                length = line;
                //获得文件名后立即开始接受
                fileName = Utils.storePath + fileName;//拼接获得存储文件路径
                long Length = Long.parseLong(length);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(fileName);
                    byte[] buffer;
                    if (Length < 1024) {
                        buffer = new byte[(int) Length];
                    } else {
                        buffer = new byte[1024];
                    }
                    int len = 0;
                    long totalLen = 0;
                    InputStream inputStream = Utils.inputStream;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        totalLen += len;
                        if (totalLen == Length) {
                            System.out.println("served：" + totalLen);
                            break;
                        } else if (Length - totalLen < buffer.length && (Length - totalLen < 1024)) {//Length - totalLen为接收区还需要接受的剩余量
                            buffer = new byte[(int) (Length - totalLen)];
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (count == 3 && line.equals("bye")) {
                rollBack.ReceiveFile(sourceName, fileName);
                return;
            }
        }
    }

    private void OneLineMessage() throws IOException {
        String line;
        String name = null;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (count == 0) {
                count++;
                name = line;
            } else if (count == 2 && "bye".equals(line)) {//接收完成，进行处理
                rollBack.Receive(name, Utils.builder.toString(), LocalDateTime.now());
                Utils.builder = new StringBuilder();
                return;
            } else if (count == 1) {
                count++;
                Utils.builder.append(line);
            }
        }
    }
    private void addFriendMessage()throws IOException{
        String line;
        String name = null;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (count == 0) {
                count++;
                name = line;
            } else if (count == 2 && "bye".equals(line)) {//接收完成，进行处理
                rollBack.addFriend(name, Utils.builder.toString());
                Utils.builder = new StringBuilder();
                return;
            } else if (count == 1) {
                count++;
                Utils.builder.append(line);
            }
        }
    }
}

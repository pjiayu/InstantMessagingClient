package util;

import com.wellread4man.instantmessagingclient.Newfriends;
import javafx.scene.Group;
import service.TransmitImpl;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther 齿轮
 * @create 2023-05-20-10:56
 */
public class Utils {
    public static Socket clientSocket;
    public static InputStream inputStream;
    public static OutputStream outputStream;
    public static BufferedReader br;
    public static PrintWriter pw;
    public static List<String> friends = new ArrayList<>();
    public static List<String> groups = new ArrayList<>();
    public static StringBuilder builder = new StringBuilder();
    public static String storePath = "D:\\tempFile\\1\\";


    public static void init() {
        try {
            clientSocket = new Socket("127.0.0.1", 20001);
            outputStream = clientSocket.getOutputStream();
            inputStream = clientSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            pw = new PrintWriter(outputStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

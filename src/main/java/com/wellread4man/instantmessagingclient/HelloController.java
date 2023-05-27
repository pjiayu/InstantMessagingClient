package com.wellread4man.instantmessagingclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ChatMessage;
import service.TransmitImpl;
import util.*;

import java.time.LocalDateTime;

public class HelloController {
    private Main mainController;
    @FXML
    public Button login;
    @FXML
    private Text logins;

    @FXML
    private TextField name;


    @FXML
    private TextField password;
    @FXML
    private Text others;

    TransmitImpl transmit=null;

    @FXML
    void onLoginClick(ActionEvent event) {
        String name1 = name.getText();
        String password1 = password.getText();
        transmit= new TransmitImpl(new RollBack() {
            @Override
            public void LoginSuccess() {
                logins.setText("LoginSuccess");
            }

            @Override
            public void LoginFailure() {
                logins.setText("LoginFailure");
            }
            @Override
            public void getFriendsSuccess() {
                try {
                    // 加载主页面的FXML文件
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                    Parent root = loader.load();

                    // 获取主页面的控制器
                    mainController = loader.getController();

                    mainController.transmit = transmit;
                    mainController.setUserInfo(name1);
                    Platform.runLater(() -> {
                        // 创建新的场景和舞台
                        Scene mainScene = new Scene(root);
                        Stage mainStage = new Stage();
                        mainStage.setScene(mainScene);
                        mainStage.setTitle("主页面");

                        // 关闭登录页面舞台，显示主页面舞台
                        Stage loginStage = (Stage) login.getScene().getWindow();
                        Utils.friends.forEach(mainController.contactListView.getItems()::add);
                        //需要用群聊接口
//                        Utils.friends.forEach(mainController.groupListView.getItems()::add);
                        mainStage.show();
//                        mainStage.getIcons().add(new Image(Main.class.getResource("").toString()));
                        loginStage.close();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Receive(String name, String message,LocalDateTime time) {
                System.out.println(name+":"+message);
                Platform.runLater(()->{

                    mainController.chatListView.getItems().add(new ChatMessage(name,message,time));

                });
            }

            @Override
            public void ReceiveFile(String name, String filePath) {
                System.out.println(name+":"+filePath);
                Platform.runLater(()->{
                    mainController.chatListView.getItems().add(new ChatMessage(name,filePath,LocalDateTime.now()));
                });
            }
            @Override
            public void addFriend(String name, String addFriendMessage) {
                Platform.runLater(()->{
                mainController.contactListView.getItems().add(name);});
//                others.setText(others.getText()+"\n"+name+":"+addFriendMessage);
                System.out.println("添加好友成功");
            }
        });
        transmit.init();
        transmit.login(name1,password1);
    }
}
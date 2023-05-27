package com.wellread4man.instantmessagingclient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ChatListCell;
import models.ChatMessage;
import service.TransmitImpl;
import util.Utils;


import java.io.File;
import java.time.LocalDateTime;


public class Main {
    @FXML
    public TextField username;
    public ListView chatListView;
    @FXML
    public ListView<String> contactListView;
    @FXML
    public Button file;
    @FXML
    public Button newFriends;
    @FXML
    private TextField message;
    TransmitImpl transmit=null;

    @FXML
    void onSendClick(MouseEvent event) {
        String selectedContact = contactListView.getSelectionModel().getSelectedItem();
        System.out.println(selectedContact);
        transmit.sendMessage(selectedContact,message.getText());
        chatListView.getItems().add(
                new ChatMessage(username.getText(), message.getText(), LocalDateTime. now())
        );
    }

    public void handleContactDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedContact = contactListView.getSelectionModel().getSelectedItem();
            System.out.println("双击联系人：" + selectedContact);

//          getChatHistory(selectedContact);
        }
    }
    public void setUserInfo(String name1){
        username.setText(name1);
    }
    private void getChatHistory(String contact) {
        // 根据联系人获取消息记录的逻辑
        // 在这里根据contact执行相关操作，例如从数据库或其他数据源获取与所选联系人相关的消息记录
        // 然后可以将消息记录显示在另一个控件中，如TextArea或ListView
        // 你可以根据具体需求自行实现获取消息记录的逻辑
    }
    public void initialize() {
        // 设置单元格工厂来自定义列表项的外观和布局
        chatListView.setCellFactory(listView -> new ChatListCell());
    }
    // 点击文件图标的事件处理函数
    @FXML
    private void handleFileIconClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");

        // 设置文件选择的起始目录（可选）
        fileChooser.setInitialDirectory(new File("."));

        // 添加文件类型过滤器（可选）
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("所有文件", "*.*"),
                new FileChooser.ExtensionFilter("文本文件", "*.txt"),
                new FileChooser.ExtensionFilter("图像文件", "*.png", "*.jpg", "*.gif")
                // 添加更多的文件类型过滤器...
        );

        // 显示文件选择对话框
        Stage stage = (Stage) file.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // 处理选择的文件
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            String selectedContact = contactListView.getSelectionModel().getSelectedItem();
            // 执行文件发送操作，将 filePath 作为文件路径传递给发送函数
            message.setText(filePath);
            transmit.sendFile(selectedContact,filePath);
        }
    }

    public void newFriend(MouseEvent mouseEvent) {
        try {
            // 加载主页面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newfriends.fxml"));
            Parent root = loader.load();

            // 获取主页面的控制器
            Newfriends newfriendsController = loader.getController();
            newfriendsController.transmit = this.transmit;
//            mainController.transmit = transmit;
            Platform.runLater(() -> {
                // 创建新的场景和舞台
                Scene newScene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(newScene);
                newStage.setTitle("添加好友页面");

                // 关闭登录页面舞台，显示主页面舞台
//                Stage loginStage = (Stage) newFriends.getScene().getWindow();
//                Utils.friends.forEach(mainController.contactListView.getItems()::add);
                newStage.show();
//                        mainStage.getIcons().add(new Image(Main.class.getResource("").toString()));
//                loginStage.close();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

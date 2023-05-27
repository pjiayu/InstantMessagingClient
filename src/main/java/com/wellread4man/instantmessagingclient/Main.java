package com.wellread4man.instantmessagingclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ChatListCell;
import models.ChatMessage;
import service.TransmitImpl;


import java.io.File;
import java.io.InputStream;
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
    public Button emoji;
    @FXML
    public ListView<String> groupListView;
    @FXML
    private TextField message;
    TransmitImpl transmit=null;

    @FXML
    void onSendClick(MouseEvent event) {
        String selectedContact = contactListView.getSelectionModel().getSelectedItem();
        String selectedGroup = groupListView.getSelectionModel().getSelectedItem();
        if(selectedContact != ""){
            transmit.sendMessage(selectedContact,message.getText());
        }
        else if(selectedGroup != ""){
            transmit.sendMessage(selectedGroup,message.getText());
        }
        chatListView.getItems().add(
                new ChatMessage(username.getText(), message.getText(), LocalDateTime.now())
        );
    }

    public void handleContactDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            chatListView.getItems().clear();
            chatListView.scrollTo(chatListView.getItems().size() - 1);
            String selectedContact = contactListView.getSelectionModel().getSelectedItem();
            System.out.println("双击联系人：" + selectedContact);
            try {
                transmit.getAllContentMsg(selectedContact,username.getText());
            }catch (Exception e){
                e.printStackTrace();
            }
//            chatListView.setCellFactory(listView -> getChatHistory(selectedContact));

        }
    }
    public void setUserInfo(String name1){
        username.setText(name1);
    }
//    private ListView<ChatMessage> getChatHistory(String contact) {
//        // 根据联系人获取消息记录的逻辑
//        // 在这里根据contact执行相关操作，例如从数据库或其他数据源获取与所选联系人相关的消息记录
//        // 然后可以将消息记录显示在另一个控件中，如TextArea或ListView
//        // 你可以根据具体需求自行实现获取消息记录的逻辑
//
//    }
    public void initialize() {
        // 设置单元格工厂来自定义列表项的外观和布局
        chatListView.setCellFactory(listView -> new ChatListCell());
    }

    @FXML
    void emojochoice(ActionEvent event) {
        // 创建表情包选择对话框
        Dialog<String> emojiDialog = createEmojiDialog();

        // 显示表情包选择对话框，并等待用户选择表情包

        String selectedEmoji = emojiDialog.showAndWait().orElse(null);

        if (selectedEmoji != null) {
            // 将选择的表情包插入到聊天框中
            insertEmoji(selectedEmoji);
            String selectedContact = contactListView.getSelectionModel().getSelectedItem();
            String selectedGroup = groupListView.getSelectionModel().getSelectedItem();
            if(selectedContact != ""){
                transmit.sendMessage(selectedContact,message.getText());
            }
            else if(selectedGroup != ""){
                transmit.sendMessage(selectedGroup,message.getText());
            }
            chatListView.getItems().add(
                    new ChatMessage(username.getText(), message.getText(), LocalDateTime. now())
            );
            message.setText("");
        }
    }
    private Dialog<String> createEmojiDialog() {
        Dialog<String> emojiDialog = new Dialog<>();
        // 创建表情包选择对话框

            emojiDialog.setTitle("选择表情包");

            // 创建表情包选择界面
            FlowPane emojiPane = new FlowPane();
            emojiPane.setPrefSize(400, 400);
            emojiPane.setHgap(5);
            emojiPane.setVgap(5);

            // 加载并显示所有表情包图像
            for (int i = 1; i <= 5; i++) {
                String emojiPath = "/emoji/" + i + ".jpeg";
                InputStream inputStream = getClass().getResourceAsStream(emojiPath);
                assert inputStream != null;
                Image emojiImage = new Image(inputStream);
                ImageView emojiImageView = new ImageView(emojiImage);
                emojiImageView.setFitWidth(50);
                emojiImageView.setFitHeight(50);
                emojiImageView.setOnMouseClicked(event -> {
                    // 用户点击表情包图像时，关闭对话框并返回选中的表情包
                    emojiDialog.setResult("C:/Users/Chenqiyang/InstantMessagingClient/src/main/resources"+emojiPath);
                    emojiDialog.close();
                });
                emojiPane.getChildren().add(emojiImageView);
            }
            emojiDialog.getDialogPane().setContent(emojiPane);

        return emojiDialog;
    }

    private void insertEmoji(String emojiPath) {
        // 将选择的表情包插入到聊天框中
        // 在这里可以根据需要，将表情包的图像路径或其他标识添加到聊天框的文本中

        message.appendText(emojiPath);
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
        chatListView.getItems().add(
                new ChatMessage(username.getText(), message.getText(), LocalDateTime. now())
        );
    }

    public void newFriend(MouseEvent mouseEvent) {
        try {
            // 加载主页面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newfriends.fxml"));
            Parent root = loader.load();

            // 获取主页面的控制器
            Newfriends newfriendsController = loader.getController();
            newfriendsController.transmit = this.transmit;
            Platform.runLater(() -> {
                // 创建新的场景和舞台
                Scene newScene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(newScene);
                newStage.setTitle("添加好友页面");

                newStage.show();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGroupDoubleClick(MouseEvent event) {
        //打开群聊界面
        if (event.getClickCount() == 2) {
            String selectedContact = groupListView.getSelectionModel().getSelectedItem();
            System.out.println("双击联系人：" + selectedContact);
//            chatListView.setCellFactory(listView -> getChatHistory(selectedContact));
        }
    }
}

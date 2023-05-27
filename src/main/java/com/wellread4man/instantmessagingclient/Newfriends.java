package com.wellread4man.instantmessagingclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.TransmitImpl;

public class Newfriends {
    @FXML
    public Button newgroup;
    @FXML
    public TextField joinGroupName;
    TransmitImpl transmit=null;
    @FXML
    public TextField friendName;

    @FXML
    public void addFriend(ActionEvent actionEvent) {
        System.out.println(friendName.getText());
        transmit.addFriend(friendName.getText(),"添加好友");

    }
    public void newgroup(MouseEvent event) {
        try {
            // 加载主页面的FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newgroup.fxml"));
            Parent root = loader.load();

            // 获取创建群组页面的控制器
            Newgroup newgroupController = loader.getController();
            newgroupController.transmit=this.transmit;

            Platform.runLater(() -> {
                // 创建新的场景和舞台
                Scene newgroupScene = new Scene(root);
                Stage newgroupStage = new Stage();
                newgroupStage.setScene(newgroupScene);
                newgroupStage.setTitle("创建群组页面");

                newgroupStage.show();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void joinGroup(ActionEvent actionEvent) {
        System.out.println("要加入的群聊名字："+joinGroupName.getText());
        transmit.joinGroup(joinGroupName.getText());

    }
}

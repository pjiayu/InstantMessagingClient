package com.wellread4man.instantmessagingclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.TransmitImpl;

public class Newgroup {
    @FXML
    public TextField groupName;
    TransmitImpl transmit=null;

    @FXML
    public void createGroup(ActionEvent actionEvent) {
        System.out.println("输入的群组名字："+groupName.getText());
        transmit.createGroup(groupName.getText());
    }


}

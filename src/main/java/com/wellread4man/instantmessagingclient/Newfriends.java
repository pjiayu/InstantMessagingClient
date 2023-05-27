package com.wellread4man.instantmessagingclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.TransmitImpl;

public class Newfriends {
    private Main main;
    TransmitImpl transmit=null;
    @FXML
    public TextField friendName;
    public void setMain(Main main){
        this.main = main;
    }

    @FXML
    public void addFriend(ActionEvent actionEvent) {
        transmit=new TransmitImpl();
        System.out.println(friendName.getText());
        transmit.addFriend(friendName.getText(),"添加好友");
//        main.contactListView.refresh();
    }
}

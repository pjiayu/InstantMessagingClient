module com.wellread4man.instantmessagingclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wellread4man.instantmessagingclient to javafx.fxml;
    exports com.wellread4man.instantmessagingclient;
}
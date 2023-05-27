module com.wellread4man.instantmessagingclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires fastjson;

    opens models to fastjson;
    opens com.wellread4man.instantmessagingclient to javafx.fxml;
    exports com.wellread4man.instantmessagingclient;
    exports models;
}
package models;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ChatListCell extends ListCell<ChatMessage> {
    private VBox container;
    private HBox messageContainer;

    private Text messageText;

    private Text senderText;

    private Text timeText;

    public ChatListCell() {
        // 创建列表项的容器和布局
        container = new VBox();
        messageContainer = new HBox();

        // 创建消息内容的控件
        senderText = new Text();
        messageText = new Text();
        timeText = new Text();

        // 将消息内容的控件添加到消息容器中
        messageContainer.getChildren().addAll(senderText, messageText, timeText);

        // 将消息容器添加到列表项的容器中
        container.getChildren().add(messageContainer);
    }

    @Override
    protected void updateItem(ChatMessage item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            // 设置消息内容和样式
            senderText.setText(item.getSender());
            messageText.setText(item.getMessage());
            timeText.setText(item.getTime());

            // 设置控件之间的间距
            messageContainer.setSpacing(10); // 设置消息内容和发送时间之间的间距
            container.setSpacing(10); // 设置整体容器内各个控件之间的间距

            // 根据需要使用CSS样式设置控件的样式
             senderText.setStyle("-fx-font-size: 14px; -fx-text-fill: #000000;");
             messageText.setStyle("-fx-font-size: 15px; -fx-text-fill: #333333;");
             timeText.setStyle("-fx-font-size: 10px; -fx-text-fill: #666666;");
            // 将列表项的容器设置为当前列表项的外观
            setGraphic(container);
        } else {
            // 清空列表项的内容
//            setGraphic(null);
        }
    }
}

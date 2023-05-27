package models;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            messageText.setStyle("-fx-font-size: 15px; -fx-text-fill: red;");
            timeText.setStyle("-fx-font-size: 10px; -fx-text-fill: #666666;");

            // 清空消息容器的内容
            messageContainer.getChildren().clear();

            if (item.isImage()) {
                // 如果是图片消息
                Image image = new Image("file:" + item.getMessage());
                ImageView imageView = new ImageView(image);
                // 设置图片大小、属性等
                // 设置preserveRatio属性为true，保持图片的宽高比例
                imageView.setPreserveRatio(true);

                // 设置最大宽度和最大高度
                double maxWidth = 200;  // 设置最大宽度
                double maxHeight = 150; // 设置最大高度

                // 设置图片的最大尺寸
                imageView.setFitWidth(maxWidth);
                imageView.setFitHeight(maxHeight);

                // 将图片添加到消息容器中
                messageContainer.getChildren().addAll(senderText,imageView,timeText);
            }
            else {
                // 添加消息文本到消息容器中
                messageContainer.getChildren().addAll(senderText,messageText,timeText);
            }
            // 将列表项的容器设置为当前列表项的外观
            setGraphic(container);
        } else {
            // 清空列表项的内容
            setGraphic(null);
        }
    }
}

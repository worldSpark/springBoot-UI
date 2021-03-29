package com.fc.model.template;

/**
 * @Description 通用WebSocket消息接口，泛型
 * @author luoy
 * @Date 2020年1月31日15:52:12
 * @version 1.0.0
 * */
public interface IWebSocketMessage<T> {

    // 类的别名(redis识别用)
    final String classType = "WebSocket.IWebSocketMessage";

    /**
     * 获得类的别名
     * @return CLASS_TYPE
     */
    String getClassType();

    /**
     * 获得接收人用户id
     */
    String getAcceptUserId();

    /**
     * 设置接收人用户id
     * @param userId 用户id
     */
    void setAcceptUserId(String userId);

    /**
     * 消息类型，CommonWebSocketConstants.java
     * @return int
     */
    int getMessageType();

    /**
     * 设置消息类型
     */
    void setMessageType(int messageType);

    /**
     * 消息数据
     * @return obj
     */
    T getMessageData();

    /**
     * 消息数据
     * @return obj
     */
    void setMessageData(T object);
}

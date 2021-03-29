package com.fc.model.websocket;

import lombok.Data;

/**
 * @Description WebSocket连接信息
 * @author luoy
 * @version 1.0.0
 * @date 2020年5月3日14:49:53
 */
@Data
public class CommonWebSocketInfo {

    // 类的别名(redis识别用)
    final String classType = "WebSocket.CommonWebSocketInfo";
    // 每次连接的id
    private String wsId = "";
    // 用户id
    private String wsUserId = "";
    // 客户端
    private int clientType;
    // redis操作的动作(onOpen、onClose)
    private String redisType = "";
}

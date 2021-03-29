package com.fc.service.common;


import com.fc.model.template.IWebSocketMessage;
import com.fc.model.websocket.CommonWebSocketInfo;

import java.util.List;

/**
 * @Description 通用WebSocket接口
 * @author luoy
 * @Date 2020年2月1日15:30:30
 * @version 1.0.0
 **/
public interface CommonWebSocketService {

    /**
     * 获得在线的用户id（所有服务器）
     * @return list
     */
    List<String> getOnlineUserId();

    /**
     * 获得在线的用户id（当前服务器）
     * @return list
     */
    List<String> getOnlineUserIdOfCurrentServer();

    /**
     * 判断用户是否在线，任意客户端在线即可（所有服务器）
     * @param userId 用户id
     * @return true在线，false不在线
     */
    boolean isUserOnline(String userId);

    /**
     * 判断用户是否在线，任意客户端在线即可（当前服务器）
     * @param userId 用户id
     * @return true在线，false不在线
     */
    boolean isUserOnlineOfCurrentServer(String userId);

    /**
     * 判断用户指定的客户端是否在线（所有服务器）
     * @param userId 用户id
     * @param clientTypeList 客户端类型
     * @param allFit true：所有客户端在线才返回true，false：存在任意客户端在线就返回true，其它都是false
     * @return true在线，false不在线
     */
    boolean isUserOnline(String userId, List<Integer> clientTypeList, boolean allFit);

    /**
     * 判断多个用户是否在线（所有服务器）
     * @param userIdList 用户id集合
     * @return true全部用户在线，false有任意不在线用户
     */
    boolean isUserOnline(List<String> userIdList);

    /**
     * 判断多个用户指定的客户端是否在线（所有服务器）
     * @param userIdList 用户id集合
     * @param clientTypeList 客户端类型
     * @param allFit true：所有客户端在线才返回true，false：存在任意客户端在线就返回true，其它都是false
     * @return true全部用户在线，false有任意不在线用户
     */
    boolean isUserOnline(List<String> userIdList, List<Integer> clientTypeList, boolean allFit);

    /**
     * 给用户发送消息（所有服务器）
     * @param userId 用户id
     * @param message 消息
     * @return true发送成功，false发送失败
     */
    boolean sendToUser(String userId, IWebSocketMessage message);

    /**
     * 给用户发送消息（所有服务器）
     * @param userId 用户id
     * @param message 消息
     * @param clientTypeList 指定的客户端类型
     * @return true发送成功，false发送失败
     */
    boolean sendToUser(String userId, IWebSocketMessage message, List<Integer> clientTypeList);

    /**
     * 给多个用户发送消息（所有服务器）
     * @param userIdList 用户id集合
     * @param message 消息
     * @return true所有人都发送成功，false有任意一个用户发送失败
     */
    boolean sendToUser(List<String> userIdList, IWebSocketMessage message);

    /**
     * 给多个用户发送消息（所有服务器）
     * @param userIdList 用户id集合
     * @param message 消息
     * @param clientTypeList 指定的客户端类型
     * @return true所有人都发送成功，false有任意一个用户发送失败
     */
    boolean sendToUser(List<String> userIdList, IWebSocketMessage message, List<Integer> clientTypeList);

    /**
     * 给用户发送消息（当前服务器）
     * @param userId 用户id
     * @param message 消息
     * @return true发送成功，false发送失败
     */
    boolean sendToUserOfCurrentServer(String userId, IWebSocketMessage message);

    /**
     * 通知其它服务器用户上线
     * @param webSocketInfo 连接信息
     */
    void noticeUserOnlineToServer(CommonWebSocketInfo webSocketInfo);

    /**
     * 通知其它服务器用户下线
     * @param webSocketInfo 连接信息
     */
    void noticeUserOfflineToServer(CommonWebSocketInfo webSocketInfo);
}

package com.fc.service.common.Impl;

import com.fc.model.template.IWebSocketMessage;
import com.fc.model.websocket.CommonWebSocketInfo;
import com.fc.service.common.CommonWebSocketService;
import com.fc.util.CommonWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 通用WebSocket接口
 * @author luoy
 * @Date 2020年2月1日15:47:00
 * @version 1.0.0
 **/
@Service
@Transactional
public class CommonWebSocketServiceImpl implements CommonWebSocketService {

    // redis消息模板
    /*@Autowired
    private StringRedisTemplateService redisTemplateService;*/

    @Override
    public List<String> getOnlineUserId() {
        // 连接信息
        Map<String, List<CommonWebSocketInfo>> webSocketInfo = CommonWebSocketServer.getWebSocketInfo();
        List<String> userIdList = new ArrayList<>(webSocketInfo.keySet());
        return userIdList;
    }

    @Override
    public List<String> getOnlineUserIdOfCurrentServer() {
        // 连接池
        Map<String, List<CommonWebSocketServer>> webSocketPool = CommonWebSocketServer.getWebSocketPool();
        List<String> userIdList = new ArrayList<>(webSocketPool.keySet());
        return userIdList;
    }

    @Override
    public boolean isUserOnline(String userId) {
        List<String> onlineUserIdList = this.getOnlineUserId();
        return onlineUserIdList.contains(userId);
    }

    @Override
    public boolean isUserOnlineOfCurrentServer(String userId) {
        List<String> onlineUserIdList = this.getOnlineUserIdOfCurrentServer();
        return onlineUserIdList.contains(userId);
    }

    @Override
    public boolean isUserOnline(String userId, List<Integer> clientTypeList, boolean allFit) {
        // 空校验
        if(clientTypeList == null || clientTypeList.isEmpty()){
            return this.isUserOnline(userId);
        }
        List<CommonWebSocketInfo> infoList = this.getCommonWebSocketInfoByUserId(userId);
        boolean isOnline;
        if(allFit){
            // 全部符合
            isOnline = true;
            for (int i = 0; i < clientTypeList.size(); i++) {
                boolean fit = false;
                int clientType = clientTypeList.get(i);
                for (int j = 0; j < infoList.size(); j++) {
                    if(clientType == infoList.get(j).getClientType()){
                        fit = true;
                        break;
                    }
                }
                if(!fit){
                    // 不在线
                    isOnline = false;
                    break;
                }
            }
        }else{
            // 任意在线即可
            isOnline = !infoList.isEmpty();
        }
        return isOnline;
    }

    @Override
    public boolean isUserOnline(List<String> userIdList) {
        // 空校验
        if(userIdList == null || userIdList.isEmpty()){
            return false;
        }
        boolean isOnline = true;
        for (String userId : userIdList) {
            boolean innerOnline = this.isUserOnline(userId);
            if(!innerOnline){
                // 用户不在线，结束判断
                isOnline = false;
                break;
            }
        }
        return isOnline;
    }

    @Override
    public boolean isUserOnline(List<String> userIdList, List<Integer> clientTypeList, boolean allFit) {
        // 空校验
        if(userIdList == null || userIdList.isEmpty()){
            return false;
        }
        boolean isOnline = true;
        for (String userId : userIdList) {
            boolean innerOnline = this.isUserOnline(userId, clientTypeList, allFit);
            if(!innerOnline){
                // 用户不在线，结束判断
                isOnline = false;
                break;
            }
        }
        return isOnline;
    }

    @Override
    public boolean sendToUser(String userId, IWebSocketMessage message) {
        if(message == null){
            return false;
        }
        // 使用redis去发布消息
        message.setAcceptUserId(userId);
//        redisTemplateService.convertAndSend(message);
        return true;
    }

    @Override
    public boolean sendToUser(String userId, IWebSocketMessage message, List<Integer> clientTypeList) {
        if(message == null){
            return false;
        }
        List<CommonWebSocketInfo> infoList = this.getCommonWebSocketInfoByUserId(userId, clientTypeList);
        if(infoList.isEmpty() || message == null){
            return false;
        }else{
            for (CommonWebSocketInfo webSocketInfo : infoList) {
                // 使用redis去发布消息
                message.setAcceptUserId(userId);
//                redisTemplateService.convertAndSend(message);
            }
        }
        return true;
    }

    @Override
    public boolean sendToUser(List<String> userIdList, IWebSocketMessage message) {
        boolean result = true;
        if(message == null){
            return false;
        }
        for (String userId : userIdList) {
            result = result && this.sendToUser(userId, message);
        }
        return result;
    }

    @Override
    public boolean sendToUser(List<String> userIdList, IWebSocketMessage message, List<Integer> clientTypeList) {
        boolean result = true;
        if(message == null){
            return false;
        }
        for (String userId : userIdList) {
            result = result && this.sendToUser(userId, message, clientTypeList);
        }
        return result;
    }

    @Override
    public boolean sendToUserOfCurrentServer(String userId, IWebSocketMessage message) {
        List<CommonWebSocketServer> serverList = this.getCommonWebSocketServerByUserId(userId);
        if(serverList.isEmpty() || message == null){
            return false;
        }else{
            for (CommonWebSocketServer webSocketServer : serverList) {
                webSocketServer.sendMessage(message);
            }
        }
        return true;
    }

    @Override
    public void noticeUserOnlineToServer(CommonWebSocketInfo webSocketInfo) {
        if(webSocketInfo == null){
            return;
        }
        webSocketInfo.setRedisType("onOpen");
//        redisTemplateService.convertAndSend(webSocketInfo);
    }

    @Override
    public void noticeUserOfflineToServer(CommonWebSocketInfo webSocketInfo) {
        if(webSocketInfo == null){
            return;
        }
        webSocketInfo.setRedisType("onClose");
//        redisTemplateService.convertAndSend(webSocketInfo);
    }

    // --------------------------------- 私有方法 ---------------------------------

    /**
     * 获得用户的WebSocket连接信息（当前服务器）
     * @param userId 用户id
     * @return list
     */
    private List<CommonWebSocketServer> getCommonWebSocketServerByUserId(String userId){
        List<CommonWebSocketServer> serverList = new ArrayList<>();
        // 连接池
        Map<String, List<CommonWebSocketServer>> webSocketPool = CommonWebSocketServer.getWebSocketPool();
        if(webSocketPool == null || webSocketPool.isEmpty()){
            return serverList;
        }
        if(webSocketPool.containsKey(userId)){
            serverList = webSocketPool.get(userId);
        }
        return serverList;
    }

    /**
     * 获得用户的WebSocket连接信息（当前服务器）
     * @param userId 用户id
     * @param clientTypeList 指定客户端
     * @return list
     */
    private List<CommonWebSocketServer> getCommonWebSocketServerByUserId(String userId, List<Integer> clientTypeList){
        List<CommonWebSocketServer> serverList = new ArrayList<>();
        // 连接池
        Map<String, List<CommonWebSocketServer>> webSocketPool = CommonWebSocketServer.getWebSocketPool();
        if(webSocketPool == null || webSocketPool.isEmpty()){
            return serverList;
        }
        if(webSocketPool.containsKey(userId)){
            serverList = webSocketPool.get(userId);
            // 过滤掉不需要的客户端
            for (int i = 0; i < serverList.size(); i++) {
                int clientType = serverList.get(i).getClientType();
                if(!clientTypeList.contains(clientType)){
                    // 不符合客户端要求，移除
                    clientTypeList.remove(i);
                    i--;
                }
            }
        }
        return serverList;
    }

    /**
     * 获得用户的WebSocket连接信息（所有服务器）
     * @param userId 用户id
     * @return list
     */
    private List<CommonWebSocketInfo> getCommonWebSocketInfoByUserId(String userId){
        List<CommonWebSocketInfo> infoList = new ArrayList<>();
        // 连接池
        Map<String, List<CommonWebSocketInfo>> webSocketPool = CommonWebSocketServer.getWebSocketInfo();
        if(webSocketPool == null || webSocketPool.isEmpty()){
            return infoList;
        }
        if(webSocketPool.containsKey(userId)){
            infoList = webSocketPool.get(userId);
        }
        return infoList;
    }

    /**
     * 获得用户的WebSocket连接信息（所有服务器）
     * @param userId 用户id
     * @param clientTypeList 指定客户端
     * @return list
     */
    private List<CommonWebSocketInfo> getCommonWebSocketInfoByUserId(String userId, List<Integer> clientTypeList){
        List<CommonWebSocketInfo> infoList = new ArrayList<>();
        // 连接池
        Map<String, List<CommonWebSocketInfo>> webSocketInfo = CommonWebSocketServer.getWebSocketInfo();
        if(webSocketInfo == null || webSocketInfo.isEmpty()){
            return infoList;
        }
        if(webSocketInfo.containsKey(userId)){
            infoList = webSocketInfo.get(userId);
            // 过滤掉不需要的客户端
            for (int i = 0; i < infoList.size(); i++) {
                int clientType = infoList.get(i).getClientType();
                if(!clientTypeList.contains(clientType)){
                    // 不符合客户端要求，移除
                    clientTypeList.remove(i);
                    i--;
                }
            }
        }
        return infoList;
    }
}

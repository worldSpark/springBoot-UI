package com.fc.util;

import com.fc.model.template.IWebSocketMessage;
import com.fc.model.websocket.CommonWebSocketInfo;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description webSocket服务层
 * @author luoy
 * @Date 2020年1月29日15:55:02
 * @version 1.0.0
 **/
@Component
//访问服务端的url地址
@ServerEndpoint(value = "/commonWebSocketServer/{param}")
public class CommonWebSocketServer {

    // ------------------------------------------ 私有变量开始 ------------------------------------------
    // 每次连接的id
    private String wsId = "";
    // 用户id
    private String wsUserId = "";
    // 客户端
    private int clientType;
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    // ------------------------------------------ 私有变量结束 ------------------------------------------
    // ------------------------------------------ 全局变量开始 ------------------------------------------
    // webSocket连接池，key是用户id
    private static final Map<String, List<CommonWebSocketServer>> WEB_SOCKET_POOL = new ConcurrentHashMap<>();
    // webSocket连接信息，key是用户id
    private static final Map<String, List<CommonWebSocketInfo>> WEB_SOCKET_INFO = new ConcurrentHashMap<>();
    // 日志
    private static Logger logger = LoggerFactory.getLogger(CommonWebSocketServer.class);
    // ------------------------------------------ 全局变量结束 ------------------------------------------

    /**
     * 获得WebSocket连接池（当前服务器）
     * 1、不允许修改，只做查询用
     * @return map
     */
    public static Map<String, List<CommonWebSocketServer>> getWebSocketPool(){
        Map<String, List<CommonWebSocketServer>> map = new ConcurrentHashMap<>();
        map.putAll(CommonWebSocketServer.WEB_SOCKET_POOL);
        return map;
    }

    /**
     * 获得WebSocket连接信息（所有服务器）
     * 1、不允许修改，只做查询用
     * @return map
     */
    public static Map<String, List<CommonWebSocketInfo>> getWebSocketInfo(){
        Map<String, List<CommonWebSocketInfo>> map = new ConcurrentHashMap<>();
        map.putAll(CommonWebSocketServer.WEB_SOCKET_INFO);
        return map;
    }

    /**
     * 用户上线（其它服务器传递过来的）
     * @param webSocketInfo 连接信息
     */
    public static void addWebSocketUser(CommonWebSocketInfo webSocketInfo){
        // 连接id
        String wsId = webSocketInfo.getWsId();
        // 连接的用户id
        String cWsUserId = webSocketInfo.getWsUserId();
        // 放入连接信息
        List<CommonWebSocketInfo> infoList;
        if(CommonWebSocketServer.WEB_SOCKET_INFO.containsKey(cWsUserId)){
            infoList = CommonWebSocketServer.WEB_SOCKET_INFO.get(cWsUserId);
            // 判断重复
            boolean isExist = false;
            for (CommonWebSocketInfo commonWebSocketInfo : infoList) {
                if(wsId.equals(commonWebSocketInfo.getWsId())){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                infoList.add(webSocketInfo);
            }
        }else{
            infoList = new ArrayList<>();
            infoList.add(webSocketInfo);
            CommonWebSocketServer.WEB_SOCKET_INFO.put(cWsUserId, infoList);
        }
    }

    /**
     * 用户下线（其它服务器传递过来的）
     * @param webSocketInfo 连接信息
     * @return boolean
     */
    public static int removeWebSocketUser(CommonWebSocketInfo webSocketInfo){
        // 连接id
        String cWsId = webSocketInfo.getWsId();
        // 连接的用户id
        String cWsUserId = webSocketInfo.getWsUserId();
        // 移除数量
        int count = 0;
        // 从连接池中移除
        if(CommonWebSocketServer.WEB_SOCKET_POOL.containsKey(cWsUserId)){
            List<CommonWebSocketServer> list = CommonWebSocketServer.WEB_SOCKET_POOL.get(cWsUserId);
            for (int i = 0; i < list.size(); i++) {
                if(cWsId.equals(list.get(i).wsId)){
                    // 移除
                    list.remove(i);
                    count++;
                    i--;
                }
            }
            if(list.isEmpty()){
                // 数组为空，从连接池中移除
                CommonWebSocketServer.WEB_SOCKET_POOL.remove(cWsUserId);
            }
        }
        // 从连接信息中移除
        if(CommonWebSocketServer.WEB_SOCKET_INFO.containsKey(cWsUserId)){
            List<CommonWebSocketInfo> list = CommonWebSocketServer.WEB_SOCKET_INFO.get(cWsUserId);
            for (int i = 0; i < list.size(); i++) {
                if(cWsId.equals(list.get(i).getWsId())){
                    // 移除
                    list.remove(i);
                    count++;
                    i--;
                }
            }
            if(list.isEmpty()){
                // 数组为空，从连接信息中中移除
                CommonWebSocketServer.WEB_SOCKET_INFO.remove(cWsUserId);
            }
        }
        return count;
    }

    /**
     * 连接建立成功调用的方法
     * @param param 连接的参数 json格式
     * @param session session
     */
    @OnOpen
    public void onOpen(@PathParam(value = "param") String param, Session session) throws Exception {
        // 连接的id
        String wsId = "";
        // 用户id
        String wsUserId = "";
        // 客户端类型
        Integer clientType = null;
        // 解析参数
        if(StringUtils.isNotBlank(param)){
            try {
                param = URLDecoder.decode(param, "UTF-8");
                JSONObject paramObject = JSONObject.fromObject(param);
                // 连接的id
                if(paramObject.containsKey("wsId")){
                    wsId = paramObject.getString("wsId");
                }
                // 用户id
                if(paramObject.containsKey("wsUserId")){
                    wsUserId = paramObject.getString("wsUserId");
                }
                // 客户端类型
                if(paramObject.containsKey("clientType")){
                    clientType = GlobalFunc.parseInt(paramObject.getString("clientType"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        this.session = session;
        if(StringUtils.isBlank(wsId) || StringUtils.isBlank(wsUserId) || clientType == null){
            // 空，抛出异常
            this.sendConsoleMessage("参数不全！");
            throw new Exception("参数不全！");
        }

        // 赋值
        this.wsId = wsId;
        this.wsUserId = wsUserId;
        this.clientType = clientType;
        // 放入连接池
        List<CommonWebSocketServer> list;
        if(CommonWebSocketServer.WEB_SOCKET_POOL.containsKey(wsUserId)){
            list = CommonWebSocketServer.WEB_SOCKET_POOL.get(wsUserId);
            list.add(this);
        }else{
            list = new ArrayList<>();
            list.add(this);
            CommonWebSocketServer.WEB_SOCKET_POOL.put(wsUserId, list);
        }
        // 放入连接信息
        CommonWebSocketInfo webSocketInfo = new CommonWebSocketInfo();
        webSocketInfo.setWsId(this.wsId);
        webSocketInfo.setWsUserId(this.wsUserId);
        webSocketInfo.setClientType(this.clientType);
        List<CommonWebSocketInfo> infoList;
        if(CommonWebSocketServer.WEB_SOCKET_INFO.containsKey(wsUserId)){
            infoList = CommonWebSocketServer.WEB_SOCKET_INFO.get(wsUserId);
            infoList.add(webSocketInfo);
        }else{
            infoList = new ArrayList<>();
            infoList.add(webSocketInfo);
            CommonWebSocketServer.WEB_SOCKET_POOL.put(wsUserId, list);
        }
        // 通知其他服务器用户上线
        /*Constants.getCommonWebSocketService().noticeUserOnlineToServer(webSocketInfo);
        // 通知刷新在线状态
        Constants.getChatUserService().refreshOnlineState();
        // 桌面推演刷新状态
        Constants.getUserStatusForDesktopDeduction().sendIsOnlineStatus();*/
        logger.info("用户【" + wsId + "】加入！当前在线人数为" + CommonWebSocketServer.WEB_SOCKET_POOL.keySet().size());
        this.sendConsoleMessage("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 从连接池中移除
        if(CommonWebSocketServer.WEB_SOCKET_POOL.containsKey(this.wsUserId)){
            List<CommonWebSocketServer> list = CommonWebSocketServer.WEB_SOCKET_POOL.get(this.wsUserId);
            for (int i = 0; i < list.size(); i++) {
                if(this.wsId.equals(list.get(i).wsId)){
                    // 移除
                    list.remove(i);
                    i--;
                }
            }
            if(list.isEmpty()){
                // 数组为空，从连接池中移除
                CommonWebSocketServer.WEB_SOCKET_POOL.remove(this.wsUserId);
            }
        }
        // 从连接信息中移除
        // 连接信息
        CommonWebSocketInfo webSocketInfo = new CommonWebSocketInfo();
        webSocketInfo.setWsId(this.wsId);
        webSocketInfo.setWsUserId(this.wsUserId);
        webSocketInfo.setClientType(this.clientType);
        if(CommonWebSocketServer.WEB_SOCKET_INFO.containsKey(this.wsUserId)){
            List<CommonWebSocketInfo> list = CommonWebSocketServer.WEB_SOCKET_INFO.get(this.wsUserId);
            for (int i = 0; i < list.size(); i++) {
                if(this.wsId.equals(list.get(i).getWsId())){
                    // 移除
                    list.remove(i);
                    i--;
                }
            }
            if(list.isEmpty()){
                // 数组为空，从连接信息中中移除
                CommonWebSocketServer.WEB_SOCKET_INFO.remove(this.wsUserId);
            }
        }
        // 通知其他服务器用户下线
      /*  Constants.getCommonWebSocketService().noticeUserOfflineToServer(webSocketInfo);
        // 通知刷新在线状态
        Constants.getChatUserService().refreshOnlineState();
        // 桌面推演刷新状态
        Constants.getUserStatusForDesktopDeduction().sendIsOnlineStatus();*/

        logger.info("有一连接关闭！当前在线人数为" + CommonWebSocketServer.WEB_SOCKET_POOL.keySet().size());
    }


    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) throws Exception{

    }

    /**
     * 错误时调用
     * @param error error
     */
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    /**
     * 发送消息（总接口）
     * @param message string或json格式
     */
    public void sendMessage(IWebSocketMessage message) {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息（私有、调试信息）
     * @param message string格式
     */
    private void sendConsoleMessage(String message) {
     /*   WSConsoleMessage consoleMessage = new WSConsoleMessage();
        consoleMessage.setMessageData(message);
        this.sendMessage(consoleMessage);*/
    }

    // ------------------------------------------ get ------------------------------------------

    public String getWsId() {
        return this.wsId;
    }

    public String getWsUserId() {
        return this.wsUserId;
    }

    public int getClientType() {
        return this.clientType;
    }

    public Session getSession() {
        return this.session;
    }
}

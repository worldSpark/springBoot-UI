package com.fc.model.websocket;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * @author fug
 * @version 1.0.0
 * @date 2019/1/10
 * @Description  websocket实体类
 */

@Data
@ModelName(name="在线聊天实体类")
@Table(name="wp_websocket")
public class WebSocket {

    @FieldName(name="主键id")
    @Column(name="id",type= MySqlTypeConstant.VARCHAR,length = 255, isKey = true)
    private String id;//主键id

    @FieldName(name="发送人id")
    @Column(name="sender_user_id",type=MySqlTypeConstant.VARCHAR ,length =255 )
    private String senderUserId;//发送人id

    @FieldName(name="发送时间")
    @Column(name="sender_time",type=MySqlTypeConstant.VARCHAR ,length =255 )
    private String senderTime;//发送时间(yyyy-MM-dd HH:mm:ss)

    @FieldName(name="接收人id")
    @Column(name="accept_user_id",type=MySqlTypeConstant.VARCHAR ,length =255 )
    private String acceptUserId;//接收人id;接收小组id

    @FieldName(name="类型")
    @Column(name="type",type=MySqlTypeConstant.VARCHAR ,length =255 )
    private String type;//类型：person/group

    @FieldName(name="传输的数据")
    @Column(name="data",type=MySqlTypeConstant.VARCHAR ,length =8000 )
    private String data;//传输的数据  1、聊天室：Map{key:message(消息)} 目前只实现全体聊天
    //2、确认框：Map{key:title（标题）、message（消息）、confirm_type（确认按钮的方式：url、func）、path（url/func）}
    //3、窗口　：Map{key:title（标题）、url}

    @FieldName(name="扩展字段")
    @Column(name="ext",type=MySqlTypeConstant.VARCHAR ,length =255 )
    private String ext;//扩展字段
}

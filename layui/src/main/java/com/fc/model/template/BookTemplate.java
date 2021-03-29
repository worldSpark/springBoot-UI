package com.fc.model.template;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * @Description 书本模板
 * @author luoy
 * @date 2020年3月11日13:39:48
 * @version 1.0.0
 */
@Data
@ModelName(name = "书本模板")
@Table(name="wp_book_template")
public class BookTemplate {

    @FieldName(name = "主键id")
    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isKey = true, length = 50)
    private String id;

    @FieldName(name = "普通输入框")
    @Column(name = "normal_input", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String normalInput;

    @FieldName(name = "整数输入框")
    @Column(name = "int_input", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String intInput;

    @FieldName(name = "整数输入框开始")
    @Column(name = "int_input_start", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String intInputStart;

    @FieldName(name = "整数输入框结束")
    @Column(name = "int_input_end", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String intInputEnd;

    // yyyy-MM-dd
    @FieldName(name = "年月日")
    @Column(name = "date_input_ymd", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dateInputYMD;
}

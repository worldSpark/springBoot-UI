package codeGenerator;

import com.fc.common.domain.CommonFieldInfo;
import lombok.Data;

import java.util.List;

/**
 * @Description 查询条件
 * @author luoy
 * @version 1.0.0
 * @Date 2020年3月9日09:09:09
 **/
@Data
public class CGSearchFiled {

    // 对应字段
    private CommonFieldInfo fieldInfo;

    // 查询类别（修改时注意CGConstants.java）：
    // 1、普通字符查询
    // 2、数字范围查询
    // 3、数字智能查询（支持输入：>10,>10且<100，等于10）
    // 4、整数范围查询
    // 5、日期范围查询
    // 6、固定下拉框单选查询
    // 7、固定下拉框多选查询
    private int searchType;

    // 范围开始（允许为空，查询类别2、4适用）
    private String areaStart;

    // 范围结束（允许为空，查询类别2、4适用）
    private String areaEnd;

    // 下拉框参数（允许为空，查询类别6、7适用）
    private List<String> selectList;
}

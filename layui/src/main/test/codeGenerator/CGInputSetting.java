package codeGenerator;

import com.fc.common.domain.CommonClassInfo;
import com.fc.common.domain.CommonFileUploadConfig;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用户输入参数
 * @author luoy
 * @version 1.0.0
 * @Date 2020年3月9日09:09:09
 **/
@Data
public class CGInputSetting {

    // 实体类地址(com.ncxdkj.model.aaa.bbb.ccc)
    private String modelPack;

    // 是否覆盖代码（默认否）
    private boolean coveredCode;

    // 实体类中文名
    private String chineseModelName;

    // 作者（读取实体类的作者，如果没有，默认为空）
    private String author;

    // 版本（读取实体类的版本，如果没有，默认为1.0.0）
    private String version;

    // 实体信息
    private CommonClassInfo classInfo;

    // 查询字段
    private List<CGSearchFiled> searchFieldList;

    // 新增、修改方式
    private int addOrEditType;

    // 是否添加附件上传功能（默认否）
    private boolean fileUpload;

    // 附件上传参数
    private CommonFileUploadConfig fileUploadConfig;

    // 假删除字段
    private String fakeDeleteFieldName;

    // 人员选择框字段
    private List<String> userSelectFieldNameList;

    // 部门选择框字段
    private List<String> deptSelectFieldNameList;

    public CGInputSetting() {
        // 设置默认值
        this.coveredCode = false;
        this.searchFieldList = new ArrayList<>();
        this.addOrEditType = CGConstants.ADD_OR_EDIT_TYPE_WINDOW;
        this.fileUpload = false;
        this.fileUploadConfig = new CommonFileUploadConfig();
        this.fakeDeleteFieldName = "";
        this.userSelectFieldNameList = new ArrayList<>();
        this.deptSelectFieldNameList = new ArrayList<>();
    }

    /**
     * 获取新增、修改方式名称
     * @return name
     */
    public String getAddOrEditTypeName(){
        String name = "无";
        if(CGConstants.ADD_OR_EDIT_TYPE_WINDOW == this.addOrEditType){
            name = "窗口";
        }else if(CGConstants.ADD_OR_EDIT_TYPE_URL == this.addOrEditType){
            name = "跳转";
        }
        return name;
    }

    /**
     * 实体类英文名
     * @return modelName
     */
    public String getModelName() {
        String modelName = new String(this.modelPack);
        modelName = modelName.substring(modelName.lastIndexOf(".") + 1);
        return modelName;
    }

    /**
     * 实体类英文变量名（首字母小写）
     * @return modelName
     */
    public String getModelVariableName() {
        String variableName = new String(this.getModelName());
        String regexA = "^[A-Z]{1,}$";
        String start = variableName.substring(0,1);
        if(start.matches(regexA)){
            variableName = start.toLowerCase() + variableName.substring(1);
        }
        return variableName;
    }

    /**
     * 实体类文件路径(com/ncxdkj/model/aaa/bbb/ccc)
     * @return path
     */
    public String getModelPath() {
        String path = new String(this.modelPack);
        path = path.replaceAll("\\.", "\\\\");
        return path;
    }

    /**
     * 实体类包地址(com.ncxdkj.model.aaa.bbb)
     * @return path
     */
    public String getPackagePack() {
        String path = new String(this.modelPack);
        path = path.substring(0, path.lastIndexOf("."));
        return path;
    }

    /**
     * 实体类包相对地址前缀(com.ncxdkj)
     * @return path
     */
    public String getPackageRelativePackPrefix() {
        String path = new String(this.modelPack);
        path = path.substring(0, path.indexOf(".model."));
        return path;
    }

    /**
     * 实体类包文件相对路径前缀(com/ncxdkj)
     * @param type true正斜杠 false反斜杠
     * @return path
     */
    public String getPackageRelativePathPrefix(boolean type) {
        String path = new String(this.modelPack);
        path = path.substring(0, path.indexOf(".model."));
        if(type){
            path = path.replaceAll("\\.", "/");
        }else{
            path = path.replaceAll("\\.", "\\\\");
        }
        return path;
    }

    /**
     * 实体类包相对地址后缀(aaa.bbb)
     * @return path
     */
    public String getPackageRelativePackSuffix() {
        String path = new String(this.modelPack);
        path = path.substring(path.indexOf(".model.") + 7, path.lastIndexOf("."));
        return path;
    }

    /**
     * 实体类包文件相对路径后缀(aaa/bbb)
     * @param type true正斜杠 false反斜杠
     * @return path
     */
    public String getPackageRelativePathSuffix(boolean type) {
        String path = new String(this.modelPack);
        path = path.substring(path.indexOf(".model.") + 7, path.lastIndexOf("."));
        if(type){
            path = path.replaceAll("\\.", "/");
        }else{
            path = path.replaceAll("\\.", "\\\\");
        }
        return path;
    }
}

package codeGenerator;

import com.fc.common.domain.CommonClassInfo;
import com.fc.common.domain.CommonFieldInfo;
import com.fc.common.domain.CommonFileUploadConfig;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 代码自动生成器
 * @author luoy
 * @version 1.0.0
 * @Date 2020年3月9日09:09:09
 **/
public class CGEngine {

    /**
     * 生成文件
     * @param inputSetting 设置
     * @param packagePath java包路径
     */
    public static void generator(CGInputSetting inputSetting, String packagePath){
        if(inputSetting == null || inputSetting.getClassInfo() == null){
            // 空校验
            return;
        }

        // 生成Service
        CGEngine.generatorService(inputSetting, packagePath);
        // 生成Controller
        CGEngine.generatorController(inputSetting, packagePath);
        // 生成Jsp
        CGEngine.generatorJsp(inputSetting, packagePath);
    }

    /**
     * 生成Service
     * @param inputSetting 设置
     * @param packagePath java包路径
     */
    private static void generatorService(CGInputSetting inputSetting, String packagePath){
        // 类信息
        CommonClassInfo classInfo = inputSetting.getClassInfo();
        // 实体类名
        String modelName = inputSetting.getModelName();
        // 实体变量名
        String modelVariableName = inputSetting.getModelVariableName();
        // 实体类中文名
        String chineseModelName = inputSetting.getChineseModelName();
        // 表名
        String tableName = classInfo.getTableName();
        // 字段信息集合
        List<CommonFieldInfo> fieldInfoList = classInfo.getCommonFieldInfoList();
        // 接口文件夹绝对路径
        String interfacePackageAbsolutePath = packagePath + File.separator + inputSetting.getPackageRelativePathPrefix(false)
                + File.separator + "service" + File.separator + inputSetting.getPackageRelativePathSuffix(false);
        // 实现层文件夹绝对路径
        String implPackageAbsolutePath = interfacePackageAbsolutePath + File.separator + "impl";
        // 接口绝对路径
        String interfaceAbsolutePath = interfacePackageAbsolutePath + File.separator + modelName + "Service" + ".java";
        // 实现层绝对路径
        String implAbsolutePath = implPackageAbsolutePath + File.separator + modelName + "ServiceImpl" + ".java";
        // 文件日期
        String fileDate = CGEngine.getFileDate();

        // 创建文件夹
        File interFacePackageFile = new File(interfacePackageAbsolutePath);
        if(!interFacePackageFile.exists()){
            interFacePackageFile.mkdirs();
        }
        File implPackageFile = new File(implPackageAbsolutePath);
        if(!implPackageFile.exists()){
            implPackageFile.mkdirs();
        }

        // 生成接口文件
        File interfaceFile = new File(interfaceAbsolutePath);
        // 是否创建文件
        boolean createInterfaceFile = true;
        if(interfaceFile.exists()){
            // 文件已存在
            if(inputSetting.isCoveredCode()){
                // 覆盖代码，先删除
                interfaceFile.delete();
                CGMain.print("【" + modelName + "Service.java】文件已存在，开始覆盖！\n");
            }else{
                // 不覆盖代码
                createInterfaceFile = false;
                CGMain.print("【" + modelName + "Service.java】文件已存在，跳过！\n");
            }
        }
        if(createInterfaceFile){
            System.out.print("开始生成【" + modelName + "Service.java】。。。");
            try {
                interfaceFile = new File(interfaceAbsolutePath);
                interfaceFile.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(interfaceFile));
                out.write("package " + inputSetting.getPackageRelativePackPrefix() + ".service." + inputSetting.getPackageRelativePackSuffix() + ";\r\n");
                out.write("\r\n");
                out.write("import " + inputSetting.getModelPack() + ";\r\n");
                out.write("\r\n");
                out.write("import javax.servlet.http.HttpServletRequest;\r\n");
                out.write("import java.util.Map;\r\n");
                out.write("\r\n");
                out.write("/**\r\n");
                out.write(" * @Description " + chineseModelName + "接口\r\n");
                out.write(" * @author " + inputSetting.getAuthor() + "\r\n");
                out.write(" * @date " + fileDate + "\r\n");
                out.write(" * @version " + inputSetting.getVersion() + "\r\n");
                out.write(" */\r\n");
                out.write("public interface " + modelName + "Service {\r\n");
                out.write("\r\n");

                out.write("\t/**\r\n");
                out.write("\t * 新增" + chineseModelName + "\r\n");
                out.write("\t * @param " + modelVariableName + " " + chineseModelName + "\r\n");
                out.write("\t * @return count\r\n");
                out.write("\t */\r\n");
                out.write("\tint insert(" + modelName + " " + modelVariableName + ");\r\n");
                out.write("\r\n");


                out.write("\t/**\r\n");
                out.write("\t * 更新一条记录\r\n");
                out.write("\t * @param " + modelVariableName + " " + chineseModelName + "\r\n");
                out.write("\t * @return count\r\n");
                out.write("\t */\r\n");
                out.write("\tint update(" + modelName + " " + modelVariableName + ");\r\n");
                out.write("\r\n");


                out.write("\t/**\r\n");
                out.write("\t * 查询一条记录\r\n");
                out.write("\t * @param id " + chineseModelName + "id\r\n");
                out.write("\t * @return " + modelName + "\r\n");
                out.write("\t */\r\n");
                out.write("\t" + modelName + " getById(String id);\r\n");
                out.write("\r\n");

                out.write("\t/**\r\n");
                out.write("\t * 删除一条记录\r\n");
                out.write("\t * @param id " + chineseModelName + "id\r\n");
                out.write("\t * @return count\r\n");
                out.write("\t */\r\n");
                out.write("\tint delete(String id);\r\n");
                out.write("\r\n");


                out.write("\t/**\r\n");
                out.write("\t * 分页获取" + chineseModelName + "信息\r\n");
                out.write("\t * @param request request\r\n");
                out.write("\t * @return data\r\n");
                out.write("\t */\r\n");
                out.write("\tMap<String, Object> get" + modelName + "List(HttpServletRequest request);\r\n");
                out.write("\r\n");


                out.write("\t/**\r\n");
                out.write("\t * 保存数据\r\n");
                out.write("\t * @param request request\r\n");
                out.write("\t * @return data\r\n");
                out.write("\t */\r\n");
                out.write("\tMap<String, Object> save(HttpServletRequest request);\r\n");
                out.write("\r\n");

                out.write("}\r\n");
                out.flush();
                out.close();
                System.out.print("生成成功！\n");
            } catch (IOException e) {
                System.out.print("生成失败！\n");
                e.printStackTrace();
            }
        }

        // 生成实现层文件
        File implFile = new File(implAbsolutePath);
        // 是否创建文件
        boolean createImplFile = true;
        if(implFile.exists()){
            // 文件已存在
            if(inputSetting.isCoveredCode()){
                // 覆盖代码，先删除
                implFile.delete();
                CGMain.print("【" + modelName + "ServiceImpl.java】文件已存在，开始覆盖！\n");
            }else{
                // 不覆盖代码
                createImplFile = false;
                CGMain.print("【" + modelName + "ServiceImpl.java】文件已存在，跳过！\n");
            }
        }
        if(createImplFile){
            System.out.print("开始生成【" + modelName + "ServiceImpl.java】。。。");
            try {
                implFile = new File(implAbsolutePath);
                implFile.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(implFile));
                out.write("package " + inputSetting.getPackageRelativePackPrefix() + ".service." + inputSetting.getPackageRelativePackSuffix() + ".impl;\r\n");
                out.write("\r\n");

                out.write("import com.github.pagehelper.PageHelper;\r\n");
                out.write("import com.github.pagehelper.PageInfo;\r\n");
                out.write("import com.ncxdkj.mapper.SqlMapper;\r\n");
                out.write("import " + inputSetting.getModelPack() + ";\r\n");
                out.write("import com.ncxdkj.service.common.CommonFileUploadService;\r\n");
                out.write("import com.ncxdkj.service." + inputSetting.getPackageRelativePackSuffix() + "." + modelName + "Service;\r\n");
                out.write("import com.ncxdkj.service.common.CommonService;\r\n");
                out.write("import com.ncxdkj.util.*;\r\n");
                out.write("import org.apache.commons.lang.StringUtils;\r\n");
                out.write("import org.springframework.beans.factory.annotation.Autowired;\r\n");
                out.write("import org.springframework.stereotype.Service;\r\n");
                out.write("import org.springframework.transaction.annotation.Transactional;\r\n");

                out.write("import javax.servlet.http.HttpServletRequest;\r\n");
                out.write("import java.util.HashMap;\r\n");
                out.write("import java.util.List;\r\n");
                out.write("import java.util.Map;\r\n");

                out.write("/**\r\n");
                out.write(" * @Description " + chineseModelName + "实现层\r\n");
                out.write(" * @author " + inputSetting.getAuthor() + "\r\n");
                out.write(" * @date " + fileDate + "\r\n");
                out.write(" * @version " + inputSetting.getVersion() + "\r\n");
                out.write(" */\r\n");
                out.write("@Service\r\n");
                out.write("@Transactional\r\n");
                out.write("public class " + modelName + "ServiceImpl implements " + modelName + "Service{\r\n");
                out.write("\r\n");

                out.write("\t@Autowired\r\n");
                out.write("\tprivate CommonService commonService;\r\n");
                out.write("\r\n");

                out.write("\t@Autowired\r\n");
                out.write("\tprivate SqlMapper sqlMapper;\r\n");
                out.write("\r\n");

                out.write("\t@Autowired\r\n");
                out.write("\tprivate CommonFileUploadService commonFileUploadService;\r\n");
                out.write("\r\n");

                out.write("\t@Override\r\n");
                out.write("\tpublic int insert(" + modelName + " " + modelVariableName + ") {\r\n");
                out.write("\t\treturn commonService.insert(" + modelVariableName + ");\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("\t@Override\r\n");
                out.write("\tpublic int update(" + modelName + " " + modelVariableName + ") {\r\n");
                out.write("\t\treturn commonService.update(" + modelVariableName + ");\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("\t@Override\r\n");
                out.write("\tpublic " + modelName + " getById(String id) {\r\n");
                out.write("\t\treturn commonService.getById(" + modelName + ".class, id);\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("\t@Override\r\n");
                out.write("\tpublic int delete(String id) {\r\n");
                out.write("\t\treturn commonService.delete(" + modelName + ".class, id);\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("\t@Override\r\n");
                out.write("\tpublic Map<String, Object> get" + modelName + "List(HttpServletRequest request) {\r\n");
                out.write("\t\t// 第n页\r\n");
                out.write("\t\tint page = GlobalFunc.parseInt(request.getParameter(\"page\"));\r\n");
                out.write("\t\t// 每页显示n条\r\n");
                out.write("\t\tint rows = GlobalFunc.parseInt(request.getParameter(\"rows\"));\r\n");
                out.write("\r\n");

                // 查询条件
                List<CGSearchFiled> searchFieldList = inputSetting.getSearchFieldList();
                if(searchFieldList.isEmpty()){
                    out.write("\t\t// 查询条件：无\r\n");
                }else{
                    out.write("\t\t// 查询条件：\r\n");
                }
                for (int i = 0; i < searchFieldList.size(); i++) {
                    CGSearchFiled searchFiled = searchFieldList.get(i);
                    // 字段信息
                    CommonFieldInfo fieldInfo = searchFiled.getFieldInfo();
                    // 属性名
                    String fieldName = fieldInfo.getFieldName();
                    // 字段注释名
                    String annotateName = fieldInfo.getAnnotateName();
                    // 首字母大小
                    String firstChar = fieldName.substring(0, 1);
                    fieldName = firstChar.toUpperCase() + fieldName.substring(1);
                    // 查询类别
                    int searchType = searchFiled.getSearchType();
                    if(searchType == CGConstants.SEARCH_TYPE_NORMAL
                            || searchType == CGConstants.SEARCH_TYPE_NUMBER_SMART
                            || searchType == CGConstants.SEARCH_TYPE_SELECT_SINGLE
                            || searchType == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
                        // 普通字符查询、数字智能查询、固定下拉框单选查询、固定下拉框多选查询
                        String variableName = "search" + fieldName;
                        out.write("\t\t// " + annotateName + "\r\n");
                        out.write("\t\tString " + variableName + " = GlobalFunc.toString(request.getParameter(\"" + variableName + "\"));\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_NUMBER_AREA
                            || searchType == CGConstants.SEARCH_TYPE_INT_AREA
                            || searchType == CGConstants.SEARCH_TYPE_DATE_AREA){
                        // 数字范围查询、整数范围查询、日期范围查询
                        String variableNameStart = "search" + fieldName + "Start";
                        String variableNameEnd = "search" + fieldName + "End";
                        out.write("\t\t// " + annotateName + "\r\n");
                        out.write("\t\tString " + variableNameStart + " = GlobalFunc.toString(request.getParameter(\"" + variableNameStart + "\"));\r\n");
                        out.write("\t\tString " + variableNameEnd + " = GlobalFunc.toString(request.getParameter(\"" + variableNameEnd + "\"));\r\n");
                    }
                }
                out.write("\r\n");

                // 表别名（如wp_user → wu）
                String tableSimpleName = tableName.substring(0, 1);
                int index = tableName.indexOf("_");
                while (index > 0 && index < (tableName.length() - 2)){
                    tableSimpleName += tableName.substring(index + 1, index + 2);
                    index = tableName.indexOf("_", index + 1);
                }
                out.write("\t\tStringBuffer sql = new StringBuffer();\r\n");
                out.write("\t\tsql.append(\" select " + tableSimpleName + ".*\");\r\n");
                out.write("\t\tsql.append(\" from " + tableName + " as " + tableSimpleName + "\");\r\n");
                out.write("\t\tsql.append(\" where 1=1\");\r\n");

                // 循环拼接条件sql
                for (int i = 0; i < searchFieldList.size(); i++) {
                    CGSearchFiled searchFiled = searchFieldList.get(i);
                    // 字段信息
                    CommonFieldInfo fieldInfo = searchFiled.getFieldInfo();
                    // 字段名
                    String columnName = fieldInfo.getColumnName();
                    // 属性名
                    String fieldName = fieldInfo.getFieldName();
                    // 首字母大小
                    String firstChar = fieldName.substring(0, 1);
                    fieldName = firstChar.toUpperCase() + fieldName.substring(1);
                    // 查询类别
                    int searchType = searchFiled.getSearchType();
                    if(searchType == CGConstants.SEARCH_TYPE_NORMAL){
                        // 普通字符查询
                        String variableName = "search" + fieldName;
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableName + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " like '%\" + SqlUtil.transferValue(" + variableName + ") + \"%'\");\r\n");
                        out.write("\t\t}\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_NUMBER_AREA){
                        // 数字范围查询
                        String variableNameStart = "search" + fieldName + "Start";
                        String variableNameEnd = "search" + fieldName + "End";
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableNameStart + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " >= \" + GlobalFunc.parseDouble(" + variableNameStart + "));\r\n");
                        out.write("\t\t}\r\n");
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableNameEnd + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " <= \" + GlobalFunc.parseDouble(" + variableNameEnd + "));\r\n");
                        out.write("\t\t}\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_NUMBER_SMART){
                        // 数字智能查询
                        String variableName = "search" + fieldName;
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableName + ")) {\r\n");
                        out.write("\t\t\tsql.append(SqlUtil.andSmartNumber(\"" + tableSimpleName + "." + columnName + "\", " + variableName + "));\r\n");
                        out.write("\t\t}\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_INT_AREA){
                        // 整数范围查询
                        String variableNameStart = "search" + fieldName + "Start";
                        String variableNameEnd = "search" + fieldName + "End";
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableNameStart + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " >= \" + GlobalFunc.parseInt(" + variableNameStart + "));\r\n");
                        out.write("\t\t}\r\n");
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableNameEnd + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " <= \" + GlobalFunc.parseInt(" + variableNameEnd + "));\r\n");
                        out.write("\t\t}\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_DATE_AREA){
                        // 日期范围查询
                        String variableNameStart = "search" + fieldName + "Start";
                        String variableNameEnd = "search" + fieldName + "End";
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableNameStart + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " >= '\" + " + variableNameStart + " + \"'\");\r\n");
                        out.write("\t\t}\r\n");
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableNameEnd + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " <= '\" + " + variableNameEnd + " + \"'\");\r\n");
                        out.write("\t\t}\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_SELECT_SINGLE){
                        // 固定下拉框单选查询
                        String variableName = "search" + fieldName;
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableName + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + " = '\" + SqlUtil.transferValue(" + variableName + ") + \"'\");\r\n");
                        out.write("\t\t}\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
                        // 固定下拉框多选查询
                        String variableName = "search" + fieldName;
                        out.write("\t\tif (StringUtils.isNotBlank(" + variableName + ")) {\r\n");
                        out.write("\t\t\tsql.append(\" and " + tableSimpleName + "." + columnName + "\" + SqlUtil.equalOrIn(ArrayUtil.split(" + variableName + ", \",\")));\r\n");
                        out.write("\t\t}\r\n");
                    }
                }
                out.write("\r\n");

                out.write("\t\t// 设置分页位置，使下一次查询分页\r\n");
                out.write("\t\tPageHelper.startPage(page, rows);\r\n");
                out.write("\t\t// 查询\r\n");
                out.write("\t\tList<Map<String, Object>> dataList = sqlMapper.sqlQueryList(sql.toString());\r\n");
                out.write("\t\t// 获取分页数据\r\n");
                out.write("\t\tPageInfo<Map<String,Object>> pageInfo = new PageInfo<>(dataList);\r\n");
                out.write("\t\tList<Map<String, Object>> pageList = pageInfo.getList();\r\n");
                out.write("\t\tlong total = pageInfo.getTotal();\r\n");
                out.write("\t\t// 下划线转驼峰\r\n");
                out.write("\t\tpageList = commonService.convertMapKeyFromUnderlineToUpperCase(pageList);\r\n");
                out.write("\t\t// 组装\r\n");
                out.write("\t\tMap<String, Object> data = new HashMap<>();\r\n");
                out.write("\t\tdata.put(\"rows\", pageList);\r\n");
                out.write("\t\tdata.put(\"total\", total);\r\n");
                out.write("\r\n");

                out.write("\t\treturn data;\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("\t@Override\r\n");
                out.write("\tpublic Map<String, Object> save(HttpServletRequest request) {\r\n");
                // 主键字段
                CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
                String keyFieldName = keyFieldInfo.getFieldName();
                // 遍历所有字段
                for (int i = 0; i < fieldInfoList.size(); i++) {
                    CommonFieldInfo fieldInfo = fieldInfoList.get(i);
                    String fieldName = fieldInfo.getFieldName();
                    out.write("\t\t// " + fieldInfo.getAnnotateName() + "\r\n");
                    out.write("\t\tString " + fieldName + " = GlobalFunc.toString(request.getParameter(\"" + fieldName + "\"));\r\n");
                }
                if(inputSetting.isFileUpload()){
                    // 有附件上传
                    out.write("\t\t// 附件上传mainId\r\n");
                    out.write("\t\tString mainId = GlobalFunc.toString(request.getParameter(\"mainId\"));\r\n");
                }
                out.write("\r\n");

                out.write("\t\t" + modelName + " " + modelVariableName + ";\r\n");
                out.write("\t\tif(StringUtils.isBlank(" + keyFieldName + ")){\r\n");
                out.write("\t\t\t// 新增\r\n");
                out.write("\t\t\t" + modelVariableName + " = new " + modelName + "();\r\n");
                out.write("\t\t\t" + modelVariableName + "." + keyFieldInfo.getSetMethodName() + "(Sequence.getSequence());\r\n");
                out.write("\t\t}else{\r\n");
                out.write("\t\t\t// 修改\r\n");
                out.write("\t\t\t" + modelVariableName + " = this.getById(" + keyFieldName + ");\r\n");
                out.write("\t\t}\r\n");
                // 遍历非主键字段，开始赋值
                for (int i = 0; i < fieldInfoList.size(); i++) {
                    CommonFieldInfo fieldInfo = fieldInfoList.get(i);
                    String fieldName = fieldInfo.getFieldName();
                    if(fieldName.equals(keyFieldName)){
                        continue;
                    }
                    out.write("\t\t" + modelVariableName + "." + fieldInfo.getSetMethodName() + "(" + fieldName + ");\r\n");
                }
                out.write("\r\n");

                out.write("\t\tif(StringUtils.isBlank(" + keyFieldName + ")){\r\n");
                out.write("\t\t\t// 新增\r\n");
                out.write("\t\t\tthis.insert(" + modelVariableName + ");\r\n");
                if(inputSetting.isFileUpload()){
                    // 有附件上传
                    out.write("\t\t\t// 附件上传，更新mainId\r\n");
                    out.write("\t\t\tcommonFileUploadService.updateMainId(mainId, " + modelVariableName + "." + keyFieldInfo.getGetMethodName() + "());\r\n");
                }
                out.write("\t\t}else{\r\n");
                out.write("\t\t\t// 修改\r\n");
                out.write("\t\t\tthis.update(" + modelVariableName + ");\r\n");
                out.write("\t\t}\r\n");
                out.write("\t\tMap<String, Object> map = new HashMap<>();\r\n");
                out.write("\t\tmap.put(\"result\", \"success\");\r\n");
                out.write("\t\tmap.put(\"message\", \"保存成功！\");\r\n");
                out.write("\t\treturn map;\r\n");
                out.write("\t}\r\n");

                out.write("}\r\n");
                out.flush();
                out.close();
                System.out.print("生成成功！\n");
            } catch (IOException e) {
                System.out.print("生成失败！\n");
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成Controller
     * @param inputSetting 设置
     * @param packagePath java包路径
     */
    private static void generatorController(CGInputSetting inputSetting, String packagePath){
        // 类信息
        CommonClassInfo classInfo = inputSetting.getClassInfo();
        // 实体类名
        String modelName = inputSetting.getModelName();
        // 实体变量名
        String modelVariableName = inputSetting.getModelVariableName();
        // 实体类中文名
        String chineseModelName = inputSetting.getChineseModelName();
        // 表名
        String tableName = classInfo.getTableName();
        // 字段信息集合
        List<CommonFieldInfo> fieldInfoList = classInfo.getCommonFieldInfoList();
        // 控制层文件夹绝对路径
        String controllerPackageAbsolutePath = packagePath + File.separator + inputSetting.getPackageRelativePathPrefix(false)
                + File.separator + "controller" + File.separator + inputSetting.getPackageRelativePathSuffix(false);
        // 控制层绝对路径
        String controllerAbsolutePath = controllerPackageAbsolutePath + File.separator + modelName + "Controller" + ".java";
        // 文件日期
        String fileDate = CGEngine.getFileDate();
        // 接口包路径
        String interfacePack = inputSetting.getPackageRelativePackPrefix() + ".service." + inputSetting.getPackageRelativePackSuffix() + "." + modelName + "Service";

        // 创建文件夹
        File controllerPackageFile = new File(controllerPackageAbsolutePath);
        if(!controllerPackageFile.exists()){
            controllerPackageFile.mkdirs();
        }

        // 生成控制层文件
        File controllerFile = new File(controllerAbsolutePath);
        // 是否创建文件
        boolean createControllerFile = true;
        if(controllerFile.exists()){
            // 文件已存在
            if(inputSetting.isCoveredCode()){
                // 覆盖代码，先删除
                controllerFile.delete();
                CGMain.print("【" + modelName + "Controller.java】文件已存在，开始覆盖！\n");
            }else{
                // 不覆盖代码
                createControllerFile = false;
                CGMain.print("【" + modelName + "Controller.java】文件已存在，跳过！\n");
            }
        }
        if(createControllerFile){
            System.out.print("开始生成【" + modelName + "Controller.java】。。。");
            try {
                controllerFile = new File(controllerAbsolutePath);
                controllerFile.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(controllerFile));
                out.write("package " + inputSetting.getPackageRelativePackPrefix() + ".controller." + inputSetting.getPackageRelativePackSuffix() + ";\r\n");
                out.write("\r\n");

                out.write("import " + inputSetting.getModelPack() + ";\r\n");
                out.write("import " + interfacePack + ";\r\n");
                out.write("import com.ncxdkj.util.GlobalFunc;\r\n");
                out.write("import org.apache.commons.lang3.StringUtils;\r\n");
                out.write("import org.springframework.beans.factory.annotation.Autowired;\r\n");
                out.write("import org.springframework.stereotype.Controller;\r\n");
                out.write("import org.springframework.ui.Model;\r\n");
                out.write("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
                out.write("import org.springframework.web.bind.annotation.RequestMethod;\r\n");
                out.write("import org.springframework.web.bind.annotation.ResponseBody;\r\n");
                out.write("\r\n");

                out.write("import javax.servlet.http.HttpServletRequest;\r\n");
                out.write("import java.util.HashMap;\r\n");
                out.write("import java.util.Map;\r\n");
                out.write("\r\n");

                out.write("/**\r\n");
                out.write(" * @Description " + chineseModelName + "Controller\r\n");
                out.write(" * @author " + inputSetting.getAuthor() + "\r\n");
                out.write(" * @date " + fileDate + "\r\n");
                out.write(" * @version " + inputSetting.getVersion() + "\r\n");
                out.write(" */\r\n");
                out.write("@Controller\r\n");
                out.write("@RequestMapping(\"/" + inputSetting.getPackageRelativePathSuffix(true) + "/" + modelVariableName + "\")\r\n");
                out.write("public class " + modelName + "Controller {\r\n");
                out.write("\r\n");

                out.write("\t@Autowired\r\n");
                out.write("\tprivate " + modelName + "Service " + modelVariableName + "Service;\r\n");
                out.write("\r\n");

                out.write("\t/**\r\n");
                out.write("\t * list页面\r\n");
                out.write("\t * @return jsp\r\n");
                out.write("\t */\r\n");
                out.write("\t@RequestMapping(value = \"/list\", method = {RequestMethod.GET, RequestMethod.POST})\r\n");
                out.write("\tpublic String list(){\r\n");
                out.write("\t\treturn \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/list" + modelName + "\";\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("\t/**\r\n");
                out.write("\t * 分页获取" + chineseModelName + "\r\n");
                out.write("\t * @param request request\r\n");
                out.write("\t * @return data\r\n");
                out.write("\t */\r\n");
                out.write("\t@ResponseBody\r\n");
                out.write("\t@RequestMapping(value=\"/get" + modelName + "List\",method= {RequestMethod.GET, RequestMethod.POST})\r\n");
                out.write("\tpublic Map<String, Object> get" + modelName + "List(HttpServletRequest request) {\r\n");
                out.write("\t\tMap<String, Object> map = " + modelVariableName + "Service.get" + modelName + "List(request);\r\n");
                out.write("\t\treturn map;\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_WINDOW){
                    // 弹窗
                    out.write("\t/**\r\n");
                    out.write("\t * 新增、修改页面\r\n");
                    out.write("\t * @return jsp\r\n");
                    out.write("\t */\r\n");
                    out.write("\t@RequestMapping(value = \"/addOrEdit" + modelName + "\", method = {RequestMethod.GET, RequestMethod.POST})\r\n");
                    out.write("\tpublic String addOrEdit" + modelName + "(HttpServletRequest request, Model model){\r\n");
                    out.write("\t\tString id = GlobalFunc.toString(request.getParameter(\"id\"));\r\n");
                    out.write("\t\tif(StringUtils.isNotBlank(id)){\r\n");
                    out.write("\t\t\t" + modelName + " " + modelVariableName + " = " + modelVariableName + "Service.getById(id);\r\n");
                    out.write("\t\t\tmodel.addAttribute(\"" + modelVariableName + "\", " + modelVariableName + ");\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\t\treturn \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/addOrEdit" + modelName + "\";\r\n");
                    out.write("\t}\r\n");
                }else if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_URL){
                    // 跳转
                    out.write("\t/**\r\n");
                    out.write("\t * 新增、修改页面\r\n");
                    out.write("\t * @return jsp\r\n");
                    out.write("\t */\r\n");
                    out.write("\t@RequestMapping(value = \"/addOrEdit" + modelName + "\", method = {RequestMethod.GET, RequestMethod.POST})\r\n");
                    out.write("\tpublic String addOrEdit" + modelName + "(HttpServletRequest request, Model model){\r\n");
                    out.write("\t\tString id = GlobalFunc.toString(request.getParameter(\"id\"));\r\n");
                    out.write("\t\tif(StringUtils.isNotBlank(id)){\r\n");
                    out.write("\t\t\t\t" + modelName + " " + modelVariableName + " = " + modelVariableName + "Service.getById(id);\r\n");
                    out.write("\t\t\t\tmodel.addAttribute(\"" + modelVariableName + "\", " + modelVariableName + ");\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\t\treturn \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/addOrEdit" + modelName + "\";\r\n");
                    out.write("\t}\r\n");
                }
                out.write("\r\n");

                out.write("\t/**\r\n");
                out.write("\t * 保存数据\r\n");
                out.write("\t * @param request request\r\n");
                out.write("\t * @return data\r\n");
                out.write("\t */\r\n");
                out.write("\t@ResponseBody\r\n");
                out.write("\t@RequestMapping(value = \"/save\", method = {RequestMethod.GET, RequestMethod.POST})\r\n");
                out.write("\tpublic Map<String, Object> save(HttpServletRequest request) throws InterruptedException {\r\n");
                out.write("\t\treturn " + modelVariableName + "Service.save(request);\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("\t/**\r\n");
                out.write("\t * 删除数据\r\n");
                out.write("\t * @param request request\r\n");
                out.write("\t * @return data\r\n");
                out.write("\t */\r\n");
                out.write("\t@ResponseBody\r\n");
                out.write("\t@RequestMapping(value = \"/delete\", method = {RequestMethod.GET, RequestMethod.POST})\r\n");
                out.write("\tpublic Map<String, Object> delete(HttpServletRequest request) throws InterruptedException {\r\n");
                out.write("\t\tString id = GlobalFunc.toString(request.getParameter(\"id\"));\r\n");
                out.write("\t\tint count = " + modelVariableName + "Service.delete(id);\r\n");
                out.write("\t\tString message = count > 0 ? \"删除成功！\" : \"操作失败！\";\r\n");
                out.write("\t\tMap<String, Object> map = new HashMap<>();\r\n");
                out.write("\t\tmap.put(\"message\", message);\r\n");
                out.write("\t\treturn map;\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                out.write("}\r\n");
                out.flush();
                out.close();
                System.out.print("生成成功！\n");
            } catch (IOException e) {
                System.out.print("生成失败！\n");
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成Jsp
     * @param inputSetting 设置
     * @param packagePath java包路径
     */
    private static void generatorJsp(CGInputSetting inputSetting, String packagePath){
        // 类信息
        CommonClassInfo classInfo = inputSetting.getClassInfo();
        // 实体类名
        String modelName = inputSetting.getModelName();
        // 实体变量名
        String modelVariableName = inputSetting.getModelVariableName();
        // 实体类中文名
        String chineseModelName = inputSetting.getChineseModelName();
        // 表名
        String tableName = classInfo.getTableName();
        // 主键
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        // 字段信息集合
        List<CommonFieldInfo> fieldInfoList = classInfo.getCommonFieldInfoList();
        // 非主键字段
        List<CommonFieldInfo> fieldInfoWithOutKeyList = new ArrayList<>(fieldInfoList);
        for (int i = 0; i < fieldInfoWithOutKeyList.size(); i++) {
            CommonFieldInfo fieldInfo = fieldInfoWithOutKeyList.get(i);
            if(fieldInfo.getFieldName().equals(keyFieldInfo.getFieldName())){
                // 删除主键
                fieldInfoWithOutKeyList.remove(i);
                i--;
            }
        }
        // jsp文件夹绝对路径
        String jspRootPath = new String(CGConstants.JSP_PACKAGE_PATH);
        if(jspRootPath.startsWith("../")){
            jspRootPath = jspRootPath.substring(3);
            packagePath = packagePath.substring(0, packagePath.lastIndexOf("\\"));
        }
        String jspPackageAbsolutePath = packagePath + File.separator + jspRootPath + File.separator + inputSetting.getPackageRelativePathSuffix(false);
        // listxxx.jsp绝对路径
        String listJspAbsolutePath = jspPackageAbsolutePath + File.separator + "list" + modelName + ".jsp";
        // addOrEditxxx.jsp绝对路径
        String addOrEditJspAbsolutePath = jspPackageAbsolutePath + File.separator + "addOrEdit" + modelName + ".jsp";

        // 创建文件夹
        File jspPackageFile = new File(jspPackageAbsolutePath);
        if(!jspPackageFile.exists()){
            jspPackageFile.mkdirs();
        }

        // 生成列表jsp文件
        File jspFile = new File(listJspAbsolutePath);
        // 是否创建文件
        boolean createJspFile = true;
        if(jspFile.exists()){
            // 文件已存在
            if(inputSetting.isCoveredCode()){
                // 覆盖代码，先删除
                jspFile.delete();
                CGMain.print("【list" + modelName + ".jsp】文件已存在，开始覆盖！\n");
            }else{
                // 不覆盖代码
                createJspFile = false;
                CGMain.print("【list" + modelName + ".jsp】文件已存在，跳过！\n");
            }
        }
        if(createJspFile){
            System.out.print("开始生成【list" + modelName + ".jsp】。。。");
            try {
                jspFile = new File(listJspAbsolutePath);
                jspFile.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(jspFile));

                out.write("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\r\n");
                out.write("<%@ include file=\"/WEB-INF/jsp/common/easyUILibraries.jsp\"%>\r\n");
                out.write("<!DOCTYPE html>\r\n");
                out.write("<head>\r\n");
                out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
                out.write("</head>\r\n");
                out.write("<body>\r\n");
                out.write("<script type=\"text/javascript\">\r\n");
                out.write("\t$.util.namespace(\"$.easyui\");\r\n");
                out.write("\t$(function(){\r\n");
                out.write("\t\tvar cookieTheme = $.cookie(\"themeName\");\r\n");
                out.write("\t\t$.easyui.theme(cookieTheme);\r\n");
                out.write("\t\tvar t = $(\"#list" + modelName + "DataGrid\").datagrid({\r\n");
                out.write("\t\t\ttitle: \"\",//datagrid标题\r\n");
                out.write("\t\t\tmethod: \"post\",\r\n");
                out.write("\t\t\turl : \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/" + modelVariableName + "/get" + modelName + "List\",\r\n");
                out.write("\t\t\tidField: \"id\",//是否跨页仍然记住选中的行\r\n");
                out.write("\t\t\tremoteSort: false,\r\n");
                out.write("\t\t\tpageSize:10,//每页显示几条数据\r\n");
                out.write("\t\t\tpageList:[10,20,30,40,50,60,70,80,90,100],//可选中每页显示的条数\r\n");
                out.write("\t\t\tfit:true,//自适应屏幕宽高\r\n");
                out.write("\t\t\tfitColumns:true,//字段平铺\r\n");
                out.write("\t\t\tborder:false,\r\n");
                out.write("\t\t\tpagination: true,\r\n");
                out.write("\t\t\tsingleSelect:true,\r\n");
                out.write("\t\t\ttoolbar : [\r\n");

                if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_WINDOW){
                    // 窗口
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t id : \"toolbar_add\",\r\n");
                    out.write("\t\t\t\t\ttext : \"新增\",\r\n");
                    out.write("\t\t\t\t\ticonCls : \"icon-add\",\r\n");
                    out.write("\t\t\t\t\thandler : function(){\r\n");
                    out.write("\t\t\t\t\t\taddOrEdit" + modelName + "();\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t\tid : \"rightbtn_edit\",\r\n");
                    out.write("\t\t\t\t\ttext : \"修改\",\r\n");
                    out.write("\t\t\t\t\ticonCls : \"icon-edit\",\r\n");
                    out.write("\t\t\t\t\thandler : function(){\r\n");
                    out.write("\t\t\t\t\t\tvar rowData = $(\"#list" + modelName + "DataGrid\").datagrid(\"getSelected\");\r\n");
                    out.write("\t\t\t\t\t\tif(rowData == null){\r\n");
                    out.write("\t\t\t\t\t\t\tlayer.msg(\"请选择需要修改的数据！\");\r\n");
                    out.write("\t\t\t\t\t\t\treturn false;\r\n");
                    out.write("\t\t\t\t\t\t}else{\r\n");
                    out.write("\t\t\t\t\t\t\taddOrEdit" + modelName + "(rowData[\"id\"]);\r\n");
                    out.write("\t\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                } else if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_URL){
                    // 跳转
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t\tid : \"toolbar_add\",\r\n");
                    out.write("\t\t\t\t\ttext : \"新增\",\r\n");
                    out.write("\t\t\t\t\ticonCls : \"icon-add\",\r\n");
                    out.write("\t\t\t\t\thandler : function(){\r\n");
                    out.write("\t\t\t\t\t\taddOrEdit" + modelName + "();\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t\tid : \"rightbtn_edit\",\r\n");
                    out.write("\t\t\t\t\ttext : \"修改\",\r\n");
                    out.write("\t\t\t\t\ticonCls : \"icon-edit\",\r\n");
                    out.write("\t\t\t\t\thandler : function(){\r\n");
                    out.write("\t\t\t\t\t\tvar rowData = $(\"#list" + modelName + "DataGrid\").datagrid(\"getSelected\");\r\n");
                    out.write("\t\t\t\t\t\tif(rowData == null){\r\n");
                    out.write("\t\t\t\t\t\t\tlayer.msg(\"请选择需要修改的数据！\");\r\n");
                    out.write("\t\t\t\t\t\t\treturn false;\r\n");
                    out.write("\t\t\t\t\t\t}else{\r\n");
                    out.write("\t\t\t\t\t\t\taddOrEdit" + modelName + "(rowData[\"id\"]);\r\n");
                    out.write("\t\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                }

                out.write("\t\t\t\t{\r\n");
                out.write("\t\t\t\t\tid : \"toolbar_del\",\r\n");
                out.write("\t\t\t\t\ttext : \"删除\",\r\n");
                out.write("\t\t\t\t\ticonCls : \"icon-add\",\r\n");
                out.write("\t\t\t\t\thandler : function(){\r\n");
                out.write("\t\t\t\t\t\tvar rowData = $(\"#list" + modelName + "DataGrid\").datagrid(\"getSelected\");\r\n");
                out.write("\t\t\t\t\t\tif(rowData == null){\r\n");
                out.write("\t\t\t\t\t\t\tlayer.msg(\"请选择需要删除的数据！\");\r\n");
                out.write("\t\t\t\t\t\t\treturn false;\r\n");
                out.write("\t\t\t\t\t\t}else{\r\n");
                out.write("\t\t\t\t\t\t\tdelete" + modelName + "(rowData[\"id\"]);\r\n");
                out.write("\t\t\t\t\t\t}\r\n");
                out.write("\t\t\t\t\t}\r\n");
                out.write("\t\t\t\t}");

                if(inputSetting.isFileUpload()){
                    // 开启上传附件
                    out.write(",\r\n");
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t\tid : \"toolbar_file\",\r\n");
                    out.write("\t\t\t\t\ttext : \"上传附件\",\r\n");
                    out.write("\t\t\t\t\ticonCls : \"icon-file\",\r\n");
                    out.write("\t\t\t\t\thandler : function(){\r\n");
                    out.write("\t\t\t\t\tvar rowData = $(\"#list" + modelName + "DataGrid\").datagrid(\"getSelected\");\r\n");
                    out.write("\t\t\t\t\t\tif(rowData == null){\r\n");
                    out.write("\t\t\t\t\t\t\tlayer.msg(\"请选择需要上传附件的数据！\");\r\n");
                    out.write("\t\t\t\t\t\t\treturn false;\r\n");
                    out.write("\t\t\t\t\t\t}else{\r\n");
                    out.write("\t\t\t\t\t\t\taddFile(rowData[\"id\"]);\r\n");
                    out.write("\t\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t}\r\n");
                }else{
                    out.write("\r\n");
                }

                out.write("\t\t\t],\r\n");
                out.write("\t\t\tfrozenColumns: [[\r\n");
                out.write("\t\t\t\t{ field: \"ck\", checkbox: true }\r\n");
                out.write("\t\t\t]],\r\n");
                out.write("\t\t\tcolumns: [[\r\n");

                // 主键
                out.write("\t\t\t\t{field: \"" + keyFieldInfo.getFieldName() + "\", hidden: true},\r\n");
                // 非主键，全部显示
                for (int i = 0; i < fieldInfoWithOutKeyList.size(); i++) {
                    CommonFieldInfo fieldInfo = fieldInfoWithOutKeyList.get(i);
                    out.write("\t\t\t\t{field: \"" + fieldInfo.getFieldName() + "\", title: \"" + fieldInfo.getAnnotateName() + "\", width: 100, sortable: true, align: \"center\"}");
                    if(i < (fieldInfoWithOutKeyList.size() - 1)){
                        out.write(",");
                    }
                    out.write("\r\n");
                }

                out.write("\t\t\t]],\r\n");
                out.write("\t\t\trowContextMenu: [\r\n");

                if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_WINDOW){
                    // 窗口
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t\tid: \"rightbtn_edit\",\r\n");
                    out.write("\t\t\t\t\ttext: \"修改\",\r\n");
                    out.write("\t\t\t\t\ticonCls: \"icon-context-edit\",\r\n");
                    out.write("\t\t\t\t\thandler: function (e, index,row) {\r\n");
                    out.write("\t\t\t\t\t\taddOrEdit" + modelName + "(row[\"id\"]);\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\t{ id: \"toolbar_xin\",\r\n");
                    out.write("\t\t\t\t\ttext: \"新增\",\r\n");
                    out.write("\t\t\t\t\ticonCls: \"icon-context-add\",\r\n");
                    out.write("\t\t\t\t\thandler: function (e, index,row) {\r\n");
                    out.write("\t\t\t\t\t\taddOrEdit" + modelName + "();\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                } else if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_URL){
                    // 跳转
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t\tid: \"rightbtn_edit\",\r\n");
                    out.write("\t\t\t\t\ttext: \"修改\",\r\n");
                    out.write("\t\t\t\t\ticonCls: \"icon-context-edit\",\r\n");
                    out.write("\t\t\t\t\thandler: function (e, index,row) {\r\n");
                    out.write("\t\t\t\t\t\taddOrEdit" + modelName + "(row[\"id\"]);\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\t{\r\n");
                    out.write("\t\t\t\t\tid: \"toolbar_xin\",\r\n");
                    out.write("\t\t\t\t\ttext: \"新增\",\r\n");
                    out.write("\t\t\t\t\ticonCls: \"icon-context-add\",\r\n");
                    out.write("\t\t\t\t\thandler: function (e, index,row) {\r\n");
                    out.write("\t\t\t\t\t\taddOrEdit" + modelName + "();\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                }

                out.write("\t\t\t\t{\r\n");
                out.write("\t\t\t\t\tid: \"toolbar_del\",\r\n");
                out.write("\t\t\t\t\ttext: \"删除\",\r\n");
                out.write("\t\t\t\t\ticonCls: \"icon-context-del\",\r\n");
                out.write("\t\t\t\t\thandler: function (e, index,row) {\r\n");
                out.write("\t\t\t\t\t\tdelete" + modelName + "(row[\"id\"]);\r\n");
                out.write("\t\t\t\t\t}\r\n");
                out.write("\t\t\t\t}");

                if(inputSetting.isFileUpload()){
                    // 开启上传附件
                    out.write(",\r\n");
                    out.write("\t\t\t\t{ id: \"toolbar_file\",\r\n");
                    out.write("\t\t\t\t\ttext: \"上传附件\",\r\n");
                    out.write("\t\t\t\t\ticonCls: \"icon-context-file\",\r\n");
                    out.write("\t\t\t\t\thandler: function (e, index,row) {\r\n");
                    out.write("\t\t\t\t\t\taddFile(row[\"id\"]);\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t}\r\n");
                }else{
                    out.write("\r\n");
                }

                out.write("\t\t\t],\r\n");
                out.write("\t\t\tautoFocusField: \"\",\r\n");
                out.write("\t\t\tautoEditing: false,             //该属性启用双击行时自定开启该行的编辑状态\r\n");
                out.write("\t\t\textEditing: true,               //该属性启用行编辑状态的 ExtEditing 风格效果，该属性默认为 true。\r\n");
                out.write("\t\t\tsingleEditing: true,            //该属性启用datagrid的只允许单行编辑效果，该属性默认为 true。\r\n");
                out.write("\t\t\tonLoadSuccess:function(data){   //加载完成后执行方法\r\n");
                out.write("\t\t\t}\r\n");
                out.write("\t\t});\r\n");
                out.write("\r\n");

                out.write("\t\t//查询方法\r\n");
                out.write("\t\t$.easyui.search = function(){\r\n");
                out.write("\t\t\t$(\"#list" + modelName + "DataGrid\").datagrid(\"load\",{\r\n");

                // 遍历查询字段
                List<CGSearchFiled> searchFieldList = inputSetting.getSearchFieldList();
                for (int i = 0; i < searchFieldList.size(); i++) {
                    CGSearchFiled searchFiled = searchFieldList.get(i);
                    // 字段信息
                    CommonFieldInfo fieldInfo = searchFiled.getFieldInfo();
                    // 字段名
                    String columnName = fieldInfo.getColumnName();
                    // 属性名
                    String fieldName = fieldInfo.getFieldName();
                    // 首字母大小
                    String firstChar = fieldName.substring(0, 1);
                    fieldName = firstChar.toUpperCase() + fieldName.substring(1);
                    // 查询类别
                    int searchType = searchFiled.getSearchType();
                    if(searchType == CGConstants.SEARCH_TYPE_NORMAL){
                        // 普通字符查询
                        String variableName = "search" + fieldName;
                        out.write("\t\t\t\t" + variableName + " : $(\"#search_" + columnName + "\").val()");
                    }else if(searchType == CGConstants.SEARCH_TYPE_NUMBER_AREA){
                        // 数字范围查询
                        String variableNameStart = "search" + fieldName + "Start";
                        String variableNameEnd = "search" + fieldName + "End";
                        out.write("\t\t\t\t" + variableNameStart + " : $(\"#search_" + columnName + "_start\").val(),\r\n");
                        out.write("\t\t\t\t" + variableNameEnd + " : $(\"#search_" + columnName + "_end\").val()");
                    }else if(searchType == CGConstants.SEARCH_TYPE_NUMBER_SMART){
                        // 数字智能查询
                        String variableName = "search" + fieldName;
                        out.write("\t\t\t\t" + variableName + " : $(\"#search_" + columnName + "\").val()");
                    }else if(searchType == CGConstants.SEARCH_TYPE_INT_AREA){
                        // 整数范围查询
                        String variableNameStart = "search" + fieldName + "Start";
                        String variableNameEnd = "search" + fieldName + "End";
                        out.write("\t\t\t\t" + variableNameStart + " : $(\"#search_" + columnName + "_start\").val(),\r\n");
                        out.write("\t\t\t\t" + variableNameEnd + " : $(\"#search_" + columnName + "_end\").val()");
                    }else if(searchType == CGConstants.SEARCH_TYPE_DATE_AREA){
                        // 日期范围查询
                        String variableNameStart = "search" + fieldName + "Start";
                        String variableNameEnd = "search" + fieldName + "End";
                        out.write("\t\t\t\t" + variableNameStart + " : $(\"#search_" + columnName + "_start\").datebox(\"getValue\"),\r\n");
                        out.write("\t\t\t\t" + variableNameEnd + " : $(\"#search_" + columnName + "_end\").datebox(\"getValue\")");
                    }else if(searchType == CGConstants.SEARCH_TYPE_SELECT_SINGLE){
                        // 固定下拉框单选查询
                        String variableName = "search" + fieldName;
                        out.write("\t\t\t\t" + variableName + " : $(\"#search_" + columnName + "\").combobox(\"getValue\")");
                    }else if(searchType == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
                        // 固定下拉框多选查询
                        String variableName = "search" + fieldName;
                        out.write("\t\t\t\t" + variableName + " : $(\"#search_" + columnName + "\").val(\"getValues\")");
                    }
                    if(i < (searchFieldList.size() - 1)){
                        out.write(",");
                    }
                    out.write("\r\n");
                }

                out.write("\t\t\t});\r\n");
                out.write("\t\t};\r\n");
                out.write("\r\n");

                out.write("\t\t//清空查询条件\r\n");
                out.write("\t\t$.easyui.clean = function(){\r\n");
                out.write("\t\t\t$(\"#list" + modelName + "DataGrid\").datagrid(\"load\",{});\r\n");

                // 遍历查询字段
                for (int i = 0; i < searchFieldList.size(); i++) {
                    CGSearchFiled searchFiled = searchFieldList.get(i);
                    // 字段信息
                    CommonFieldInfo fieldInfo = searchFiled.getFieldInfo();
                    // 字段名
                    String columnName = fieldInfo.getColumnName();
                    // 属性名
                    String fieldName = fieldInfo.getFieldName();
                    // 首字母大小
                    String firstChar = fieldName.substring(0, 1);
                    fieldName = firstChar.toUpperCase() + fieldName.substring(1);
                    // 查询类别
                    int searchType = searchFiled.getSearchType();
                    if(searchType == CGConstants.SEARCH_TYPE_NORMAL){
                        // 普通字符查询
                        out.write("\t\t\t$(\"#search_" + columnName + "\").val(\"\");\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_NUMBER_AREA){
                        // 数字范围查询
                        out.write("\t\t\t$(\"#search_" + columnName + "_start\").val(\"\");\r\n");
                        out.write("\t\t\t$(\"#search_" + columnName + "_end\").val(\"\");\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_NUMBER_SMART){
                        // 数字智能查询
                        out.write("\t\t\t$(\"#search_" + columnName + "\").val(\"\");\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_INT_AREA){
                        // 整数范围查询
                        out.write("\t\t\t$(\"#search_" + columnName + "_start\").val(\"\");\r\n");
                        out.write("\t\t\t$(\"#search_" + columnName + "_end\").val(\"\");\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_DATE_AREA){
                        // 日期范围查询
                        out.write("\t\t\t$(\"#search_" + columnName + "_start\").datebox(\"setValue\", \"\");\r\n");
                        out.write("\t\t\t$(\"#search_" + columnName + "_end\").datebox(\"setValue\", \"\");\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_SELECT_SINGLE){
                        // 固定下拉框单选查询
                        out.write("\t\t\t$(\"#search_" + columnName + "\").combobox(\"setValue\", \"\");\r\n");
                    }else if(searchType == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
                        // 固定下拉框多选查询
                        out.write("\t\t\t$(\"#search_" + columnName + "\").combobox(\"setValues\", []);\r\n");
                    }
                }

                out.write("\t\t};\r\n");
                out.write("\t});\r\n");
                out.write("\r\n");

                if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_WINDOW){
                    // 窗口
                    out.write("\t/**\r\n");
                    out.write("\t * 新增或修改数据\r\n");
                    out.write("\t * @param id 数据id\r\n");
                    out.write("\t */\r\n");
                    out.write("\tfunction addOrEdit" + modelName + "(id) {\r\n");
                    out.write("\t\tif(typeof(id) == \"undefined\"){\r\n");
                    out.write("\t\t\tid = \"\";\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\t\t// 标题\r\n");
                    out.write("\t\tvar title = id ? \"修改\" : \"新增\";\r\n");
                    out.write("\t\tvar url = \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/" + modelVariableName + "/addOrEdit" + modelName + "?id=\" + id;\r\n");
                    out.write("\r\n");

                    out.write("\t\t// 自适应高度\r\n");
                    out.write("\t\tvar height = 600;\r\n");
                    out.write("\t\tvar bodyHeight = $(\"body\").height();\r\n");
                    out.write("\t\tif(height > bodyHeight){\r\n");
                    out.write("\t\t\theight = bodyHeight - 10;\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\r\n");

                    out.write("\t\tvar layerIndex = layer.open({\r\n");
                    out.write("\t\t\ttype: 2,\r\n");
                    out.write("\t\t\ttitle: title,\r\n");
                    out.write("\t\t\tarea: [\"800px\", height + \"px\"], // 宽高\r\n");
                    out.write("\t\t\tcontent:url,\r\n");
                    out.write("\t\t\tbtn: [\"保存\", \"关闭\"],\r\n");
                    out.write("\t\t\tshade: 0.2, // 显示遮罩\r\n");
                    out.write("\t\t\tyes: function(index, obj){\r\n");
                    out.write("\t\t\t\tvar iframeWin = window[obj.find(\"iframe\")[0][\"name\"]];\r\n");
                    out.write("\t\t\t\t// 触发子页面保存方法\r\n");
                    out.write("\t\t\t\tiframeWin.save(\"list" + modelName + "DataGrid\", layerIndex);\r\n");
                    out.write("\t\t\t}\r\n");
                    out.write("\t\t});\r\n");
                    out.write("\t}\r\n");
                    out.write("\r\n");
                } else if(inputSetting.getAddOrEditType() == CGConstants.ADD_OR_EDIT_TYPE_URL){
                    // 跳转
                    out.write("\t/**\r\n");
                    out.write("\t * 新增或修改数据\r\n");
                    out.write("\t * @param id 数据id\r\n");
                    out.write("\t */\r\n");
                    out.write("\tfunction addOrEdit" + modelName + "(id) {\r\n");
                    out.write("\t\tif(typeof(id) == \"undefined\"){\r\n");
                    out.write("\t\t\tid = \"\";\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\t\tvar url = \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/" + modelVariableName + "/addOrEdit" + modelName + "?id=\" + id;\r\n");
                    out.write("\t\twindow.location.href = url;\r\n");
                    out.write("\t}\r\n");
                    out.write("\r\n");
                }

                out.write("\t// 删除数据\r\n");
                out.write("\tfunction delete" + modelName + "(id){\r\n");
                out.write("\t\tlayer.confirm(\"您确定要删除该条数据吗？\", {\r\n");
                out.write("\t\t\t\tid: \"confirm_window\",\r\n");
                out.write("\t\tbtn: [\"确定\", \"取消\"] //按钮\r\n");
                out.write("\t\t}, function () {\r\n");
                out.write("\t\t\t// loading层\r\n");
                out.write("\t\t\tvar loadIndex = commonLayerLoading();\r\n");
                out.write("\t\t\t$.ajax({\r\n");
                out.write("\t\t\t\turl: \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/" + modelVariableName + "/delete\",\r\n");
                out.write("\t\t\t\ttype: \"post\",\r\n");
                out.write("\t\t\t\tdata:{\r\n");
                out.write("\t\t\t\t\tid: id\r\n");
                out.write("\t\t\t\t},\r\n");
                out.write("\t\t\t\tdataType: \"json\",\r\n");
                out.write("\t\t\t\tsuccess: function(data){\r\n");
                out.write("\t\t\t\t\tlayer.msg(data[\"message\"]);\r\n");
                out.write("\t\t\t\t\t// 刷新页面，清除选中\r\n");
                out.write("\t\t\t\t\t$(\"#list" + modelName + "DataGrid\").datagrid(\"reload\");\r\n");
                out.write("\t\t\t\t\t$(\"#list" + modelName + "DataGrid\").datagrid(\"clearSelections\");\r\n");
                out.write("\t\t\t\t\t// 延时关闭loading\r\n");
                out.write("\t\t\t\t\tcommonLayerClose(loadIndex, 100);\r\n");
                out.write("\t\t\t\t},\r\n");
                out.write("\t\t\t\terror: function(){\r\n");
                out.write("\t\t\t\t\tlayer.msg(\"操作失败！\");\r\n");
                out.write("\t\t\t\t\t// 关闭loading\r\n");
                out.write("\t\t\t\t\tcommonLayerClose(loadIndex, 100);\r\n");
                out.write("\t\t\t\t}\r\n");
                out.write("\t\t\t});\r\n");
                out.write("\t\t});\r\n");
                out.write("\t}\r\n");
                out.write("\r\n");

                if(inputSetting.isFileUpload()){
                    // 开启上传附件
                    out.write("\t// 上传附件\r\n");
                    out.write("\tfunction addFile(id){\r\n");
                    out.write("\t\t// 打开窗口，url自己拼接参数，更多参数请参考CommonFileUploadConfig\r\n");
                    out.write("\t\tlayer.open({\r\n");
                    out.write("\t\t\ttype: 2,\r\n");
                    out.write("\t\t\ttitle: \"上传附件\",\r\n");
                    out.write("\t\t\tarea: [900, 480], // 宽高\r\n");
                    out.write("\t\t\tcontent: \"/common/commonFileUpload/addUpload?mainId=\" + id\r\n");
                    // 附件条件
                    CommonFileUploadConfig fileUploadConfig = inputSetting.getFileUploadConfig();
                    if(fileUploadConfig.getMinFileCount() > 0){
                        // 最小文件数量
                        out.write("\t\t\t\t+ \"&minFileCount=" + fileUploadConfig.getMinFileCount() + "\"\r\n");
                    }
                    if(fileUploadConfig.getMinFileCount() > 0){
                        // 最大文件数量
                        out.write("\t\t\t\t+ \"&maxFileCount=" + fileUploadConfig.getMaxFileCount() + "\"\r\n");
                    }
                    if(StringUtils.isNotBlank(fileUploadConfig.getUploadPath())){
                        // 最大文件数量
                        out.write("\t\t\t\t+ \"&uploadPath=" + fileUploadConfig.getUploadPath() + "\"\r\n");
                    }
                    if(StringUtils.isNotBlank(fileUploadConfig.getExts())){
                        // 最大文件数量
                        out.write("\t\t\t\t+ \"&exts=" + fileUploadConfig.getExts() + "\"\r\n");
                    }

                    out.write("\t\t});\r\n");
                    out.write("\t}\r\n");
                    out.write("\r\n");
                }

                out.write("\t$(function(){\r\n");
                out.write("\t\t// 查询控制\r\n");

                // 遍历查询，将有条件的控制起来
                for (int i = 0; i < searchFieldList.size(); i++) {
                    CGSearchFiled searchFiled = searchFieldList.get(i);

                    int searchTypeLeft = searchFiled.getSearchType();
                    if(searchTypeLeft == CGConstants.SEARCH_TYPE_NORMAL){
                        // 普通字符查询，不需要控制
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_NUMBER_AREA){
                        // 数字范围查询
                        String areaStart = searchFiled.getAreaStart();
                        String areaEnd = searchFiled.getAreaEnd();
                        if(StringUtils.isNotBlank(areaStart) || StringUtils.isNotBlank(areaEnd)){
                            out.write("\t\t// " + searchFiled.getFieldInfo().getAnnotateName() + "\r\n");
                            out.write("\t\tonlyDouble(\"#search_" + searchFiled.getFieldInfo().getColumnName() + "_start\", \"" + areaStart + "\", \"" + areaEnd + "\");\r\n");
                            out.write("\t\tonlyDouble(\"#search_" + searchFiled.getFieldInfo().getColumnName() + "_end\", \"" + areaStart + "\", \"" + areaEnd + "\");\r\n");
                        }
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_NUMBER_SMART){
                        // 数字智能查询，不需要控制
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_INT_AREA){
                        // 整数范围查询
                        String areaStart = searchFiled.getAreaStart();
                        String areaEnd = searchFiled.getAreaEnd();
                        if(StringUtils.isNotBlank(areaStart) || StringUtils.isNotBlank(areaEnd)){
                            out.write("\t\t// " + searchFiled.getFieldInfo().getAnnotateName() + "\r\n");
                            out.write("\t\tonlyDouble(\"#search_" + searchFiled.getFieldInfo().getColumnName() + "_start\", \"" + "#search_" + searchFiled.getFieldInfo().getColumnName() + "_end\",\"" + areaStart + "\", \"" + areaEnd + "\");\r\n");
                        }
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_DATE_AREA){
                        // 日期范围查询
                        out.write("\t\t// " + searchFiled.getFieldInfo().getAnnotateName() + "\r\n");
                        out.write("\t\tsetDateBoxBetween(\"#search_" + searchFiled.getFieldInfo().getColumnName() + "_start\", \"" + "#search_" + searchFiled.getFieldInfo().getColumnName() + "_end\");\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_SELECT_SINGLE){
                        // 固定下拉框单选查询
                        List<String> selectList = searchFiled.getSelectList();
                        out.write("\t\t// " + searchFiled.getFieldInfo().getAnnotateName() + "\r\n");
                        out.write("\t\t$(\"#search_" + searchFiled.getFieldInfo().getColumnName() + "\").combobox({\r\n");
                        out.write("\t\t\ttextField: \"text\",\r\n");
                        out.write("\t\t\tvalueField: \"value\",\r\n");
                        out.write("\t\t\tmultiple: false,\r\n");
                        out.write("\t\t\teditable: false,// 不可编辑\r\n");
                        out.write("\t\t\tdata: [\r\n");

                        // 放置查询条件
                        for (int j = 0; j < selectList.size(); j++) {
                            String selectValue = selectList.get(j);
                            out.write("\t\t\t\t{\"text\": \"" + selectValue + "\", \"value\": \"" + selectValue + "\"}");
                            if(j < (selectList.size() - 1)){
                                out.write(",");
                            }
                            out.write("\r\n");
                        }

                        out.write("\t\t\t],\r\n");
                        out.write("\t\t\tonLoadSuccess: function () {\r\n");
                        out.write("\r\n");
                        out.write("\t\t\t},\r\n");
                        out.write("\t\t\tonSelect: function () {\r\n");
                        out.write("\r\n");
                        out.write("\t\t\t}\r\n");
                        out.write("\t\t});\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
                        // 固定下拉框多选查询
                        List<String> selectList = searchFiled.getSelectList();
                        out.write("\t\t// " + searchFiled.getFieldInfo().getAnnotateName() + "\r\n");
                        out.write("\t\t$(\"#search_" + searchFiled.getFieldInfo().getColumnName() + "\").combobox({\r\n");
                        out.write("\t\t\ttextField: \"text\",\r\n");
                        out.write("\t\t\tvalueField: \"value\",\r\n");
                        out.write("\t\t\tmultiple: true,\r\n");
                        out.write("\t\t\teditable: false,// 不可编辑\r\n");
                        out.write("\t\t\tdata: [\r\n");

                        // 放置查询条件
                        for (int j = 0; j < selectList.size(); j++) {
                            String selectValue = selectList.get(j);
                            out.write("\t\t\t\t{\"text\": \"" + selectValue + "\", \"value\": \"" + selectValue + "\"}");
                            if(j < (selectList.size() - 1)){
                                out.write(",");
                            }
                            out.write("\r\n");
                        }

                        out.write("\t\t\t],\r\n");
                        out.write("\t\t\tonLoadSuccess: function () {\r\n");
                        out.write("\r\n");
                        out.write("\t\t\t},\r\n");
                        out.write("\t\t\tonSelect: function () {\r\n");
                        out.write("\r\n");
                        out.write("\t\t\t}\r\n");
                        out.write("\t\t});\r\n");
                    }
                }
                if(searchFieldList.isEmpty()){
                    out.write("\r\n");
                }

                out.write("\t});\r\n");
                out.write("</script>\r\n");
                out.write("<div class=\"easyui-layout\" data-options=\"fit : true,border : false\">\r\n");
                out.write("\t<div class=\"search_area\" style=\"height: 68px;\" data-options=\"region: 'north', split: true,border:true,title:'过滤条件'\">\r\n");
                out.write("\t\t<table width=\"500px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n");

                // 遍历查询，两个放一行
                for (int i = 0; i < searchFieldList.size(); i+=2) {
                    CGSearchFiled searchFiledLeft = searchFieldList.get(i);
                    CGSearchFiled searchFiledRight = null;
                    if(i < (searchFieldList.size() - 1)){
                        searchFiledRight = searchFieldList.get(i + 1);
                    }
                    if(i == 0){
                        // 第一行
                        out.write("\t\t\t<tr class=\"normal_search_tr_area\">\r\n");
                    }else{
                        // 之后行
                        out.write("\t\t\t<tr class=\"senior_search_tr_area search_hide\">\r\n");
                    }
                    out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"5%\">" + searchFiledLeft.getFieldInfo().getAnnotateName() + "：</td>\r\n");
                    out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"28%\">\r\n");

                    // 左边查询
                    int searchTypeLeft = searchFiledLeft.getSearchType();
                    if(searchTypeLeft == CGConstants.SEARCH_TYPE_NORMAL){
                        // 普通字符查询
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "\" style=\"width: 195px; height: 26px;\"/>\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_NUMBER_AREA){
                        // 数字范围查询
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "_start\" style=\"width: 85px; height: 26px;\"/>\r\n");
                        out.write("\t\t\t\t\t<span>至</span>\r\n");
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "_end\" style=\"width: 85px; height: 26px;\"/>\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_NUMBER_SMART){
                        // 数字智能查询
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "\" class=\"common_smart_search\" style=\"width: 195px; height: 26px;\" />\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_INT_AREA){
                        // 整数范围查询
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "_start\" style=\"width: 85px; height: 26px;\"/>\r\n");
                        out.write("\t\t\t\t\t<span>至</span>\r\n");
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "_end\" style=\"width: 85px; height: 26px;\"/>\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_DATE_AREA){
                        // 日期范围查询
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "_start\" style=\"width: 85px; height: 26px;\"/>\r\n");
                        out.write("\t\t\t\t\t<span>至</span>\r\n");
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "_end\" style=\"width: 85px; height: 26px;\"/>\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_SELECT_SINGLE){
                        // 固定下拉框单选查询
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "\" style=\"width: 195px; height: 26px;\"/>\r\n");
                    }else if(searchTypeLeft == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
                        // 固定下拉框多选查询
                        out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledLeft.getFieldInfo().getColumnName() + "\" style=\"width: 195px; height: 26px;\"/>\r\n");
                    }
                    out.write("\t\t\t\t</td>\r\n");

                    // 右边查询
                    if(searchFiledRight == null){
                        out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"5%\" style=\"text-align: right;\"></td>\r\n");
                        out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"28%\"></td>\r\n");
                    }else{
                        out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"5%\" style=\"text-align: right;\">" + searchFiledRight.getFieldInfo().getAnnotateName() + "：</td>\r\n");
                        out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"28%\">\r\n");

                        int searchTypeRight = searchFiledRight.getSearchType();
                        if(searchTypeRight == CGConstants.SEARCH_TYPE_NORMAL){
                            // 普通字符查询
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "\" style=\"width: 195px; height: 26px;\"/>\r\n");
                        }else if(searchTypeRight == CGConstants.SEARCH_TYPE_NUMBER_AREA){
                            // 数字范围查询
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "_start\" style=\"width: 85px; height: 26px;\"/>\r\n");
                            out.write("\t\t\t\t\t<span>至</span>\r\n");
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "_end\" style=\"width: 85px; height: 26px;\"/>\r\n");
                        }else if(searchTypeRight == CGConstants.SEARCH_TYPE_NUMBER_SMART){
                            // 数字智能查询
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "\" class=\"common_smart_search\" style=\"width: 195px; height: 26px;\"/>\r\n");
                        }else if(searchTypeRight == CGConstants.SEARCH_TYPE_INT_AREA){
                            // 整数范围查询
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "_start\" style=\"width: 85px; height: 26px;\"/>\r\n");
                            out.write("\t\t\t\t\t<span>至</span>\r\n");
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "_end\" style=\"width: 85px; height: 26px;\"/>\r\n");
                        }else if(searchTypeRight == CGConstants.SEARCH_TYPE_DATE_AREA){
                            // 日期范围查询
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "_start\" style=\"width: 85px; height: 26px;\"/>\r\n");
                            out.write("\t\t\t\t\t<span>至</span>\r\n");
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "_end\" style=\"width: 85px; height: 26px;\"/>\r\n");
                        }else if(searchTypeRight == CGConstants.SEARCH_TYPE_SELECT_SINGLE){
                            // 固定下拉框单选查询
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "\" style=\"width: 195px; height: 26px;\"/>\r\n");
                        }else if(searchTypeRight == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
                            // 固定下拉框多选查询
                            out.write("\t\t\t\t\t<input type=\"text\" id=\"search_" + searchFiledRight.getFieldInfo().getColumnName() + "\" style=\"width: 195px; height: 26px;\"/>\r\n");
                        }
                        out.write("\t\t\t\t</td>\r\n");
                    }
                    if(i == 0){
                        // 按钮
                        out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"34%\">\r\n");
                        out.write("\t\t\t\t\t<button onclick=\"$.easyui.search();\">查询</button>\r\n");
                        out.write("\t\t\t\t\t<button onclick=\"$.easyui.clean();\">清空</button>\r\n");
                        // 查询条件超过一行
                        if(searchFieldList.size() > 2){
                            out.write("\t\t\t\t\t<button>高级查询</button>\r\n");
                        }
                        out.write("\t\t\t\t</td>\r\n");
                    }else{
                        // 空
                        out.write("\t\t\t\t<td nowrap=\"nowrap\"></td>\r\n");
                    }
                    out.write("\t\t\t</tr>\r\n");
                }
                // 无查询条件下也需要按钮
                if(searchFieldList.isEmpty()){
                    out.write("\t\t\t<tr class=\"normal_search_tr_area\">\r\n");
                    out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"5%\"></td>\r\n");
                    out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"28%\"></td>\r\n");
                    out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"5%\" style=\"text-align: right;\"></td>\r\n");
                    out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"28%\"></td>\r\n");
                    out.write("\t\t\t\t<td nowrap=\"nowrap\" width=\"34%\">\r\n");
                    out.write("\t\t\t\t\t<button onclick=\"$.easyui.search();\">查询</button>\r\n");
                    out.write("\t\t\t\t\t<button onclick=\"$.easyui.clean();\">清空</button>\r\n");
                    out.write("\t\t\t\t\t<button>高级查询</button>\r\n");
                    out.write("\t\t\t\t</td>\r\n");
                    out.write("\t\t\t</tr>\r\n");
                }

                out.write("\t\t</table>\r\n");
                out.write("\t</div>\r\n");
                out.write("\t<div data-options=\"region:'center',border:false\" >\r\n");
                out.write("\t\t<table id=\"list" + modelName + "DataGrid\" ></table>\r\n");
                out.write("\t</div>\r\n");
                out.write("</div>\r\n");
                out.write("</body>\r\n");
                out.write("</html>\r\n");
                out.write("\r\n");

                out.flush();
                out.close();
                System.out.print("生成成功！\n");
            } catch (IOException e) {
                System.out.print("生成失败！\n");
                e.printStackTrace();
            }
        }

        // 生成新增、修改jsp文件
        File addOrEditJspFile = new File(addOrEditJspAbsolutePath);
        // 是否创建文件
        boolean createAddOrEditJspFile = true;
        if(addOrEditJspFile.exists()){
            // 文件已存在
            if(inputSetting.isCoveredCode()){
                // 覆盖代码，先删除
                addOrEditJspFile.delete();
                CGMain.print("【addOrEdit" + modelName + ".jsp】文件已存在，开始覆盖！\n");
            }else{
                // 不覆盖代码
                createAddOrEditJspFile = false;
                CGMain.print("【addOrEdit" + modelName + ".jsp】文件已存在，跳过！\n");
            }
        }
        if(createAddOrEditJspFile){
            System.out.print("开始生成【addOrEdit" + modelName + ".jsp】。。。");
            try {
                addOrEditJspFile = new File(addOrEditJspAbsolutePath);
                addOrEditJspFile.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(addOrEditJspFile));

                int addOrEditType = inputSetting.getAddOrEditType();
                if(CGConstants.ADD_OR_EDIT_TYPE_WINDOW == addOrEditType){
                    // 窗口
                    out.write("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\r\n");
                    out.write("<%@ include file=\"/WEB-INF/jsp/common/easyUILibraries.jsp\"%>\r\n");
                    out.write("<!DOCTYPE html>\r\n");
                    out.write("<head >\r\n");
                    out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
                    out.write("\t<script>\r\n");

                    // 有文件上传
                    if(inputSetting.isFileUpload()){
                        out.write("\t\t// 文件上传id\r\n");
                        out.write("\t\tvar mainId;\r\n");
                    }

                    out.write("\r\n");
                    out.write("\t\t/**\r\n");
                    out.write("\t\t * 保存方法\r\n");
                    out.write("\t\t * @param tableId 父页面表格id\r\n");
                    out.write("\t\t * @param layerIndex 父页面layer窗口index\r\n");
                    out.write("\t\t */\r\n");
                    out.write("\t\tfunction save(tableId, layerIndex){\r\n");

                    // 主键取值
                    out.write("\t\t\tvar " + keyFieldInfo.getFieldName() + " = $(\"#" + keyFieldInfo.getColumnName() + "\").val();\r\n");
                    // 非主键取值
                    for (int i = 0; i < fieldInfoWithOutKeyList.size(); i++) {
                        CommonFieldInfo fieldInfo = fieldInfoWithOutKeyList.get(i);
                        String fieldName = fieldInfo.getFieldName();
                        String columnName = fieldInfo.getColumnName();
                        out.write("\t\t\tvar " + fieldName + " = $(\"#" + columnName + "\").val();\r\n");
                    }

                    out.write("\r\n");
                    out.write("\t\t\t// 父页面loading层\r\n");
                    out.write("\t\t\tvar loadIndex = window.parent.commonLayerLoading();\r\n");
                    out.write("\t\t\t$.ajax({\r\n");
                    out.write("\t\t\t\turl: \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/" + modelVariableName + "/save\",\r\n");
                    out.write("\t\t\t\ttype: \"post\",\r\n");
                    out.write("\t\t\t\tdata:{\r\n");

                    // 主键取值
                    out.write("\t\t\t\t\t" + keyFieldInfo.getFieldName() + ": " + keyFieldInfo.getFieldName() + ",\r\n");
                    // 有文件上传
                    if(inputSetting.isFileUpload()){
                        out.write("\t\t\t\t\tmainId: mainId,\r\n");
                    }
                    // 非主键取值
                    for (int i = 0; i < fieldInfoWithOutKeyList.size(); i++) {
                        CommonFieldInfo fieldInfo = fieldInfoWithOutKeyList.get(i);
                        String fieldName = fieldInfo.getFieldName();
                        out.write("\t\t\t\t\t" + fieldName + ": " + fieldName);
                        if(i < (fieldInfoWithOutKeyList.size() - 1)){
                            out.write(",");
                        }
                        out.write("\r\n");
                    }

                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\tdataType: \"json\",\r\n");
                    out.write("\t\t\t\tsuccess: function(data){\r\n");
                    out.write("\t\t\t\t\tvar result = data[\"result\"];\r\n");
                    out.write("\t\t\t\t\tvar message = data[\"message\"];\r\n");
                    out.write("\t\t\t\t\t// 父页面提示\r\n");
                    out.write("\t\t\t\t\twindow.parent.layer.msg(message);\r\n");
                    out.write("\t\t\t\t\tif(result == \"success\"){\r\n");
                    out.write("\t\t\t\t\t\t// 刷新表格\r\n");
                    out.write("\t\t\t\t\t\tif(tableId){\r\n");
                    out.write("\t\t\t\t\t\t\twindow.parent.$(\"#\" + tableId).datagrid(\"reload\");\r\n");
                    out.write("\t\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t\t\t// 延时关闭loading\r\n");
                    out.write("\t\t\t\t\t\twindow.parent.commonLayerClose(loadIndex, 100);\r\n");
                    out.write("\t\t\t\t\t\t// 关闭layer\r\n");
                    out.write("\t\t\t\t\t\tif(layerIndex){\r\n");
                    out.write("\t\t\t\t\t\t\twindow.parent.layer.close(layerIndex);\r\n");
                    out.write("\t\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t\t}else{\r\n");
                    out.write("\t\t\t\t\t\t// 关闭loading\r\n");
                    out.write("\t\t\t\t\t\twindow.parent.commonLayerClose(loadIndex);\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\terror: function(){\r\n");
                    out.write("\t\t\t\t\t// 关闭loading\r\n");
                    out.write("\t\t\t\t\twindow.parent.commonLayerClose(loadIndex);\r\n");
                    out.write("\t\t\t\t\tlayer.msg(\"操作失败！\");\r\n");
                    out.write("\t\t\t\t}\r\n");
                    out.write("\t\t\t});\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\r\n");
                    out.write("\t\t$(function(){\r\n");

                    // 有文件上传
                    if(inputSetting.isFileUpload()){
                        out.write("\t\t\t// 实例化附件上传（不预先生成id，先用自动生成的关联id，保存后再更新成真实的实体id，所以这里的mainId需要传递到后台更新，当然也可以用预先生成id的方式）\r\n");
                        out.write("\t\t\tmainId = initCommonFileUpload($(\"#file_upload\"), {\r\n");
                        out.write("\t\t\t\tmainId: $(\"#id\").val(),\r\n");
                        out.write("\t\t\t\tparentLayer: true");
                        // 附件条件
                        CommonFileUploadConfig fileUploadConfig = inputSetting.getFileUploadConfig();
                        if(fileUploadConfig.getMinFileCount() > 0){
                            // 最小文件数量
                            out.write(",\r\n");
                            out.write("\t\t\t\tminFileCount:" + fileUploadConfig.getMinFileCount());
                        }
                        if(fileUploadConfig.getMinFileCount() > 0){
                            // 最大文件数量
                            out.write(",\r\n");
                            out.write("\t\t\t\tmaxFileCount:" + fileUploadConfig.getMaxFileCount());
                        }
                        if(StringUtils.isNotBlank(fileUploadConfig.getUploadPath())){
                            // 最大文件数量
                            out.write(",\r\n");
                            out.write("\t\t\t\tuploadPath:\"" + fileUploadConfig.getUploadPath() + "\"");
                        }
                        if(StringUtils.isNotBlank(fileUploadConfig.getExts())){
                            // 最大文件数量
                            out.write(",\r\n");
                            out.write("\t\t\t\texts:\"" + fileUploadConfig.getExts() + "\"");
                        }
                        out.write("\r\n");
                        out.write("\t\t\t});\r\n");
                    }

                    out.write("\t\t});\r\n");
                    out.write("\t</script>\r\n");
                    out.write("</head>\r\n");
                    out.write("<body>\r\n");
                    out.write("<input type=\"hidden\" id=\"" + keyFieldInfo.getColumnName() + "\" value=\"${" + modelVariableName + ".id}\">\r\n");
                    out.write("<table width=\"95%\" class=\"common_model_table\" style=\"height:auto; margin: 0 auto;\">\r\n");

                    // 遍历字段，两个放一行
                    for (int i = 0; i < fieldInfoWithOutKeyList.size(); i+=2) {
                        CommonFieldInfo fieldInfoLeft = fieldInfoWithOutKeyList.get(i);
                        CommonFieldInfo fieldInfoRight = null;
                        if(i < (fieldInfoWithOutKeyList.size() - 1)){
                            fieldInfoRight = fieldInfoWithOutKeyList.get(i + 1);
                        }
                        out.write("\t<tr>\r\n");
                        out.write("\t\t<td width=\"18%\" style=\"text-align: right;\" nowrap=\"nowrap\">" + fieldInfoLeft.getAnnotateName() + "：</td>\r\n");
                        out.write("\t\t<td width=\"32%\">\r\n");
                        out.write("\t\t\t<input id=\"" + fieldInfoLeft.getColumnName() + "\" name=\"" + fieldInfoLeft.getColumnName() + "\" type=\"text\" value=\"${" + modelVariableName + "." + fieldInfoLeft.getFieldName() + "}\" max_length=\"<model:length value='" + inputSetting.getModelPack() + "[" + fieldInfoLeft.getFieldName() + "]'/>\" />\r\n");
                        out.write("\t\t</td>\r\n");
                        if(fieldInfoRight == null){
                            out.write("\t\t<td width=\"18%\" style=\"text-align: right;\" nowrap=\"nowrap\"></td>\r\n");
                            out.write("\t\t<td width=\"32%\"></td>\r\n");
                        }else{
                            out.write("\t\t<td width=\"18%\" style=\"text-align: right;\" nowrap=\"nowrap\">" + fieldInfoRight.getAnnotateName() + "：</td>\r\n");
                            out.write("\t\t<td width=\"32%\">\r\n");
                            out.write("\t\t\t<input id=\"" + fieldInfoRight.getColumnName() + "\" name=\"" + fieldInfoRight.getColumnName() + "\" type=\"text\" value=\"${" + modelVariableName + "." + fieldInfoRight.getFieldName() + "}\" max_length=\"<model:length value='" + inputSetting.getModelPack() + "[" + fieldInfoRight.getFieldName() + "]'/>\" />\r\n");
                            out.write("\t\t</td>\r\n");
                        }
                        out.write("\t</tr>\r\n");
                    }

                    // 有附件上传
                    if(inputSetting.isFileUpload()){
                        out.write("\t<tr>\r\n");
                        out.write("\t\t<td style=\"text-align: right;\" nowrap=\"nowrap\">附件：</td>\r\n");
                        out.write("\t\t<td colspan=\"3\" >\r\n");
                        out.write("\t\t\t<input id=\"file_upload\" type=\"text\">\r\n");
                        out.write("\t\t</td>\r\n");
                        out.write("\t</tr>\r\n");
                    }

                    out.write("</table>\r\n");
                    out.write("</body>\r\n");
                }else if(CGConstants.ADD_OR_EDIT_TYPE_URL == addOrEditType){
                    // 跳转
                    out.write("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\r\n");
                    out.write("<%@ include file=\"/WEB-INF/jsp/common/easyUILibraries.jsp\"%>\r\n");
                    out.write("<!DOCTYPE html>\r\n");
                    out.write("<head >\r\n");
                    out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n");
                    out.write("\t<script>\r\n");

                    // 有文件上传
                    if(inputSetting.isFileUpload()){
                        out.write("\t\t// 文件上传id\r\n");
                        out.write("\t\tvar mainId;\r\n");
                    }

                    out.write("\r\n");
                    out.write("\t\t/**\r\n");
                    out.write("\t\t * 保存方法\r\n");
                    out.write("\t\t * @param tableId 父页面表格id\r\n");
                    out.write("\t\t * @param layerIndex 父页面layer窗口index\r\n");
                    out.write("\t\t */\r\n");
                    out.write("\t\tfunction save(tableId, layerIndex){\r\n");

                    // 主键取值
                    out.write("\t\t\tvar " + keyFieldInfo.getFieldName() + " = $(\"#" + keyFieldInfo.getColumnName() + "\").val();\r\n");
                    // 非主键取值
                    for (int i = 0; i < fieldInfoWithOutKeyList.size(); i++) {
                        CommonFieldInfo fieldInfo = fieldInfoWithOutKeyList.get(i);
                        String fieldName = fieldInfo.getFieldName();
                        String columnName = fieldInfo.getColumnName();
                        out.write("\t\t\tvar " + fieldName + " = $(\"#" + columnName + "\").val();\r\n");
                    }

                    out.write("\r\n");
                    out.write("\t\t\t// loading层\r\n");
                    out.write("\t\t\tvar loadIndex = commonLayerLoading();\r\n");
                    out.write("\t\t\t$.ajax({\r\n");
                    out.write("\t\t\t\turl: \"/" + inputSetting.getPackageRelativePathSuffix(true) + "/" + modelVariableName + "/save\",\r\n");
                    out.write("\t\t\t\ttype: \"post\",\r\n");
                    out.write("\t\t\t\tdata:{\r\n");

                    // 主键取值
                    out.write("\t\t\t\t\t" + keyFieldInfo.getFieldName() + ": " + keyFieldInfo.getFieldName() + ",\r\n");
                    // 有文件上传
                    if(inputSetting.isFileUpload()){
                        out.write("\t\t\t\t\tmainId: mainId,\r\n");
                    }
                    // 非主键取值
                    for (int i = 0; i < fieldInfoWithOutKeyList.size(); i++) {
                        CommonFieldInfo fieldInfo = fieldInfoWithOutKeyList.get(i);
                        String fieldName = fieldInfo.getFieldName();
                        out.write("\t\t\t\t\t" + fieldName + ": " + fieldName);
                        if(i < (fieldInfoWithOutKeyList.size() - 1)){
                            out.write(",");
                        }
                        out.write("\r\n");
                    }

                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\tdataType: \"json\",\r\n");
                    out.write("\t\t\t\tsuccess: function(data){\r\n");
                    out.write("\t\t\t\t\tvar result = data[\"result\"];\r\n");
                    out.write("\t\t\t\t\tvar message = data[\"message\"];\r\n");
                    out.write("\t\t\t\t\t// 提示\r\n");
                    out.write("\t\t\t\t\tlayer.msg(message);\r\n");
                    out.write("\t\t\t\t\tif(result == \"success\"){\r\n");
                    out.write("\t\t\t\t\t\t// 一秒后返回\r\n");
                    out.write("\t\t\t\t\t\tsetTimeout(back, 1000);\r\n");
                    out.write("\t\t\t\t\t\t// 延时关闭loading\r\n");
                    out.write("\t\t\t\t\t\tcommonLayerClose(loadIndex, 1000);\r\n");
                    out.write("\t\t\t\t\t}else{\r\n");
                    out.write("\t\t\t\t\t\t// 关闭loading\r\n");
                    out.write("\t\t\t\t\t\tcommonLayerClose(loadIndex);\r\n");
                    out.write("\t\t\t\t\t}\r\n");
                    out.write("\t\t\t\t},\r\n");
                    out.write("\t\t\t\terror: function(){\r\n");
                    out.write("\t\t\t\t\t// 关闭loading\r\n");
                    out.write("\t\t\t\t\tcommonLayerClose(loadIndex);\r\n");
                    out.write("\t\t\t\t\tlayer.msg(\"操作失败！\");\r\n");
                    out.write("\t\t\t\t}\r\n");
                    out.write("\t\t\t});\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\r\n");
                    out.write("\t\tfunction back(){\r\n");
                    out.write("\t\t\twindow.location.href = \"/" + inputSetting.getPackageRelativePathPrefix(true) + "/" + modelVariableName + "/list\";\r\n");
                    out.write("\t\t}\r\n");
                    out.write("\r\n");
                    out.write("\t\t$(function(){\r\n");

                    // 有文件上传
                    if(inputSetting.isFileUpload()) {
                        out.write("\t\t\t// 实例化附件上传（不预先生成id，先用自动生成的关联id，保存后再更新成真实的实体id，所以这里的mainId需要传递到后台更新，当然也可以用预先生成id的方式）\r\n");
                        out.write("\t\t\tmainId = initCommonFileUpload($(\"#file_upload\"), {\r\n");
                        out.write("\t\t\t\tmainId: $(\"#id\").val(),\r\n");
                        out.write("\t\t\t\tparentLayer: true,\r\n");
                        out.write("\t\t\t\tmaxFileCount: 3\r\n");
                        out.write("\t\t\t});\r\n");
                    }

                    out.write("\t\t});\r\n");
                    out.write("\t</script>\r\n");
                    out.write("</head>\r\n");
                    out.write("<body>\r\n");
                    out.write("<input type=\"hidden\" id=\"id\" name=\"id\" value=\"${" + modelVariableName + ".id}\">\r\n");
                    out.write("<%-- 按钮栏开始 --%>\r\n");
                    out.write("<div class=\"common_model_munu_bar\">\r\n");
                    out.write("\t<input type=\"button\" value=\"保存\" onclick=\"save()\">\r\n");
                    out.write("\t<input type=\"button\" value=\"返回\" onclick=\"back()\">\r\n");
                    out.write("</div>\r\n");
                    out.write("<%-- 按钮栏结束 --%>\r\n");
                    out.write("<%-- 内容开始 --%>\r\n");
                    out.write("<div class=\"common_model_content\">\r\n");
                    out.write("\t<div class=\"common_model_a4\">\r\n");
                    out.write("\t\t<p style=\"text-align: center; line-height: 50px; font-size: 26px; margin-top: 16px;\">模板</p>\r\n");
                    out.write("\t\t<table width=\"95%\" class=\"common_model_table\" style=\"height:auto; margin: 0 auto;\">\r\n");

                    // 遍历字段，两个放一行
                    for (int i = 0; i < fieldInfoWithOutKeyList.size(); i+=2) {
                        CommonFieldInfo fieldInfoLeft = fieldInfoWithOutKeyList.get(i);
                        CommonFieldInfo fieldInfoRight = null;
                        if(i < (fieldInfoWithOutKeyList.size() - 1)){
                            fieldInfoRight = fieldInfoWithOutKeyList.get(i + 1);
                        }
                        out.write("\t\t\t<tr>\r\n");
                        out.write("\t\t\t\t<td width=\"18%\" style=\"text-align: right;\" nowrap=\"nowrap\">" + fieldInfoLeft.getAnnotateName() + "：</td>\r\n");
                        out.write("\t\t\t\t<td width=\"32%\">\r\n");
                        out.write("\t\t\t\t\t<input id=\"" + fieldInfoLeft.getColumnName() + "\" name=\"" + fieldInfoLeft.getColumnName() + "\" type=\"text\" value=\"${" + modelVariableName + "." + fieldInfoLeft.getFieldName() + "}\" max_length=\"<model:length value='" + inputSetting.getModelPack() + "[" + fieldInfoLeft.getFieldName() + "]'/>\" />\r\n");
                        out.write("\t\t\t\t</td>\r\n");
                        if(fieldInfoRight == null){
                            out.write("\t\t\t\t<td width=\"18%\" style=\"text-align: right;\" nowrap=\"nowrap\"></td>\r\n");
                            out.write("\t\t\t\t<td width=\"32%\"></td>\r\n");
                        }else{
                            out.write("\t\t\t\t<td width=\"18%\" style=\"text-align: right;\" nowrap=\"nowrap\">" + fieldInfoRight.getAnnotateName() + "：</td>\r\n");
                            out.write("\t\t\t\t<td width=\"32%\">\r\n");
                            out.write("\t\t\t\t\t<input id=\"" + fieldInfoRight.getColumnName() + "\" name=\"" + fieldInfoRight.getColumnName() + "\" type=\"text\" value=\"${" + modelVariableName + "." + fieldInfoRight.getFieldName() + "}\" max_length=\"<model:length value='" + inputSetting.getModelPack() + "[" + fieldInfoRight.getFieldName() + "]'/>\" />\r\n");
                            out.write("\t\t\t\t</td>\r\n");
                        }
                        out.write("\t\t\t</tr>\r\n");
                    }

                    // 有附件上传
                    if(inputSetting.isFileUpload()){
                        out.write("\t<tr>\r\n");
                        out.write("\t\t<td style=\"text-align: right;\" nowrap=\"nowrap\">附件：</td>\r\n");
                        out.write("\t\t<td colspan=\"3\" >\r\n");
                        out.write("\t\t\t<input id=\"file_upload\" type=\"text\">\r\n");
                        out.write("\t\t</td>\r\n");
                        out.write("\t</tr>\r\n");
                    }

                    out.write("\t\t</table>\r\n");
                    out.write("\t</div>\r\n");
                    out.write("</div>\r\n");
                    out.write("<%-- 内容结束 --%>\r\n");
                    out.write("</body>\r\n");
                }

                out.flush();
                out.close();
                System.out.print("生成成功！\n");
            } catch (IOException e) {
                System.out.print("生成失败！\n");
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得生成文件日期
     * @return date
     */
    private static String getFileDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return format.format(new Date());
    }
}

package codeGenerator;


import com.fc.annotation.ModelName;
import com.fc.common.domain.CommonClassInfo;
import com.fc.common.domain.CommonFieldInfo;
import com.fc.common.domain.CommonFileUploadConfig;
import com.fc.service.common.Impl.CommonServiceImpl;
import com.fc.util.ArrayUtil;
import com.fc.util.GlobalFunc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Description 代码自动生成器
 * @author luoy
 * @version 1.0.0
 * @Date 2020年3月9日09:09:09
 **/
public class CGMain {

    // 引用commonService，不可操作数据库
    private static CommonServiceImpl commonService;

    // 作者
    private static final String AUTHOR = "Strideyouyou";
    // 版本
    private static final String VERSION = "1.0.0";
    // 退出符号
    private static final String EXIT_CODE = "#";
    // 打印文字顺序
    private static int printIndex = 1;
    // 此处改为需要扫描的java文件的路径
    private static final String PACKAGE_PATH = "F:\\\\ideaProjects\\\\ciswitip\\src\\main\\java";

    static {
        commonService = new CommonServiceImpl();
    }

    public static void main(String[] args) {
        String out = "\n";
        out += " ██████╗  ██████╗  ██████╗  ███████╗  ██████╗  ███████╗ ███╗   ██╗ ███████╗ ██████╗   █████╗  ████████╗  ██████╗  ██████╗ \n";
        out += "██╔════╝ ██╔═══██╗ ██╔══██╗ ██╔════╝ ██╔════╝  ██╔════╝ ████╗  ██║ ██╔════╝ ██╔══██╗ ██╔══██╗ ╚══██╔══╝ ██╔═══██╗ ██╔══██╗\n";
        out += "██║      ██║   ██║ ██║  ██║ █████╗   ██║  ███╗ █████╗   ██╔██╗ ██║ █████╗   ██████╔╝ ███████║    ██║    ██║   ██║ ██████╔╝\n";
        out += "██║      ██║   ██║ ██║  ██║ ██╔══╝   ██║   ██║ ██╔══╝   ██║╚██╗██║ ██╔══╝   ██╔══██╗ ██╔══██║    ██║    ██║   ██║ ██╔══██╗\n";
        out += "╚██████╗ ╚██████╔╝ ██████╔╝ ███████╗ ╚██████╔╝ ███████╗ ██║ ╚████║ ███████╗ ██║  ██║ ██║  ██║    ██║    ╚██████╔╝ ██║  ██║\n";
        out += " ╚═════╝  ╚═════╝  ╚═════╝  ╚══════╝  ╚═════╝  ╚══════╝ ╚═╝  ╚═══╝ ╚══════╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝    ╚═╝     ╚═════╝  ╚═╝  ╚═╝";
        System.out.println(out);
        System.out.print("【增删改查代码生成器】" + "\n");
        System.out.print("作者：" + CGMain.AUTHOR + "\n");
        System.out.print("版本:" + CGMain.VERSION + "\n");
        System.out.print("java包路径：" + CGMain.PACKAGE_PATH + "\n");
        System.out.print("================================================【开始运行】（输入" + CGMain.EXIT_CODE + "回车退出）================================================\n");

        CGInputSetting inputSetting = null;
        // 输入
        Scanner scanner = new Scanner(System.in);

        // 用户输入参数
        CGMain.inputModelPath(inputSetting, scanner);

        if(inputSetting == null){
            CGMain.print("已退出！");
        }
    }

    /**
     * 打印文字，不换行
     */
    public static void print(String content){
        System.out.print("#" + CGMain.printIndex + " " + content);
        CGMain.printIndex++;
    }

    /**
     * 打印信息
     * @param inputSetting 用户输入参数
     */
    private static void printSetting(CGInputSetting inputSetting){
        System.out.print("==================================================【解析信息】==================================================\n");
        CGMain.print("实体路径：" + inputSetting.getModelPack() + "\n");
        CGMain.print("实体名称：" + inputSetting.getChineseModelName() + "\n");
        CGMain.print("实体作者：" + inputSetting.getAuthor() + "\n");
        CGMain.print("实体版本：" + inputSetting.getVersion() + "\n");
        CGMain.print("是否覆盖：" + (inputSetting.isCoveredCode() ? "是" : "否") + "\n");
        CGMain.print("实体主键：" + (inputSetting.getClassInfo().getKeyFieldInfo() == null ? "无" : "有") + "\n");
        CGMain.print("字段个数：" + inputSetting.getClassInfo().getCommonFieldInfoList().size() + "个\n");
        CGMain.print("查询字段：" + inputSetting.getSearchFieldList().size() + "个\n");
        System.out.print("==================================================【解析信息】==================================================\n");
    }

    /**
     * 输入实体路径
     * @param inputSetting 用户输入参数
     * @param scanner 输入
     */
    private static void inputModelPath(CGInputSetting inputSetting, Scanner scanner){
        CGMain.print("输入实体类路径(必须在model下，格式如：com.ncxdkj.model.template.BookTemplate)\n");
        CGMain.print("请输入：");
        // 类信息
        Class<?> classObj = null;
        do{
            // 等待输入值
            String val = scanner.next();
            if(CGMain.EXIT_CODE.equalsIgnoreCase(val)){
                // 直接结束
                return;
            }
            // 通过路径找到文件
            try {
                classObj = Thread.currentThread().getContextClassLoader().loadClass(val);
            } catch (ClassNotFoundException e) {
                CGMain.print("未找到文件，请重新输入：");
            }
            if(classObj != null){
                // 解析class
                CommonClassInfo classInfo = commonService.getCommonClassInfo(classObj);
                if(classInfo != null){
                    if(classInfo.getKeyFieldInfo() == null){
                        // 主键为空
                        CGMain.print("不可使用没有主键的实体类，请重新输入：");
                    }else{
                        // 可用，装入
                        inputSetting = new CGInputSetting();
                        inputSetting.setModelPack(val);
                        inputSetting.setClassInfo(classInfo);
                    }
                }else{
                    CGMain.print("文件有误！\n");
                }
            }
        }while(inputSetting == null || inputSetting.getClassInfo().getKeyFieldInfo() == null);

        // 解析文件
        // 读取实体类中文名、作者、版本
        String chineseModelName = "";
        String author = "";
        String version = CGConstants.DEFAULT_VERSION;
        try{
            ModelName[] modelNameAnnotations = classObj.getAnnotationsByType(ModelName.class);
            if(modelNameAnnotations != null && modelNameAnnotations.length > 0){
                chineseModelName = GlobalFunc.toString(modelNameAnnotations[0].name());
                if(chineseModelName.endsWith("实体")){
                    chineseModelName = chineseModelName.substring(0, chineseModelName.length() - 2);
                }
                if(chineseModelName.endsWith("表")
                        && !chineseModelName.endsWith("主表")
                        && !chineseModelName.endsWith("子表")
                        && !chineseModelName.endsWith("从表")){
                    chineseModelName = chineseModelName.substring(0, chineseModelName.length() - 1);
                }
            }
            // 文件绝对路径
            String classAbsoluteFilePath = CGMain.PACKAGE_PATH + File.separator + inputSetting.getModelPath() + ".java";
            File classFile = new File(classAbsoluteFilePath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(classFile));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                // 一次读入一行数据
                line = br.readLine();
                if(line.toLowerCase().contains("@author")){
                    // 作者
                    author = line.substring(line.toLowerCase().indexOf("@author") + 7).trim();
                }
                if(line.toLowerCase().contains("@version")){
                    // 版本
                    version = line.substring(line.toLowerCase().indexOf("@version") + 8).trim();
                }
            }
        }catch (Exception e){
            //e.printStackTrace();
        }
        inputSetting.setChineseModelName(chineseModelName);
        inputSetting.setAuthor(author);
        inputSetting.setVersion(version);

        // 打印信息
        CGMain.printSetting(inputSetting);

        // 进入一级菜单
        menuLevelOne(inputSetting, scanner);
    }

    /**
     * 选择一级菜单
     * @param inputSetting 用户输入参数
     * @param scanner 输入
     */
    private static void menuLevelOne(CGInputSetting inputSetting, Scanner scanner){
        System.out.print("==================================================【一级菜单】==================================================\n");
        CGMain.print("[0]：重新输入路径\n");
        CGMain.print("[1]：打印解析信息\n");
        CGMain.print("[2]：设置新增修改方式，当前为：" + inputSetting.getAddOrEditTypeName() + "\n");
        CGMain.print("---------------------------------------------------------------------------------------------\n");
        if(inputSetting.isCoveredCode()){
            CGMain.print("[3]：关闭代码覆盖（相同文件名则跳过）\n");
        }else{
            CGMain.print("[3]：开启代码覆盖（相同文件名则先删除）\n");
        }
        CGMain.print("[4]：设置查询字段\n");
        CGMain.print("[5]：设置附件上传\n");
        CGMain.print("---------------------------------------------------------------------------------------------\n");
        CGMain.print("[6]：生成文件\n");
        CGMain.print("[7]：生成文件并退出\n");
        CGMain.print("---------------------------------------------------------------------------------------------\n");
        CGMain.print("[" + CGMain.EXIT_CODE + "]：退出\n");
        System.out.print("==================================================【一级菜单】==================================================\n");
        CGMain.print("请输入序号：");
        // 等待输入值
        String val = scanner.next();
        // 处理
        if("0".equals(val)){
            // 重新输入路径
            inputSetting = null;
            CGMain.inputModelPath(inputSetting, scanner);
        }else if("1".equals(val)){
            // 打印解析信息
            CGMain.printSetting(inputSetting);
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }else if("2".equals(val)){
            // 设置新增修改方式
            CGMain.print("请给选择新增、修改方式\n");
            CGMain.print("[1]：窗口\n");
            CGMain.print("[2]：跳转\n");
            CGMain.print("请输入：");
            // 等待输入值
            val = scanner.next();
            if("null".equals(val)){
                val = "";
            }
            int type = GlobalFunc.parseInt(val);
            if(CGConstants.ADD_OR_EDIT_TYPE_WINDOW == type){
                inputSetting.setAddOrEditType(type);
            }else if(CGConstants.ADD_OR_EDIT_TYPE_URL == type){
                inputSetting.setAddOrEditType(type);
            }else{
                CGMain.print("输入内容有误！\n");
            }
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }else if("3".equals(val)){
            // 代码覆盖
            if(inputSetting.isCoveredCode()){
                inputSetting.setCoveredCode(false);
                CGMain.print("已关闭代码覆盖\n");
            }else{
                inputSetting.setCoveredCode(true);
                CGMain.print("已开启代码覆盖\n");
            }
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }else if("4".equals(val)){
            // 设置查询字段
            CGMain.setSelectField(inputSetting, scanner);
        }else if("5".equals(val)){
            // 设置附件上传
             CGMain.setFileUpload(inputSetting, scanner);
        }else if("6".equals(val)){
            // 生成文件
            CGEngine.generator(inputSetting, CGMain.PACKAGE_PATH);
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }else if("7".equals(val)){
            // 生成文件并退出
            CGEngine.generator(inputSetting, CGMain.PACKAGE_PATH);
            // 退出
            return;
        }else if(CGMain.EXIT_CODE.equals(val)){
            // 退出
            return;
        }else{
            CGMain.print("输入内容有误！\n");
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }
    }

    /**
     * 设置查询字段
     * @param inputSetting 用户输入参数
     * @param scanner 输入
     */
    private static void setSelectField(CGInputSetting inputSetting, Scanner scanner){
        // 全部字段
        List<String> allFieldList = new ArrayList<>();
        // 已选字段
        List<String> searchFieldList = new ArrayList<>();

        CommonFieldInfo keyFieldInfo = inputSetting.getClassInfo().getKeyFieldInfo();
        List<CommonFieldInfo> allFieldInfoList = inputSetting.getClassInfo().getCommonFieldInfoList();
        List<CGSearchFiled> searchFieldInfoList = inputSetting.getSearchFieldList();
        for (CommonFieldInfo fieldInfo : allFieldInfoList) {
            if(fieldInfo.getFieldName().equals(keyFieldInfo.getFieldName())){
                continue;
            }
            allFieldList.add(fieldInfo.getFieldName());
        }
        for (CGSearchFiled cgSearchFiled : searchFieldInfoList) {
            searchFieldList.add(cgSearchFiled.getFieldInfo().getFieldName());
        }

        System.out.print("==================================================【查询字段】==================================================\n");
        CGMain.print("全部字段（排除主键）：" + (allFieldList.size() == 0 ? "无" : ArrayUtil.toString(allFieldList, "、")) + "\n");
        CGMain.print("已选字段：" +  (searchFieldList.size() == 0 ? "无" : ArrayUtil.toString(searchFieldList, "、")) + "\n");
        CGMain.print("[0]：返回上一级\n");
        CGMain.print("[1]：添加查询字段\n");
        CGMain.print("[2]：删除查询字段\n");
        System.out.print("==================================================【查询字段】==================================================\n");
        CGMain.print("请输入序号：");
        // 等待输入值
        String val = scanner.next();
        // 处理
        if("0".equals(val)){
            // 返回上一级
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }else if("1".equals(val)){
            // 添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }else if("2".equals(val)){
            // 删除查询字段
            CGMain.deleteSearchField(inputSetting, scanner);
        }else{
            CGMain.print("输入内容有误！\n");
            // 进入设置查询字段
            CGMain.setSelectField(inputSetting, scanner);
        }
    }

    /**
     * 添加查询字段
     * @param inputSetting 用户输入参数
     * @param scanner 输入
     */
    private static void addSearchField(CGInputSetting inputSetting, Scanner scanner){
        // 已选字段
        List<String> searchFieldList = new ArrayList<>();
        // 可选字段
        List<String> fieldList = new ArrayList<>();

        CommonFieldInfo keyFieldInfo = inputSetting.getClassInfo().getKeyFieldInfo();
        List<CommonFieldInfo> allFieldInfoList = inputSetting.getClassInfo().getCommonFieldInfoList();
        List<CGSearchFiled> searchFieldInfoList = inputSetting.getSearchFieldList();
        for (CGSearchFiled cgSearchFiled : searchFieldInfoList) {
            searchFieldList.add(cgSearchFiled.getFieldInfo().getFieldName());
        }
        for (CommonFieldInfo fieldInfo : allFieldInfoList) {
            if(fieldInfo.getFieldName().equals(keyFieldInfo.getFieldName()) || searchFieldList.contains(fieldInfo.getFieldName())){
                // 排除主键、已选字段
                continue;
            }
            fieldList.add(fieldInfo.getFieldName());
        }

        System.out.print("================================================【添加查询字段】================================================\n");
        CGMain.print("[0]：返回上一级\n");
        if(fieldList.isEmpty()){
            CGMain.print("当前无可添加的字段\n");
        }
        for (int i = 0; i < fieldList.size(); i++) {
            CGMain.print("[" + (i + 1) + "]：选择" + fieldList.get(i) + "\n");
        }
        System.out.print("================================================【添加查询字段】================================================\n");
        CGMain.print("请输入序号：");
        // 等待输入值
        String val = scanner.next();
        int index = GlobalFunc.parseInt(val);
        // 处理
        if("0".equals(val)){
            // 返回上一级
            // 进入设置查询字段
            CGMain.setSelectField(inputSetting, scanner);
        }else if(index >= 1 && index <= fieldList.size()){
            // 添加查询字段
            CommonFieldInfo fieldInfo = null;
            for (CommonFieldInfo field : allFieldInfoList) {
                if(field.getFieldName().equals(fieldList.get(index - 1))){
                    fieldInfo = field;
                    break;
                }
            }
            // 进入添加查询字段 - 设置参数
            CGMain.addSearchFieldType(inputSetting, scanner, fieldInfo);
        }else{
            CGMain.print("输入内容有误！\n");
            // 进入添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }
    }

    /**
     * 添加查询字段 - 设置参数
     * @param inputSetting 用户输入参数
     * @param scanner 输入
     */
    private static void addSearchFieldType(CGInputSetting inputSetting, Scanner scanner, CommonFieldInfo fieldInfo){
        System.out.print("==============================================【设置查询字段类型】==============================================\n");
        CGMain.print("字段名：" + fieldInfo.getFieldName() + "\n");
        CGMain.print("[0]：返回上一级\n");
        CGMain.print("[" + CGConstants.SEARCH_TYPE_NORMAL + "]：普通字符查询\n");
        CGMain.print("[" + CGConstants.SEARCH_TYPE_NUMBER_AREA + "]：数字范围查询\n");
        CGMain.print("[" + CGConstants.SEARCH_TYPE_NUMBER_SMART + "]：数字智能查询\n");
        CGMain.print("[" + CGConstants.SEARCH_TYPE_INT_AREA + "]：整数范围查询\n");
        CGMain.print("[" + CGConstants.SEARCH_TYPE_DATE_AREA + "]：日期范围查询\n");
        CGMain.print("[" + CGConstants.SEARCH_TYPE_SELECT_SINGLE + "]：固定下拉框单选查询\n");
        CGMain.print("[" + CGConstants.SEARCH_TYPE_SELECT_MULTIPLE + "]：固定下拉框多选查询\n");
        System.out.print("==============================================【设置查询字段类型】==============================================\n");
        CGMain.print("请输入序号：");
        // 等待输入值
        String val = scanner.next();
        int type = GlobalFunc.parseInt(val);
        // 处理
        if("0".equals(val)){
            // 返回上一级
            // 进入添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }else if(type == CGConstants.SEARCH_TYPE_NORMAL){
            // 普通字符查询
            CGSearchFiled searchFiled = new CGSearchFiled();
            searchFiled.setFieldInfo(fieldInfo);
            searchFiled.setSearchType(type);
            inputSetting.getSearchFieldList().add(searchFiled);
            // 进入添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }else if(type == CGConstants.SEARCH_TYPE_NUMBER_AREA || type == CGConstants.SEARCH_TYPE_INT_AREA){
            // 数字范围查询、整数范围查询，需要再次输入范围，可为空
            CGSearchFiled searchFiled = new CGSearchFiled();
            searchFiled.setFieldInfo(fieldInfo);
            searchFiled.setSearchType(type);
            CGMain.print("请给【" + fieldInfo.getFieldName() + "】设置最小值（非数字代表不设置）：");
            // 等待输入值
            val = scanner.next();
            if(type == CGConstants.SEARCH_TYPE_NUMBER_AREA && !GlobalFunc.isNumber(val)){
                val = "";
            }
            if(type == CGConstants.SEARCH_TYPE_INT_AREA && !GlobalFunc.isNumeric(val)){
                val = "";
            }
            searchFiled.setAreaStart(val);
            CGMain.print("请给【" + fieldInfo.getFieldName() + "】设置最大值（非数字代表不设置）：");
            // 等待输入值
            val = scanner.next();
            if(type == CGConstants.SEARCH_TYPE_NUMBER_AREA && !GlobalFunc.isNumber(val)){
                val = "";
            }
            if(type == CGConstants.SEARCH_TYPE_INT_AREA && !GlobalFunc.isNumeric(val)){
                val = "";
            }
            searchFiled.setAreaEnd(val);
            inputSetting.getSearchFieldList().add(searchFiled);
            // 进入添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }else if(type == CGConstants.SEARCH_TYPE_NUMBER_SMART){
            // 数字智能查询
            CGSearchFiled searchFiled = new CGSearchFiled();
            searchFiled.setFieldInfo(fieldInfo);
            searchFiled.setSearchType(type);
            inputSetting.getSearchFieldList().add(searchFiled);
            // 进入添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }else if(type == CGConstants.SEARCH_TYPE_DATE_AREA){
            // 日期范围查询
            CGSearchFiled searchFiled = new CGSearchFiled();
            searchFiled.setFieldInfo(fieldInfo);
            searchFiled.setSearchType(type);
            inputSetting.getSearchFieldList().add(searchFiled);
            // 进入添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }else if(type == CGConstants.SEARCH_TYPE_SELECT_SINGLE || type == CGConstants.SEARCH_TYPE_SELECT_MULTIPLE){
            // 固定下拉框单选查询、固定下拉框多选查询
            CGSearchFiled searchFiled = new CGSearchFiled();
            searchFiled.setFieldInfo(fieldInfo);
            searchFiled.setSearchType(type);
            CGMain.print("请给【" + fieldInfo.getFieldName() + "】设置下拉参数（可为空，null代表空，多个用\";\"分隔）：");
            // 等待输入值
            val = scanner.next();
            if("null".equals(val)){
                val = "";
            }
            searchFiled.setSelectList(ArrayUtil.split(val, ";"));
            inputSetting.getSearchFieldList().add(searchFiled);
            // 进入添加查询字段
            CGMain.addSearchField(inputSetting, scanner);
        }else{
            CGMain.print("输入内容有误！\n");
            // 进入设置查询字段参数
            CGMain.addSearchFieldType(inputSetting, scanner, fieldInfo);
        }
    }

    /**
     * 删除查询字段
     * @param inputSetting 用户输入参数
     * @param scanner 输入
     */
    private static void deleteSearchField(CGInputSetting inputSetting, Scanner scanner){
        // 已选字段
        List<String> fieldList = new ArrayList<>();

        List<CGSearchFiled> searchFieldInfoList = inputSetting.getSearchFieldList();
        for (CGSearchFiled cgSearchFiled : searchFieldInfoList) {
            fieldList.add(cgSearchFiled.getFieldInfo().getFieldName());
        }

        System.out.print("================================================【删除查询字段】================================================\n");
        CGMain.print("[0]：返回上一级\n");
        if(fieldList.isEmpty()){
            CGMain.print("当前无可删除的字段\n");
        }
        for (int i = 0; i < fieldList.size(); i++) {
            CGMain.print("[" + (i + 1) + "]：删除" + fieldList.get(i) + "\n");
        }
        System.out.print("================================================【删除查询字段】================================================\n");
        CGMain.print("请输入序号：");
        // 等待输入值
        String val = scanner.next();
        int index = GlobalFunc.parseInt(val);
        // 处理
        if("0".equals(val)){
            // 返回上一级
            // 进入设置查询字段
            CGMain.setSelectField(inputSetting, scanner);
        }else if(index >= 1 && index <= fieldList.size()){
            // 删除查询字段
            CGSearchFiled searchFiled = searchFieldInfoList.get(index - 1);
            // 找到并删除
            List<CGSearchFiled> searchFieldList = inputSetting.getSearchFieldList();
            for (int i = 0; i < searchFieldList.size(); i++) {
                CGSearchFiled cgSearchFiled = searchFieldList.get(i);
                if(searchFiled.getFieldInfo().getFieldName().equals(cgSearchFiled.getFieldInfo().getFieldName())){
                    // 移除
                    searchFieldList.remove(i);
                    break;
                }
            }
            // 进入删除查询字段
            CGMain.deleteSearchField(inputSetting, scanner);
        }else{
            CGMain.print("输入内容有误！\n");
            // 进入添加查询字段
            CGMain.deleteSearchField(inputSetting, scanner);
        }
    }

    /**
     * 设置附件上传
     * @param inputSetting 用户输入参数
     * @param scanner 输入
     */
    private static void setFileUpload(CGInputSetting inputSetting, Scanner scanner){
        CommonFileUploadConfig fileUploadConfig = inputSetting.getFileUploadConfig();
        System.out.print("================================================【附件上传设置】================================================\n");
        CGMain.print("序[0]：返回上一级\n");
        if(inputSetting.isFileUpload()){
            CGMain.print("[1]：关闭文件上传\n");
        }else{
            CGMain.print("[1]：开启文件上传\n");
        }
        CGMain.print("[2]：设置最小文件数量（" + fileUploadConfig.getMinFileCount() + "）\n");
        CGMain.print("[3]：最大文件数量（" + fileUploadConfig.getMaxFileCount() + "）\n");
        CGMain.print("[4]：上传的文件夹（" + fileUploadConfig.getUploadPath() + "）\n");
        CGMain.print("[5]：文件的扩展名，多个用\",\"分隔（" + fileUploadConfig.getExts() + "）\n");
        CGMain.print("更多参数可以生成文件后修改\n");
        System.out.print("================================================【附件上传设置】================================================\n");
        CGMain.print("请输入序号：");
        // 等待输入值
        String val = scanner.next();
        // 处理
        if("0".equals(val)){
            // 返回上一级
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }else if("1".equals(val)){
            // 开启、关闭文件上传
            if(inputSetting.isFileUpload()){
                inputSetting.setFileUpload(false);
                CGMain.print("已关闭文件上传\n");
            }else{
                inputSetting.setFileUpload(true);
                CGMain.print("已开启文件上传\n");
            }
            // 附件上传设置
            CGMain.setFileUpload(inputSetting, scanner);
        }else if("2".equals(val)){
            // 最小文件数量
            CGMain.print("请输入最小文件数量（可为空，null代表空）：");
            // 等待输入值
            val = scanner.next();
            if(!"null".equals(val)){
                fileUploadConfig.setMinFileCount(GlobalFunc.parseInt(val));
            }
            // 附件上传设置
            CGMain.setFileUpload(inputSetting, scanner);
        }else if("3".equals(val)){
            // 最大文件数量
            CGMain.print("请输入最大文件数量（可为空，null代表空）：");
            // 等待输入值
            val = scanner.next();
            if(!"null".equals(val)){
                fileUploadConfig.setMaxFileCount(GlobalFunc.parseInt(val));
            }
            // 附件上传设置
            CGMain.setFileUpload(inputSetting, scanner);
        }else if("4".equals(val)){
            // 上传的文件夹
            CGMain.print("请输入文件上传的文件夹（可为空，null代表空，如\"/lab\"）：");
            // 等待输入值
            val = scanner.next();
            if(!"null".equals(val)){
                fileUploadConfig.setUploadPath(val);
            }
            // 附件上传设置
            CGMain.setFileUpload(inputSetting, scanner);
        }else if("5".equals(val)){
            // 文件的扩展名
            CGMain.print("请输入文件上传的扩展名（可为空，null代表空，多个用逗号分隔，如\"jpg,png\"：");
            // 等待输入值
            val = scanner.next();
            if(!"null".equals(val)){
                fileUploadConfig.setExts(val);
            }
            // 附件上传设置
            CGMain.setFileUpload(inputSetting, scanner);
        }else{
            CGMain.print("输入内容有误！\n");
            // 进入一级菜单
            CGMain.menuLevelOne(inputSetting, scanner);
        }
    }
}

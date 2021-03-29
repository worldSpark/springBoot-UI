package codeCheck;


import com.fc.util.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description equals修复测试
 * 1、找到并修改 obj.equals("xxx") → "xxx".equals(obj)
 * 2、删除标记:^\/\/ todo equals err.*\n
 * @author luoy
 * @version 1.0.0
 * @Date 2019年01月17日 下午16:55:00
 **/
public class FixEqualsTest {

    // 此处改为需要扫描的java文件的路径
    private static final String PACKAGE_PATH = "D:\\workspace\\ciswitip\\src\\main\\java";
    // 匹配有问题equals的正则
    private static final String EQUALS_REG = "\\.equals\\(\\\"[\\u4e00-\\u9fa5a-zA-Z0-9/\\(\\)\\+-\\[\\]]*\\\"\\)";
    // 是否打印到控制台
    private static boolean WRITE_CONSOLE = true;
    // 是否修改并写入文件
    private static boolean WRITE_FILE = false;

    public static void main(String[] args) {

        try {
            Pattern pattern = Pattern.compile(EQUALS_REG);

            File dir = new File(PACKAGE_PATH);

            // 第一步 获取所有文件
            List<File> fileList = FileUtils.getAllSubFile(dir);
            int originalSize = fileList.size();

            // 第二步 遍历文件
            for (int i = 0;i < fileList.size();i++) {
                File file = fileList.get(i);
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                boolean hasErrEquals = false;
                while (line != null) {
                    // 一次读入一行数据
                    line = br.readLine();
                    if(FixEqualsTest.isBlank(line)
                            || line.trim().startsWith("// todo equals err")
                            || line.trim().startsWith("*")
                            ){
                        continue;
                    }
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()){
                        hasErrEquals = true;
                        break;
                    }
                }
                if(!hasErrEquals){
                    fileList.remove(i);
                    i--;
                }
            }
            int resultSize = fileList.size();

            // 第三步 开始替换
            int replaceCount = 0;
            for (int i = 0;i < fileList.size();i++) {
                File file = fileList.get(i);
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                String afterLine = line;
                List<String> lineList = new ArrayList<>();
                if(line != null){
                    lineList.add(line);
                }
                while (line != null) {
                    // 一次读入一行数据
                    line = br.readLine();
                    afterLine = line;
                    if(line != null){
                        lineList.add(line);
                    }
                    if(FixEqualsTest.isBlank(line)
                            || line.trim().startsWith("// todo equals err")
                            || line.trim().startsWith("*")
                            ){
                        continue;
                    }
                    Matcher matcher = pattern.matcher(line);
                    boolean hasChanged = false;
                    while (matcher.find()){
                        String group = matcher.group();
                        String after = group.substring(group.indexOf("(") + 2,group.lastIndexOf(")") - 1);
                        String before = "";
                        int groupIndex = line.indexOf(group);
                        int leftCount = 0;
                        int rightCount = 0;
                        for(int j = groupIndex;j > 0;j--){
                            String c = line.substring(j - 1,j);
                            if("(".equals(c)){
                                leftCount++;
                            }
                            if(")".equals(c)){
                                rightCount++;
                            }
                            if((leftCount > rightCount && "(".equals(c))
                                    || " ".equals(c)
                                    || "|".equals(c)
                                    || "&".equals(c)
                                    || "!".equals(c)
                                    || "/".equals(c)
                                    ){
                                break;
                            }
                            before = c + before;
                        }
                        afterLine = afterLine.replace(before + group, "\"" + after + "\".equals(" + before + ")");
                        replaceCount++;
                        hasChanged = true;
                    }
                    if(hasChanged){
                        // 替换行
                        lineList.remove(lineList.size() - 1);
                        lineList.add("// todo equals err " + line);
                        lineList.add(afterLine);
                        if(WRITE_CONSOLE){
                            System.out.println("");
                            System.out.println("错　　误：" + line.trim());
                            System.out.println("应替换为：" + afterLine.trim());
                            System.out.println("");
                        }
                    }
                }
                if(WRITE_FILE){
                    // 写出到文件
                    file.delete();
                    file.createNewFile();
                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
                    for(String str : lineList){
                        out.write(str + "\r\n");
                    }
                    out.flush();
                    out.close();
                }
            }
            System.out.println("扫描文件:" + originalSize + "个;"
                    + " 有问题的文件:" + resultSize + "个;"
                    + " 发现问题:" + replaceCount + "处");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isBlank(String str){
        return str == null || "".equals(str);
    }
}

package codeCheck;


import com.fc.util.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description service测试
 * 1、找到有额外注解的接口
 * @author luoy
 * @version 1.0.0
 * @Date 2019年01月21日 下午16:25:00
 **/
public class FixServiceTest {

    // 此处改为需要扫描的java文件的路径
    private static final String PACKAGE_PATH = "D:\\workspace\\ciswitip\\src\\main\\java";
    // 匹配有问题equals的正则
    private static final String SERVICE_REG = "\\@Service\\(.*\\)";
    // 是否打印到控制台
    private static boolean WRITE_CONSOLE = true;
    // 是否修改并写入文件
    private static boolean WRITE_FILE = false;

    public static void main(String[] args) {

        try {
            Pattern pattern = Pattern.compile(SERVICE_REG);

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
                    if(FixServiceTest.isBlank(line)){
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
                    if(FixServiceTest.isBlank(line)){
                        continue;
                    }
                    Matcher matcher = pattern.matcher(line);
                    boolean hasChanged = false;
                    while (matcher.find()){
                        String before = matcher.group();
                        afterLine = afterLine.substring(0, afterLine.indexOf("\"") - 1) + afterLine.substring(afterLine.lastIndexOf("\"") + 2);
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

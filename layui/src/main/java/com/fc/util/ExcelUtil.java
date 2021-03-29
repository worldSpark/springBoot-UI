package com.fc.util;

import com.fc.util.GlobalFunc;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述:
 *
 * @Author: wanpq
 * @Date: 2020/6/11 0011 14:28
 * @Version 1.0
 */
public class ExcelUtil {

    public static Map<String,Object> readXlsx(InputStream input,Map<String,Object> headerMap) throws IOException {
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        for (Sheet xssfSheet : workbook) {
            if (xssfSheet == null) {
                continue;
            }
            Map<String,Object> map = new HashMap<>();
            //每一行
            Map<String,Object> columnMap = new HashMap<>();
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                Row row = xssfSheet.getRow(rowNum);
                if(row==null){
                    continue;
                }
                int minCellNum = row.getFirstCellNum();
                int maxCellNum = row.getLastCellNum();
                Map<String,Object> valueMap = new HashMap<>();
                //每一列
                List<Map<String,Object>> filedList = new ArrayList<>();
                for (int i = minCellNum; i < maxCellNum; i++) {
                    //为第一行,则默认把第一行作为键,放入,否则对比列,把第一行值放入键中
                    if(rowNum==0){
                        org.apache.poi.ss.usermodel.Cell cell = row.getCell(i);
                        if (cell == null) {
                            continue;
                        }
                        //字段不为空,则添加自定义字段
                        String thisColumn = GlobalFunc.toString(String.valueOf(cell));
                        String thisZhColumn = GlobalFunc.toString(headerMap.get(thisColumn));
                        if(StringUtils.isNotBlank(thisZhColumn)){
                            Map<String,Object> filedMap = new HashMap<>();
                            filedMap.put("filed",thisZhColumn);
                            //filedMap.put("title", thisColumn);
                            filedList.add(filedMap);
                            columnMap.put("filed"+String.valueOf(i),thisZhColumn);
                        }
                    }else{
                        org.apache.poi.ss.usermodel.Cell cell = row.getCell(i);
                        if (cell == null) {
                            continue;
                        }
                        //获取到当前key值
                        String thisKey = GlobalFunc.toString(columnMap.get("filed"+i));
                        valueMap.put(thisKey,GlobalFunc.toString(cell));
                    }
                }
                if(ArrayUtil.isNotEmpty(filedList)){
                    dataMap.put("column",filedList);
                }
                if(valueMap!=null&&!valueMap.isEmpty()){
                    result.add(valueMap);
                }
            }
            dataMap.put("list",result);
            if(ArrayUtil.isNotEmpty(result)){
                dataMap.put("msg","success");
            }else {
                dataMap.put("msg","error");
            }
        }
        return dataMap;
    }

    public static Map<String,Object> readXls(InputStream input,Map<String, Object> headerMap) throws IOException {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        Map<String,Object> dataMap = new HashMap<>();
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet sheet = workbook.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            Map<String,Object> columnMap = new HashMap<>();
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                if(row==null){
                    continue;
                }
                int minCellNum = row.getFirstCellNum();
                int maxCellNum = row.getLastCellNum();
                Map<String,Object> valueMap = new HashMap<>();
                List<Map<String,Object>> filedList = new ArrayList<>();
                for (int i = minCellNum; i < maxCellNum; i++) {
                    //为第一行,则默认把第一行作为键,放入,否则对比列,把第一行值放入键中
                    if(rowNum==0){
                        HSSFCell cell = row.getCell(i);
                        if (cell == null) {
                            continue;
                        }
                        //字段不为空,则添加自定义字段
                        String thisColumn = GlobalFunc.toString(String.valueOf(cell));
                        String thisZhColumn = GlobalFunc.toString(headerMap.get(thisColumn));
                        if(StringUtils.isNotBlank(thisZhColumn)){
                            Map<String,Object> filedMap = new HashMap<>();
                            filedMap.put("filed",thisZhColumn);
                            filedList.add(filedMap);
                            columnMap.put("filed"+String.valueOf(i),thisZhColumn);
                        }
                    }else{
                        HSSFCell cell = row.getCell(i);
                        if (cell == null) {
                            continue;
                        }
                        //获取到当前key值
                        String thisKey = GlobalFunc.toString(columnMap.get("filed"+i));
                        valueMap.put(thisKey,GlobalFunc.toString(getStringVal(cell)));
                    }
                }
                if(ArrayUtil.isNotEmpty(filedList)){
                    dataMap.put("column",filedList);
                }
                if(valueMap!=null&&!valueMap.isEmpty()){
                    result.add(valueMap);
                }
            }
            dataMap.put("list",result);
            if(ArrayUtil.isNotEmpty(result)){
                dataMap.put("msg","success");
            }else {
                dataMap.put("msg","error");
            }
        }
        return dataMap;
    }

    private static String getStringVal(HSSFCell cell) {
        String result = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result =  cell.getBooleanCellValue() ? "TRUE" : "FALSE";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result =  cell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                result =  cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_STRING:
                result =  cell.getStringCellValue();
                break;
            default:
                return "";
        }
        return result;
    }

    /**
     * 功能: 导出为Excel工作簿，导出加文件名设置列宽
     *
     * @param sheetName [工作簿中的一张工作表的名称]
     * @param titleName [表格的标题名称]
     * @param headers   [表格每一列的列名]
     * @param dataSet   [要导出的数据源]
     * @param response  response
     * @param originals [字段名数组]
     * @param excelName [文件名字]
     * @param cellWidth 指定列的宽度 下标0为指定第几列从0开始,下标1指定宽度
     */
    public static void exportExcel(String sheetName, String titleName, String[] headers, List<Map<String, Object>> dataSet,
                                   HttpServletResponse response, String[] originals, String excelName, String[]... cellWidth) {
        HSSFWorkbook workbook = getHSSFWorkbook(new HSSFWorkbook(), sheetName, titleName, headers, dataSet, originals, cellWidth);
        exportExcel(workbook, response, excelName);
    }



    public static HSSFWorkbook getHSSFWorkbook(HSSFWorkbook workbook, String sheetName, String titleName, String[] headers, List<Map<String, Object>> dataSet, String[] originals, String[]... cellWidth) {
        // 生成一个工作表
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置工作表默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        //设置宽高
        for (String[] width : cellWidth) {
            if (width.length == 2) {
                sheet.setColumnWidth(GlobalFunc.parseInt(width[0]), GlobalFunc.parseInt(width[1]) * 372);
            }
        }
        //在工作表中合并首行并居中
        if (headers.length > 1) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
        }
        // 创建[标题]样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        // 设置[标题]样式
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        //创建[标题]字体
        HSSFFont titleFont = workbook.createFont();
        //设置[标题]字体
        titleFont.setFontHeightInPoints((short) 24);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把[标题字体]应用到[标题样式]
        titleStyle.setFont(titleFont);
        // 创建[列首]样式
        HSSFCellStyle headersStyle = workbook.createCellStyle();
        // 设置[列首]样式
        headersStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headersStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headersStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headersStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headersStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headersStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headersStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        //创建[列首]字体
        HSSFFont headersFont = workbook.createFont();
        //设置[列首]字体
        headersFont.setFontHeightInPoints((short) 12);
        headersFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把[列首字体]应用到[列首样式]
        headersStyle.setFont(headersFont);
        // 创建[表中数据]样式
        HSSFCellStyle dataSetStyle = workbook.createCellStyle();
        // 设置[表中数据]样式
        dataSetStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        dataSetStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataSetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataSetStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        dataSetStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        // 创建[表中数据]字体
        HSSFFont dataSetFont = workbook.createFont();
        // 设置[表中数据]字体
        dataSetFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把[表中数据字体]应用到[表中数据样式]
        dataSetStyle.setFont(dataSetFont);
        //创建标题行-增加样式-赋值
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(titleName);
        // 创建列首-增加样式-赋值
        HSSFRow row = sheet.createRow(1);
        for (short i = 0; i < headers.length; i++) {
            @SuppressWarnings("deprecation")
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headersStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 创建表中数据行-增加样式-赋值
        int index = 1;
        if (dataSet != null) {
            for (int i = 0; i < dataSet.size(); i++) {
                index++;
                row = sheet.createRow(index);
                int num = 0;
                Map<String, Object> map = dataSet.get(i);
                for (int j = 0; j < originals.length; j++) {
                    String original = GlobalFunc.toString(map.get(originals[j]));
                    HSSFCell cell = row.createCell(num);
                    num++;
                    cell.setCellStyle(dataSetStyle);
                    cell.setCellValue(original);
                }
            }
        }
        return workbook;
    }

    /**
     * 功能:真正实现导出
     */
    private static void doExportExcel(String sheetName, String titleName, String[] headers, Collection<?> dataSet, ServletOutputStream resultUrl, String pattern) {
        try {
            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 生成一个工作表
            HSSFSheet sheet = workbook.createSheet(sheetName);
            // 设置工作表默认列宽度为20个字节
            sheet.setDefaultColumnWidth((short) 20);
            //在工作表中合并首行并居中
            if (headers.length > 1) {
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
            }
            // 创建[标题]样式
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            // 设置[标题]样式
            titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //创建[标题]字体
            HSSFFont titleFont = workbook.createFont();
            //设置[标题]字体
            titleFont.setFontHeightInPoints((short) 24);
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            // 把[标题字体]应用到[标题样式]
            titleStyle.setFont(titleFont);

            // 创建[列首]样式
            HSSFCellStyle headersStyle = workbook.createCellStyle();
            // 设置[列首]样式
            headersStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headersStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headersStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headersStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headersStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headersStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //创建[列首]字体
            HSSFFont headersFont = workbook.createFont();
            //设置[列首]字体
            headersFont.setFontHeightInPoints((short) 12);
            headersFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            // 把[列首字体]应用到[列首样式]
            headersStyle.setFont(headersFont);

            // 创建[表中数据]样式
            HSSFCellStyle dataSetStyle = workbook.createCellStyle();
            // 设置[表中数据]样式
            dataSetStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            dataSetStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            dataSetStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            dataSetStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            dataSetStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            dataSetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            dataSetStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 创建[表中数据]字体
            HSSFFont dataSetFont = workbook.createFont();
            // 设置[表中数据]字体
            dataSetFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            // 把[表中数据字体]应用到[表中数据样式]
            dataSetStyle.setFont(dataSetFont);

            //创建标题行-增加样式-赋值
            HSSFRow titleRow = sheet.createRow(0);
            HSSFCell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellValue(titleName);

            // 创建列首-增加样式-赋值
            HSSFRow row = sheet.createRow(1);
            for (short i = 0; i < headers.length; i++) {
                @SuppressWarnings("deprecation")
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(headersStyle);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);

            }

            // 创建表中数据行-增加样式-赋值
            Iterator<?> it = dataSet.iterator();
            int index = 1;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                Object t = it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();

                int size = fields.length;
                int num = 0;
                for (short i = 0; i < size; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    if (value == null) {
                        continue;
                    }
                    HSSFCell cell = row.createCell(num);
                    num++;
                    cell.setCellStyle(dataSetStyle);

                    // 如果是时间类型,按照格式转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }

                    // 利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            // 不是数字做普通处理
                            cell.setCellValue(textValue);
                        }
                    }
                }
            }
            workbook.write(resultUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ---------------------------导出加文件名设置列宽
     * 功能: 导出为Excel工作簿
     * 参数: sheetName[工作簿中的一张工作表的名称]
     * 参数: titleName[表格的标题名称]
     * 参数: headers[表格每一列的列名]
     * 参数: dataSet[要导出的数据源]
     * 参数: resultUrl[导出的excel文件地址]
     * 参数: originals[字段名数组]
     * 参数：excelName[文件名字]
     * 参数: cellWidth 指定列的宽度 下标0为指定第几列从0开始,下标1指定宽度
     */
    public static void exportExcel(HSSFWorkbook workbook, HttpServletResponse response, String excelName) {
        try {
            //获取sheet
            String fileName;
            if (StringUtils.isNotBlank(excelName)) {
                fileName = excelName;
            } else {
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
                fileName = sdFormat.format(new Date());
            }
            FileOutputStream fos = new FileOutputStream(fileName + ".xls");

            workbook.write(fos);
            fos.flush();

            response.reset();// 清除Buffer
            response.setContentType("application/vnd.ms-excel");
            fileName = fileName + ".xls";
            response.addHeader("content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            File excel = new File(fileName);
            InputStream is = new FileInputStream(excel);
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int readed = 0; // 一次读多个，readed代表当前已读的数据总数

            while ((readed = is.read(buffer)) != -1) {
                os.write(buffer, 0, readed); // 从第0位写，reader代表读写几位
            }
            is.close();
            os.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String filePath, String downloadFileName) {
        //获取文件的路径
        String excelPath = request.getSession().getServletContext().getRealPath(filePath);

        // 读到流中
        InputStream inStream;
        try {
            inStream = new FileInputStream(excelPath);//文件的存放路径
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
            // 循环取出流中的数据
            byte[] b = new byte[1024];
            int len;

            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

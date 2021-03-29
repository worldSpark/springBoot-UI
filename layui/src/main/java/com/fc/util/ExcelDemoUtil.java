package com.fc.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: springbootui
 * @description: demo
 * @author: Mr.Wan
 * @create: 2021-03-29 11:15
 **/
public class ExcelDemoUtil {

    /**
     * 跨列导出
     * @param request
     * @param response
     */
    public void excelByMachineInfoAndUser(HttpServletRequest request, HttpServletResponse response){
        /**
         * 前台参数
         *  thisMachineInfo = machineInfo;
         *  thisMachineInfo['totalArea'] = msg.totalArea.toFixed(2)+"亩";
         *  thisMachineInfo['totalWorkTime'] = (Number(msg.totalWorkTime) / 24).toFixed(2)+"天";
         *  window.open("${ctx}/export/excelByMachineInfoAndUser?thisMachineInfo="+JSON.stringify(thisMachineInfo));
         */
        String thisMachineInfo = GlobalFunc.toString(request.getParameter("thisMachineInfo"));
        //转成json
        Map info = JSONObject.parseObject(thisMachineInfo,Map.class);
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("用户详情导出");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 5));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 3, 5));
        sheet.setColumnWidth(0, 6400);
        sheet.setColumnWidth(1, 4900);
        sheet.setColumnWidth(2, 4900);
        sheet.setColumnWidth(3, 6400);
        sheet.setColumnWidth(4, 6400);
        sheet.setColumnWidth(5, 6400);
        sheet.setColumnWidth(6, 8400);
        sheet.setColumnWidth(7, 6400);

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = row.createCell((short) 0);
        // 设置样式字体自动换行
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        cell = row.createCell(0);
        cell.setCellValue("用户详情导出");
        cell.setCellStyle(cellStyle);

        HSSFRow row2= sheet.createRow((int) 1);//设置第二行
        row2.setHeight((short) 360);
        HSSFCell cell2 = row2.createCell((short) 0);
        cell2 = row2.createCell(0);
        cell2.setCellValue("购机者:"+ GlobalFunc.toString(info.get("realName")));
        cell2.setCellStyle(style);
        cell2 = row2.createCell(1);
        cell2.setCellValue("证件号:"+GlobalFunc.toString(info.get("ownerIdcard")));
        cell2.setCellStyle(style);
        cell2 = row2.createCell(3);
        cell2.setCellValue("生产企业:"+GlobalFunc.toString(info.get("companyName")));
        cell2.setCellStyle(style);
        cell2 = row2.createCell(6);
        cell2.setCellValue("有轨迹天数:"+GlobalFunc.toString(info.get("totalWorkTime")));
        cell2.setCellStyle(style);
        cell2 = row2.createCell(7);
        cell2.setCellValue("作业总面积:"+GlobalFunc.toString(info.get("totalArea")));
        cell2.setCellStyle(style);

        HSSFRow row3= sheet.createRow((int) 2);//设置第三行
        row3.setHeight((short) 360);
        HSSFCell cell3 = row3.createCell((short) 0);
        cell3 = row3.createCell(0);
        cell3.setCellValue("所属区域:"+GlobalFunc.toString(info.get("parentName"))+GlobalFunc.toString(info.get("regionName")));
        cell3.setCellStyle(style);
        cell3 = row3.createCell(3);
        cell3.setCellValue("机具名称:"+GlobalFunc.toString(info.get("dicName")));
        cell3.setCellStyle(style);
        cell3 = row3.createCell(6);
        cell3.setCellValue("最后作业时间:"+GlobalFunc.toString(info.get("endTime")));
        cell3.setCellStyle(style);

        HSSFRow row4= sheet.createRow((int) 3);//设置第四行
        row4.setHeight((short) 360);
        HSSFCell cell4 = row4.createCell((short) 0);
        cell4 = row4.createCell(0);
        cell4.setCellValue("机具型号:"+GlobalFunc.toString(info.get("machineryModel")));
        cell4.setCellStyle(style);
        cell4 = row4.createCell(1);
        cell4.setCellValue("出厂编号:"+GlobalFunc.toString(info.get("factoryNumber")));
        cell4.setCellStyle(style);
        cell4 = row4.createCell(3);
        cell4.setCellValue("设备号:"+GlobalFunc.toString(info.get("iotNumber")));
        cell4.setCellStyle(style);

        ExcelUtil.exportExcel(wb,response,"用户详情导出");
    }

}

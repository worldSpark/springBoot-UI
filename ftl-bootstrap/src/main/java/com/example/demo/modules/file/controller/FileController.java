package com.example.demo.modules.file.controller;

import com.example.demo.core.entity.ProcessResult;
import com.example.demo.core.entity.ProcessStatHolder;
import com.example.demo.modules.file.model.SysFile;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;


/**
 * 文件上传下载
 *
 * @author yangfeng
 * @date 2018-06-04 12:57
 **/
@Controller
@RequestMapping("/file")
public class FileController {


    // 获得SpringBoot提供的mongodb的GridFS对象
    @Autowired
    private GridFsTemplate gridFsTemplate;


    /**
     * 文件上传
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ProcessResult uploadFile(HttpServletRequest request) throws Exception {
        Part part = request.getPart("file");
        // 获得提交的文件名
        String fileName = part.getSubmittedFileName();
        // 获得文件输入流
        InputStream ins = part.getInputStream();
        // 获得文件类型
        String contentType = part.getContentType();
        // 将文件存储到mongodb中,mongodb 将会返回这个文件的具体信息
        GridFSFile gridFSFile = gridFsTemplate.store(ins, fileName, contentType);
        SysFile fileInfo = new SysFile();
        fileInfo.setFileType(contentType);
        fileInfo.setFileName(fileName);
        fileInfo.setFileUrl(gridFSFile.getId().toString());
        return new ProcessResult(fileInfo);
    }

    /**
     * 下载
     *
     * @param fileId   文件id
     * @param response
     * @return
     */
    @RequestMapping(value = "/downloadFile")
    public void downloadFile(@RequestParam(name = "file_id") String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Query query = Query.query(Criteria.where("_id").is(fileId));
        // 查询单个文件
        GridFSDBFile gfsfile = gridFsTemplate.findOne(query);
        if (gfsfile == null) {
            return;
        }
        String fileName = gfsfile.getFilename().replace(",", "");
        //处理中文文件名乱码
        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            //非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        // 通知浏览器进行文件下载
        response.setContentType(gfsfile.getContentType());
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        gfsfile.writeTo(response.getOutputStream());
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public ProcessResult deleteFile(@RequestParam(name = "file_id") String fileId) {
        Query query = Query.query(Criteria.where("_id").is(fileId));
        // 查询单个文件
        GridFSDBFile gfsfile = gridFsTemplate.findOne(query);
        if (gfsfile == null) {
            return new ProcessResult(ProcessStatHolder.RESULT_STAT_ERROR,"未找到文件");
        }
        gridFsTemplate.delete(query);
        return new ProcessResult();
    }



    @RequestMapping(value = "/preview")
    public void preview(@RequestParam(name = "file_id") String fileId,HttpServletResponse response) {
        //获取存储在mongodb文件对应的id
        //Attachment attachment = attachmentService.getAttachmentDetail(attachmentId);
        Query query = Query.query(Criteria.where("_id").is(fileId));
        // 查询单个文件
        GridFSDBFile gridFSDBFile = gridFsTemplate.findOne(query);
        try {
            String fileType = gridFSDBFile.getFilename().substring(gridFSDBFile.getFilename().lastIndexOf(".") + 1);
            if("xls".equalsIgnoreCase(fileType)||"xlsx".equalsIgnoreCase(fileType)){//1、EXCEL（转换为HTML预览）
                HSSFWorkbook workBook = new HSSFWorkbook(gridFSDBFile.getInputStream());
                ExcelToHtmlConverter converter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
                converter.setOutputColumnHeaders(false);// 不显示列的表头
                converter.setOutputRowNumbers(false);// 不显示行的表头
                converter.processWorkbook(workBook);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                StreamResult streamResult = new StreamResult(outStream);
                Transformer serializer = TransformerFactory.newInstance().newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(new DOMSource(converter.getDocument()),
                        streamResult);
                response.getOutputStream().write(outStream.toByteArray());
            }else if("doc".equalsIgnoreCase(fileType)||"docx".equalsIgnoreCase(fileType)){//2、word（转换为HTML预览）
                HWPFDocument wordDocument = new HWPFDocument(gridFSDBFile.getInputStream());
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                        DocumentBuilderFactory.newInstance().newDocumentBuilder()
                                .newDocument());
                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                    public String savePicture(byte[] content, PictureType pictureType,
                                              String suggestedName, float widthInches, float heightInches) {
                        return "E:/test/" + suggestedName;
                    }
                });
                List pics = wordDocument.getPicturesTable().getAllPictures();
                if (pics != null) {
                    for (int i = 0; i < pics.size(); i++) {
                        Picture pic = (Picture) pics.get(i);
                        try {
                            pic.writeImageContent(new FileOutputStream("E:/test/" + pic.suggestFullFileName()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                wordToHtmlConverter.processDocument(wordDocument);
                Document htmlDocument = wordToHtmlConverter.getDocument();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                DOMSource domSource = new DOMSource(htmlDocument);
                StreamResult streamResult = new StreamResult(outStream);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer serializer = tf.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(domSource, streamResult);
                outStream.close();
                //String content = new String(outStream.toByteArray());
                response.getOutputStream().write(outStream.toByteArray());
            }
            //3、PDF+TXT JPG JPEG
            BufferedReader bis = null;
            BufferedInputStream br = new BufferedInputStream(gridFSDBFile.getInputStream());
            response.setHeader("Content-Disposition",
                    "inline; filename=" + URLEncoder.encode(gridFSDBFile.getFilename(), "UTF-8"));
            OutputStream out = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = br.read(buf)) != -1)
                out.write(buf, 0, len);
            br.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

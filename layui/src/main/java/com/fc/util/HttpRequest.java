package com.fc.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            //PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"utf-8"));
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置token请求响应
     * @param url
     * @param param
     * @param token
     * @return
     */
    public static String sendPost(String url, String param,String token) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置tocken
            conn.setRequestProperty("Authorization",token);
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            //PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"utf-8"));
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            //in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostParam(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("connection", "UTF-8");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * 发起http请求并获取结果 UTF-8
     *
     * @param requestUrl 请求地址
     * @param mimeTypes enum MimeTypes
     * @param requestMethod 请求方式（GET、POST）
     * @param connTimeoutMills 设置连接主机超时，单位：毫秒
     * @param readTimeoutMills 设置从主机读取数据超时，单位：毫秒
     * @param attachments 附加提交的数据，可以是单字符串{"json":"value"} 或者 多个参数遵循 A=a&B=b格式
     *
     * @return remoteHttp 返回的结果
     */
    public static String httpRequest(String requestUrl, String mimeTypes, String requestMethod,
                                     int connTimeoutMills, int readTimeoutMills, String attachments)
            throws Exception
    {
        return httpRequest(requestUrl, "UTF-8", "text/plain;charset=UTF-8", requestMethod, connTimeoutMills,
                readTimeoutMills, attachments);
    }


    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param chartSet 字符集
     * @param mimeTypes enum MimeTypes
     * @param requestMethod 请求方式（GET、POST）
     * @param connTimeoutMills 设置连接主机超时，单位：毫秒
     * @param readTimeoutMills 设置从主机读取数据超时，单位：毫秒
     * @param attachments 附加提交的数据，可以是单字符串{"json":"value"} 或者 多个参数遵循 A=a&B=b格式
     *
     * @return remoteHttp 返回的结果
     */
    public static String httpRequest(String requestUrl, String chartSet, String mimeTypes,
                                     String requestMethod, int connTimeoutMills,
                                     int readTimeoutMills, String attachments)
            throws Exception
    {
        HttpURLConnection httpUrlConn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        // PS:其实亦可以统一使用ObjectOutputStream，因为String也是impl Serializable
        OutputStream outputStream = null;
        StringBuilder buffer = new StringBuilder();
        try
        {
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection)url.openConnection();
            // 设置content_type
            httpUrlConn.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
            httpUrlConn.setConnectTimeout(connTimeoutMills);
            httpUrlConn.setReadTimeout(readTimeoutMills);
            // 设置是否向httpUrlConn输出，因为兼容post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false
            httpUrlConn.setDoOutput(true);
            // 设置是否从httpUrlConn读入，默认情况下是true
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);


            // 设置请求方式（GET/POST），默认是GET
            httpUrlConn.setRequestMethod(requestMethod);
            // 完成相关配置后，进行connect操作，实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求
            httpUrlConn.connect();


            // 当有额外数据需要提交时
            if (StringUtils.isNotBlank(attachments))
            {
                // 此处getOutputStream会隐含的进行connect，即：如同调用上面的connect()方法，
                // 所以在开发中不调用上述的connect()也可以，不过建议最好显式调用
                outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(attachments.getBytes(chartSet));
                outputStream.flush();
                // outputStream不是一个网络流，充其量是个字符串流，往里面写入的东西不会立即发送到网络，
                // 而是存在于内存缓冲区中，待outputStream流关闭时，根据输入的内容生成http正文。所以这里的close是必须的
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            // 无论是post还是get，http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去
            inputStream = httpUrlConn.getInputStream();//注意，实际发送请求的代码段就在这里
            inputStreamReader = new InputStreamReader(inputStream, chartSet);
            bufferedReader = new BufferedReader(inputStreamReader);


            String str = null;
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                bufferedReader.close();
                inputStreamReader.close();
                inputStreamReader.close();
                inputStream.close();
                outputStream.close();
                /*IOUtils.closeQuietly(bufferedReader);
                IOUtils.closeQuietly(inputStreamReader);
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);*/
                if (httpUrlConn != null)
                {
                    httpUrlConn.disconnect();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return buffer.toString();
    }


    /**
     * 采取post方式提交序列化后的object对象 </br>
     * 另请参考：java.io.ObjectInputStream/ObjectOutputStream
     * @param requestUrl 请求地址
     * @param connTimeoutMills 设置连接主机超时，单位：毫秒
     * @param readTimeoutMills 设置从主机读取数据超时，单位：毫秒
     * @param serializedObject 序列化后的object对象
     *
     * @return remoteHttp返回的结果
     */
    public static String httpPostSerialObject(String requestUrl, int connTimeoutMills,
                                              int readTimeoutMills, Object serializedObject)
            throws Exception
    {
        HttpURLConnection httpUrlConn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        ObjectOutputStream oos = null;
        StringBuilder buffer = new StringBuilder();
        try
        {
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection)url.openConnection();
            // 设置content_type=SERIALIZED_OBJECT
            // 如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException
            httpUrlConn.setRequestProperty("Content-Type",
                    "text/plain;charset=UTF-8");
            httpUrlConn.setConnectTimeout(connTimeoutMills);
            httpUrlConn.setReadTimeout(readTimeoutMills);
            // 设置是否向httpUrlConn输出，因为是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false
            httpUrlConn.setDoOutput(true);
            // 设置是否从httpUrlConn读入，默认情况下是true
            httpUrlConn.setDoInput(true);
            // 不使用缓存
            httpUrlConn.setUseCaches(false);


            // 设置请求方式，默认是GET
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.connect();


            if (serializedObject != null)
            {
                // 此处getOutputStream会隐含的进行connect，即：如同调用上面的connect()方法，
                // 所以在开发中不调用上述的connect()也可以，不过建议最好显式调用
                // write object(impl Serializable) using ObjectOutputStream
                oos = new ObjectOutputStream(httpUrlConn.getOutputStream());
                oos.writeObject(serializedObject);
                oos.flush();
                // outputStream不是一个网络流，充其量是个字符串流，往里面写入的东西不会立即发送到网络，
                // 而是存在于内存缓冲区中，待outputStream流关闭时，根据输入的内容生成http正文。所以这里的close是必须的
                oos.close();
            }
            // 将返回的输入流转换成字符串
            // 无论是post还是get，http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去
            inputStream = httpUrlConn.getInputStream();//注意，实际发送请求的代码段就在这里
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);


            String str = null;
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                bufferedReader.close();
                inputStreamReader.close();
                inputStreamReader.close();
                inputStream.close();
                oos.close();
                //这里引用的是mybatis-plus-core的jar
                /*IOUtils.closeQuietly(bufferedReader);
                IOUtils.closeQuietly(inputStreamReader);
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(oos);*/
                if (httpUrlConn != null)
                {
                    httpUrlConn.disconnect();
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {
        Map<String,Object> paraMap=new HashMap<String,Object>();
        paraMap.put("factoryNumber","ZRLJF313184");
        System.out.println(httpRequest("http://njzydc.jx.gnb360.cn/updateUserByFactoryNumber?factoryNumber=ZRLJF313184", "text/plain;charset=UTF-8", "POST",
                100000, 100000, ""));
    }
}

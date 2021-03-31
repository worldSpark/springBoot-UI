package com.fc.servlet;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class SendMessage {
    /**
     * @param yzm    生成的随机验证码
     * @param mobile 需要发送验证码的手机号码
     * @return
     * @throws IOException
     */
    public static String sendSMS(String yzm, String mobile) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://http.yunsms.cn/tx/");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data = {new NameValuePair("uid", "190590"), new NameValuePair("pwd", "7fef6171469e80d32c0559f88b377245"), new NameValuePair("mobile", mobile), new NameValuePair("content", "您的验证码是：" + yzm + "此验证码应用于微信亲情送注册，10分钟之内有效。" + "【亲情送】")};
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result); //打印返回消息状态
        post.releaseConnection();
        return result;
    }

    /**
     * 生成随机验证码
     *
     * @return
     */
    public static int getPhoneVerification() {
        int phoneVerification = 0;
        for (int j = 0; j < 100; j++) {
            phoneVerification = (int) ((Math.random() * 9 + 1) * 100000);
        }
        return phoneVerification;
    }

    public static void main(String[] args) {
        System.out.println(SendMessage.getPhoneVerification());
    }
}

package com.fc.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @description: aes加密
 * @author: Mr.Wan
 * @create: 2020-11-24 08:58
 **/
public class AesUtil {
    //密钥 (需要前端和后端保持一致)
    private static final String KEY = "jxljtg0123456789";
    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * aes解密
     * @param encrypt   内容
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encrypt) {
        try {
            return aesDecrypt(encrypt, KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return encrypt;
        }
    }

    /**
     * aes加密
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) {
        try {
            return aesEncrypt(content, KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        String content1 = "123加密前崽";
        String content = "1328968636874199041";
        String content2 = "code=forestry&id=1272737101586108417&videoUrl=https://live.jxljtg.cn/view.html&homeNum=1&classroomTitle=演示用直播11-19&liveType=2&projectType=1&photoUrl=forestry/0ddbc67ae87648be8737f28c3c200e0d.jpg&classroomAbstract=测试非专家直播&userName=admin_xt";
        System.out.println("加密前：" + content);
        System.out.println("加密密钥和解密密钥：" + KEY);
        String encrypt = aesEncrypt(content, KEY);
        System.out.println("加密后：" + encrypt);
        String decrypt = aesDecrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);

    }

    /**
     * 编码
     *
     * @param content
     * @return
     */
    public static String encode(byte[] content) {
        return new sun.misc.BASE64Encoder().encode(content);
    }

    /**
     * 解码
     *
     * @param source
     * @return
     */
    public static byte[] decode(String source) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            return decoder.decodeBuffer(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static String[] hex = {
            "00","01","02","03","04","05","06","07","08","09","0A","0B","0C","0D","0E","0F",
            "10","11","12","13","14","15","16","17","18","19","1A","1B","1C","1D","1E","1F",
            "20","21","22","23","24","25","26","27","28","29","2A","2B","2C","2D","2E","2F",
            "30","31","32","33","34","35","36","37","38","39","3A","3B","3C","3D","3E","3F",
            "40","41","42","43","44","45","46","47","48","49","4A","4B","4C","4D","4E","4F",
            "50","51","52","53","54","55","56","57","58","59","5A","5B","5C","5D","5E","5F",
            "60","61","62","63","64","65","66","67","68","69","6A","6B","6C","6D","6E","6F",
            "70","71","72","73","74","75","76","77","78","79","7A","7B","7C","7D","7E","7F",
            "80","81","82","83","84","85","86","87","88","89","8A","8B","8C","8D","8E","8F",
            "90","91","92","93","94","95","96","97","98","99","9A","9B","9C","9D","9E","9F",
            "A0","A1","A2","A3","A4","A5","A6","A7","A8","A9","AA","AB","AC","AD","AE","AF",
            "B0","B1","B2","B3","B4","B5","B6","B7","B8","B9","BA","BB","BC","BD","BE","BF",
            "C0","C1","C2","C3","C4","C5","C6","C7","C8","C9","CA","CB","CC","CD","CE","CF",
            "D0","D1","D2","D3","D4","D5","D6","D7","D8","D9","DA","DB","DC","DD","DE","DF",
            "E0","E1","E2","E3","E4","E5","E6","E7","E8","E9","EA","EB","EC","ED","EE","EF",
            "F0","F1","F2","F3","F4","F5","F6","F7","F8","F9","FA","FB","FC","FD","FE","FF"
    };
    private final static byte[] val = {
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,
            0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F,0x3F
    };
    public static String escape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch == ' ') {                        // space : map to '+'
                sbuf.append('+');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char)ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char)ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9' : as it was
                sbuf.append((char)ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                    || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*'
                    || ch == '/' || ch == '('
                    || ch == ')') {
                sbuf.append((char)ch);
            } else if (ch <= 0x007F) {              // other ASCII : map to %XX
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else {                                // unicode : map to %uXXXX
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[(ch >>> 8)]);
                sbuf.append(hex[(0x00FF & ch)]);
            }
        }
        return sbuf.toString();
    }
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if (ch == '+') {                        // + : map to ' '
                sbuf.append(' ');
            } else if ('A' <= ch && ch <= 'Z') {    // 'A'..'Z' : as it was
                sbuf.append((char)ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z' : as it was
                sbuf.append((char)ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9' : as it was
                sbuf.append((char)ch);
            } else if (ch == '-' || ch == '_'       // unreserved : as it was
                    || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*'
                    || ch == '/' || ch == '('
                    || ch == ')') {
                sbuf.append((char)ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i+1)) {         // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[s.charAt(i+1)];
                    cint = (cint << 4) | val[s.charAt(i+2)];
                    i+=2;
                } else {                            // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[s.charAt(i+2)];
                    cint = (cint << 4) | val[s.charAt(i+3)];
                    cint = (cint << 4) | val[s.charAt(i+4)];
                    cint = (cint << 4) | val[s.charAt(i+5)];
                    i+=5;
                }
                sbuf.append((char)cint);
            }
            i++;
        }
        return sbuf.toString();
    }

}

package com.fc.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * 功能描述:AES加密解密
 *
 * @Author: wanpq
 * @Date: 2020/6/29 0029 11:25
 * @Version 1.0
 */
public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
    private static final String ENCODING = "UTF-8";
    private static final String PASSWORD = "46EBA22EF5204DD5B110A1F730513965"; // 加密秘钥

    /**
     * AES加密
     *
     * @param content 明文
     * @return 密文
     */
    public static String encryptAES(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        //加密前先解密看看是否为null,为null再去加密,处理原因是可能有些user没有改变
        String s = decryptAES(content);
        if(StringUtils.isNotBlank(s)&&!s.equals(content)){
            return content;
        }
        byte[] encryptResult = encrypt(content, PASSWORD);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        // BASE64位加密
        encryptResultStr = ebotongEncrypto(encryptResultStr);
        return encryptResultStr;

    }

    /**
     * * AES解密
     * *
     * * @param encryptResultStr
     * *            密文
     * * @return 明文
     */

    public static String decryptAES(String encryptResultStr) {
        if (StringUtils.isEmpty(encryptResultStr)) {
            return "";
        }
        // BASE64位解密
        try {
            String decrpt = ebotongDecrypto(encryptResultStr);
            byte[] decryptFrom = parseHexStr2Byte(decrpt);
            byte[] decryptResult = decrypt(decryptFrom, PASSWORD);
            String thisResult = new String(decryptResult, "UTF-8");
            return thisResult;

        } catch (Exception e) { // 当密文不规范时会报错，可忽略，但调用的地方需要考虑
            return encryptResultStr;

        }

    }

    /**
     * 加密字符串
     */

    private static String ebotongEncrypto(String str) {
        BASE64Encoder base64encoder = new BASE64Encoder();
        String result = str;
        if (str != null && str.length() > 0) {
            try {
                byte[] encodeByte = str.getBytes(ENCODING);
                result = base64encoder.encode(encodeByte);

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        // base64加密超过一定长度会自动换行 需要去除换行符
        return result.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");

    }

    /**
     * 解密字符串
     */

    private static String ebotongDecrypto(String str) {
        BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            byte[] encodeByte = base64decoder.decodeBuffer(str);
            return new String(encodeByte);

        } catch (IOException e) {
            logger.error("IO 异常", e);
            return str;

        }
    }

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */


    private static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 注意这句是关键，防止linux下 随机生成key。用其他方式在Windows上正常，但Linux上会有问题
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            // kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            logger.error("加密异常", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    private static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            // kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            logger.error("解密异常", e);
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        express();
    }

    /**
     * 测试
     */
    private static void express() {
        String content = "RjE5MzFEQjNDREE1RTE4MTAwMDhFMjZBRTBERUMxNDk4MjMyQzI5REY5Q0E3RjYzRDBEOUE5MzYwNDg2QUZGQg==";
        System.out.println("原内容为：" + content);
        String encryContent = encryptAES(content);
        System.out.println("加密后的内容为：" + encryContent);
        String decryContent = decryptAES(content);
        System.out.println("解密后的内容为：" + decryContent);
    }
}

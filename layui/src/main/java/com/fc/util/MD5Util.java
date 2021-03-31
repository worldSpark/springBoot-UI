package com.fc.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
* MD5 工具类-建议添油加醋的对入参 str 改造一下
* @author Administrator
*
*/
public class MD5Util {


	//工具类不允许被实例化
	private MD5Util() throws Exception {
		throw new Exception("异常");
	}
	public static String encode(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++){
			byteArray[i] = (byte) charArray[i];
		}

		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}

			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String EncoderByMd5(String buf) {
		try {
			MessageDigest digist = MessageDigest.getInstance("MD5");
			byte[] rs = digist.digest(buf.getBytes("ASCII"));
			StringBuffer digestHexStr = new StringBuffer();
			for (int i = 0; i < 16; i++) {
				digestHexStr.append(byteHEX(rs[i]));
			}
			return digestHexStr.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static String byteHEX(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}


	private static String sortChar(String str) {
		// 1.将字符串转化成数组
		char[] chs = stringToArray(str);
		// 2.对数组进行排序
		sort(chs);
		// 3.将数组转成字符串
		return toString(chs);
	}

	private static String toString(char[] chs) {
		return new String(chs);
	}

	// 对字符数组进行升序排序
	private static void sort(char[] chs) {
		Arrays.sort(chs);
	}

	// 将字符串转化成为数组
	private static char[] stringToArray(String string) {
		return string.toCharArray();
	}

	public static  String StringToMD5(String data,String key){
		String value = data + key;
		String newValue = sortChar(value);
		String md5 = EncoderByMd5(newValue);
		return md5;
	}

}

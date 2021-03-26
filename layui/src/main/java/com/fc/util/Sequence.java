package com.fc.util;


import org.safehaus.uuid.UUIDGenerator;

import java.util.UUID;

/**
 * @Description 系统唯一标识生成类
 * @author chencc
 * @Date 2018年03月03日 上午08:12:00
 * @version 1.0.0
 * */

public class Sequence {
	private final static int MAX_DEPTS_PER_LEVEL = 100;

	private final static int MAX_GROUP_PER_LEVEL = 100;

	private final static int MAX_PERSONS_PER_LEVEL = 1000;

	private static final int base = 100000;

	private static long millis = 0;

	private static long counter = 0;

	private static long old = 0;

	public final static int MAX_TOPIC_NUMBER = 1000000;

	public final static int MAX_TOPICCHILD_NUMBER = 1000;

	public final static int MAX_TITLE_COUNT = 10000;

	public final static int MAX_SHORTMESSAGE_NUMBER = 9999;

	private static char[] HandlerChars = { 'A', 'B', 'C' };

	private static char baseChar = 'A';

	private static final char maxChar = 'Z';

	private static Object obj = new Object();


	/**
	 * Get the base Sequence.
	 *
	 * @return the Sequence
	 */
	public static synchronized String getSequence() {
		UUIDGenerator gen = UUIDGenerator.getInstance();
		org.safehaus.uuid.UUID uuid = gen.generateTimeBasedUUID();

		String uuidStr = uuid.toString();
		String[] uuidParts = uuidStr.split("-");
		StringBuffer builder = new StringBuffer();
		builder.append(uuidParts[2]);
		builder.append("-");
		builder.append(uuidParts[1]);
		builder.append("-");
		builder.append(uuidParts[0]);
		builder.append("-");
		builder.append(uuidParts[3]);
		builder.append("-");
		builder.append(uuidParts[4]);

		return builder.toString();
	}

	public static synchronized String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static synchronized String getTimeSequence()  {
		long result = System.currentTimeMillis();
		if (result == millis) {
			old++;
			if (old >= base)
			result = millis * base + old;
		} else {
			millis = result;
			result *= base;
			old = 0;
		}
		return result + "";
	}



	public static synchronized long getSequenceTimes() {
		try {
			long rtn = System.currentTimeMillis();
			synchronized(obj){
				while (rtn == counter) {
					// Thread.sleep(2);
					// Thread.currentThread().wait(2);
					obj.wait(2);
					rtn = System.currentTimeMillis();
				}
			}
			counter = rtn;
			return rtn;
		} catch (Exception ie) {
			return System.currentTimeMillis();
		}
	}

	/**
	 * 获取uuid
	 * @return
	 */
	public static String getRadomUUID(){
		return cn.hutool.core.lang.UUID.randomUUID().toString();
	}

	public static void main(String[] args) throws Exception {

		// String receiver = "13725303059";
		// String applicationid = "01b807a0-8c7f-3f80-aa27-c741f5c90259";
		// for (int i = 0; i < 260 + 1; i++) {
		// (getShortMessageCode(receiver, applicationid));
		// }

		// (Sequence.getSequenceTimes());// 125292303662500000
		System.out.print(new Sequence().getSequence());
	}
}

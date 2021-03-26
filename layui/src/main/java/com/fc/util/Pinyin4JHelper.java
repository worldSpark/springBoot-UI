package com.fc.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Description 中文转全拼工具类
 * @author chencc
 * @Date 2016年04月22日 上午09:04:00
 * @version 1.0.0
 * */
public class Pinyin4JHelper {

    private static HanyuPinyinOutputFormat format = null;
    private static String[] pinyin;

    public Pinyin4JHelper(){
        format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyin = null;
    }
	//转换单个字符
    public static String getCharacterPinYin(char c){
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        String result = "";
        if(pinyin != null && pinyin.length > 0) {
            // 只取一个发音，如果是多音字，仅取第一个发音
            result = pinyin[0];
        }else{
            // 没有拼音，返回本身
            result = GlobalFunc.toString(c);
        }
        return result;
    }

    //转换一个字符串
    public static String getStringPinYin(String str) {
          StringBuilder sb = new StringBuilder();
          String tempPinyin = null;

          for(int i = 0; i < str.length(); ++i){
               tempPinyin =getCharacterPinYin(str.charAt(i));
               if(tempPinyin == null) {
                    // 如果str.charAt(i)非汉字，则保持原样
                    sb.append(str.charAt(i));
               } else{
                    sb.append(tempPinyin);
               }
          }
          return sb.toString();
    }


    public static void main(String[] args){
        new Pinyin4JHelper();
        System.out.print("转换拼音码如下："+ getStringPinYin("尘世得受到法律上的发"));
    }
}

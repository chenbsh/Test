package com.coding.jdbc.tools;

import com.coding.jdbc.exception.SYHCException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 常用工具类
 * <p>
 * 进制换行、字符串分隔与合并、字符编码转换、MD5加密、随机数、日期时间格式化
 *
 * @Copyright MacChen
 * @Project CodeGenerationTool
 * @Author MacChen
 * @timer 2017-12-01
 * @Version 1.0.0
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
public class GeneralUtilTools {

    /**
     * 无参的构造方法
     */
    public GeneralUtilTools() {
        super();
    }

    /**
     * 十进制字节数组转十六进制字符串
     */
    public static final String toHexadecimal(byte[] decimalism) {
        String stmp = null;
        StringBuilder hexadecimal = new StringBuilder(decimalism.length * 2);
        for (int n = 0; n < decimalism.length; n++) {
            // 整数转成十六进制表示
            stmp = Integer.toHexString(decimalism[n] & 0XFF);
            if (stmp.length() == 1)
                hexadecimal.append("0").append(stmp);
            else
                hexadecimal.append(stmp);
        }
        return hexadecimal.toString();
    }

    // -------------------------------------------------------------------------------------------

    /**
     * 十六进制字符串转十进制字节数组
     */
    public static final byte[] toDecimalism(String hexadecimal) {
        byte[] bytes = hexadecimal.getBytes();
        if ((bytes.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[bytes.length / 2];
        for (int n = 0; n < bytes.length; n += 2) {
            String item = new String(bytes, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个十进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 按照某分隔符合并字符串数组
     *
     * @param source           源字符串数组
     * @param separator=字符串分隔符
     * @return String=合并后字符串
     */
    public static String mergeString(String source[], String separator) {
        if (source == null || separator == null || source.length == 0)
            return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < source.length; i++) {
            if (StringUtils.isNotBlank(source[i])) {
                builder.append(source[i]);
                if (i + 1 != source.length) {
                    builder.append(separator);
                }
            }
        }
        return builder.toString();
    }

    /**
     * 根据分隔符统计字符串数量
     *
     * @param souce=源字符串
     * @param separator=字符串分隔符
     * @return int=字符串数
     */
    public static int countForSubString(String souce, String separator) {
        if (souce == null || souce.length() == 0) {
            return 0;
        }
        StringTokenizer token = new StringTokenizer(souce, separator);
        return token.countTokens();
    }

    /**
     * 统计字符串中包含某子字符串数量
     *
     * @param souce=源字符串
     * @param sonString=子字符串
     * @return int=字符数
     */
    public static int countForSubStringByContain(String souce, String sonString) {
        // 查找某一字符串中souce，特定子串sonString的出现次数
        if (souce == null)
            return 0;
        int i = souce.length();
        souce = souce.replaceAll(sonString, "");// 反串中的字符sonString替换成""
        return (i - souce.length()) / sonString.length();
    }

    /**
     * 按照某分隔符合并字符串列表集合
     *
     * @param sourceList=源字符串数集合
     * @param separator=字符串分隔符
     * @return String=合并后字符串
     */
    public static String mergeString(List<String> sourceList, String separator) {
        if (sourceList == null || separator == null || sourceList.size() == 0)
            return "";
        StringBuilder str = new StringBuilder();
        Iterator<String> iter = sourceList.iterator();
        while (iter.hasNext()) {
            str.append(iter.next());
            if (iter.hasNext() == false)
                str.append(separator);
        }
        return str.toString();
    }

    /**
     * 根据分隔符拆分字符串并得到子字符串数组集合
     *
     * @param souce=源字符串
     * @param separator=字符串分隔符
     * @return String[]=子字符串数组
     */
    public static String[] stringToArray(String souce, String separator) {
        if (souce == null || souce.length() == 0) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(souce, separator);
        String array[] = new String[token.countTokens()];
        int arrayFlag = 0;
        while (token.hasMoreElements()) {
            array[arrayFlag] = token.nextToken();
            arrayFlag = arrayFlag + 1;
        }
        return array;
    }

    /**
     * 根据分隔符拆分字符串并得到子字符串数组集合
     *
     * @param souce=源字符串［key、value、key、value……］
     * @param separator=字符串分隔符
     * @return String[]=子字符串数组
     */
    public static String[][] stringToTwoArray(String souce, String separator) {
        if (souce == null || souce.length() == 0) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(souce, separator);
        String array[][] = new String[token.countTokens() / 2][];
        int arrayFlag = 0;
        while (token.hasMoreElements()) {
            array[arrayFlag][0] = token.nextToken();
            array[arrayFlag][1] = token.nextToken();
            arrayFlag = arrayFlag + 1;
        }
        return array;
    }

    // ---------------------------------------------------------------------------------------------------------------------------

    /**
     * 将源字符串转换成指定编码格式的字符串 （一般用于不知道自己本身编码格式情况）
     *
     * @param sourceStr       源字符串
     * @param targetEncoding= 目标编码格式
     * @return String=字符串
     */
    public static String convertCharacterCode(String sourceStr, String targetEncoding) throws UnsupportedEncodingException {
        if (sourceStr != null)
            return new String(sourceStr.getBytes(), targetEncoding);
        return "";
    }

    /**
     * 将指定编码格式的源字符串转换成目标编码格式的字符串
     *
     * @param sourceStr            源字符串
     * @param sourceEncoding=原编码格式
     * @param targetEncoding=      目标编码格式
     * @return String=字符串
     */
    public static String convertCharacterCode(String sourceStr, String sourceEncoding, String targetEncoding) throws UnsupportedEncodingException {
        if (sourceStr != null)
            return sourceStr = new String(sourceStr.getBytes(sourceEncoding), targetEncoding);
        return "";
    }

    /**
     * 将指定编码格式的源字符串转换成UNICODE格式的字符串
     *
     * @param sourceStr 源字符串
     * @return String=UNICODE格式的字符串
     */
    public static String convertCharacterCode(String sourceStr) {
        StringBuilder target = new StringBuilder();
        char array[] = sourceStr.toCharArray();
        for (char element : array) {
            target.append("\\u" + Integer.toHexString(element & 0xffff));
        }
        return target.toString();
    }

    // ------------------------------------------------------------------------------------------------------------------

    /**
     * 根据长度要求获取随机数字字符串
     *
     * @param length 数字字符串长度
     * @return String=数字字符串
     */

    public static String getRandomNumericString(int length) {
        Random rd = new Random();
        StringBuilder rstr = new StringBuilder();
        for (int i = 0; i < length; i++) {
            rstr.append(rd.nextInt(10));
        }
        return rstr.toString();
    }

    /**
     * 根据长度要求获取随机英文字符串
     *
     * @param length     英文字符串长度
     * @param formatType 英文字符串格式（1=小写字母,2=大写字母，3=大小写混合）
     * @return String=英文字符串
     */

    public static String getRandomEnString(int length, int formatType) {
        int i = 0;
        Random r = new Random();
        StringBuffer code = new StringBuffer();
        String exChars = null;// exChars：排除特殊字符
        while (i < length) {
            if (formatType == 1) {
                int t = r.nextInt(123);
                if ((t >= 97) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                    code.append((char) t);
                    i++;
                }
            } else if (formatType == 2) {
                int t = r.nextInt(91);
                if ((t >= 65) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                    code.append((char) t);
                    i++;
                }
            } else if (formatType == 3) {
                int t = r.nextInt(123);
                if ((t >= 97 || (t >= 65 && t <= 90)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                    code.append((char) t);
                    i++;
                }
            }
        }
        return code.toString();
    }

    /**
     * 根据长度要求获取随机数字字母混合字符串
     *
     * @param length 混合字符串长度
     * @return String=混合字符串
     */
    public static String getRandomString(int length) {
        int i = 0;
        String exChars = null;// exChars：排除特殊字符
        Random r = new Random();
        StringBuffer code = new StringBuffer();
        while (i < length) {
            int t = r.nextInt(123);
            if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                code.append((char) t);
                i++;
            }
        }
        return code.toString();
    }

    /**
     * 根据分隔符拆分字符串并得到子字符串列表集合
     *
     * @param souce=源字符串
     * @param separator=字符串分隔符
     * @return List<String>=子字符串集合
     */
    public static List<String> stringToList(String souce, String separator) {
        if (souce == null || souce.length() == 0) {
            return null;
        }
        StringTokenizer token = new StringTokenizer(souce, separator);
        ArrayList<String> list = new ArrayList<String>(token.countTokens());
        while (token.hasMoreElements()) {
            list.add(token.nextToken());
        }

        return list;
    }

    public static String getPrimaryKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 格式化时间字符串
     *
     * @param typecode 1=YYYY-MM-DD HH:MM:SS，2=YYYY-MM-DD，3=YYYYMMDD，4=YYYYMM，5=YYYY/MM/DD，6=DD/MM，7=YYYY-MM
     * @return 格式化的时间字符串
     */
    public static String getFormatDate(Integer typecode) {
        if (typecode == 1) {
            return DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss");
        } else if (typecode == 2) {
            return DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd");
        } else if (typecode == 3) {
            return DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd");
        } else if (typecode == 4) {
            return DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");
        } else if (typecode == 5) {
            return DateFormatUtils.format(Calendar.getInstance(), "yyyy/MM/dd");
        } else if (typecode == 6) {
            return DateFormatUtils.format(Calendar.getInstance(), "dd/MM");
        } else if (typecode == 7) {
            return DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM");
        } else if (typecode == 8) {
            return DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm");
        } else {
            return null;
        }
    }

    /**
     * 在当前时间基础上按固定字符串格式获取一个过去或者将来的字符串时间
     *
     * @param typecode 1=YYYY-MM-DD HH:MM:SS，2=YYYY-MM-DD，3=YYYYMMDD，4=YYYYMM，5=YYYY/MM/DD，6=DD/MM,7=YYYY-MM
     * @param timeType 时间运算单位信息【0=无运算、1=秒运算、2=分运算、3=时运算、4=天运算、5=月运算、6=年运算】
     * @param number   运算时间量【整数】
     * @return 格式化的时间字符串
     */
    public static String getFormatDate(Integer typecode, int timeType, int number) {
        /** 等到一个当前时间 */
        Calendar calendar = Calendar.getInstance();
        /** 计算过去或者将来时间 */
        if (timeType == 1) {
            calendar.add(Calendar.SECOND, number);// 增减秒数
        } else if (timeType == 2) {
            calendar.add(Calendar.MINUTE, number);// 增减分数
        } else if (timeType == 3) {
            calendar.add(Calendar.HOUR_OF_DAY, number);// 增减时数
        } else if (timeType == 4) {
            calendar.add(Calendar.DATE, number);// 增减天数
        } else if (timeType == 5) {
            calendar.add(Calendar.MONTH, number);// 增减月份
        } else if (timeType == 6) {
            calendar.add(Calendar.YEAR, number);// 增减年份
        } else if (timeType != 0) {
            return null;
        }
        if (typecode == 1) {
            return DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss");
        } else if (typecode == 2) {
            return DateFormatUtils.format(calendar, "yyyy-MM-dd");
        } else if (typecode == 3) {
            return DateFormatUtils.format(calendar, "yyyyMMdd");
        } else if (typecode == 4) {
            return DateFormatUtils.format(calendar, "yyyyMM");
        } else if (typecode == 5) {
            return DateFormatUtils.format(calendar, "yyyy/MM/dd");
        } else if (typecode == 6) {
            return DateFormatUtils.format(calendar, "dd/MM");
        } else if (typecode == 7) {
            return DateFormatUtils.format(calendar, "yyyy-MM");
        } else {
            return null;
        }
    }

    /**
     * 字符串时间格式转换日期时间格式
     *
     * @param dateTime 字符串时间（YYYY-MM-DD 或 YYYY-MM-DD HH24:MI:SS）
     * @return Calendar calendar 日期时间格式
     */
    public static Calendar getFormatDate(String dateTime) {
        Calendar calendar = Calendar.getInstance();
        if (dateTime.length() == 10) {
            String split[] = dateTime.split("-");
            calendar.set(Calendar.YEAR, Integer.parseInt(split[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(split[1]) - 1);
            calendar.set(Calendar.DATE, Integer.parseInt(split[2]));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } else if (dateTime.length() == 19) {
            String split[] = dateTime.replaceFirst(" ", "-").replaceAll(":", "-").split("-");
            calendar.set(Calendar.YEAR, Integer.parseInt(split[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(split[1]) - 1);
            calendar.set(Calendar.DATE, Integer.parseInt(split[2]));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[3]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(split[4]));
            calendar.set(Calendar.SECOND, Integer.parseInt(split[5]));
            calendar.set(Calendar.MILLISECOND, 0);
        } else {
            return null;
        }
        return calendar;
    }

    /**
     * 获取任意两个时间间隔秒杀
     *
     * @param startTime 起始时间 YYYY-MM-DD HH24:MI:SS
     * @param endTime   起始时间 YYYY-MM-DD HH24:MI:SS
     * @return long second 间隔秒数
     */
    public static long interval(String startTime, String endTime) {
        Calendar start = getFormatDate(startTime);
        Calendar end = getFormatDate(endTime);
        return (start.getTimeInMillis() - end.getTimeInMillis()) / 1000;
    }

    // ------------------------------------------------------------------------------------------------------------------

    /**
     * MD5加密算法
     *
     * @param souce=源字符串
     * @return String=MD5 加密的字符串
     */
    public static String encryptForMD5(String souce) {
        if (souce == null || souce.length() == 0) {
            System.err.println("警告 ： 空字符串不可以作MD5加密 !");
            return null;
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(souce.getBytes());
            byte hash[] = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("当请求特定的加密算法<MD5>而它在该环境中不可用时抛出此异常");
        }
        return null;
    }

    /**
     * 树形结构主键序号获取头部序号[三位一个层级]
     *
     * @param parentId=树形结构主键序号
     * @return String=树形结构主键序号头部序号
     */
    public static String getParentHead(String parentId) throws SYHCException {
        if (parentId.endsWith("000000000000000000000000000")) {
            return parentId.substring(0, 3);
        } else if (parentId.endsWith("000000000000000000000000")) {
            return parentId.substring(0, 6);
        } else if (parentId.endsWith("000000000000000000000")) {
            return parentId.substring(0, 9);
        } else if (parentId.endsWith("000000000000000000")) {
            return parentId.substring(0, 12);
        } else if (parentId.endsWith("000000000000000")) {
            return parentId.substring(0, 15);
        } else if (parentId.endsWith("000000000000")) {
            return parentId.substring(0, 18);
        } else if (parentId.endsWith("000000000")) {
            return parentId.substring(0, 21);
        } else if (parentId.endsWith("000000")) {
            return parentId.substring(0, 24);
        } else if (parentId.endsWith("000")) {
            return parentId.substring(0, 27);
        } else {
            throw new SYHCException(SYHCException.OPERATION_CODE_THRESHOLD_OVERFLOW, "子节点个数越界[999]");
        }
    }

    /**
     * 补全树形结构节点长度[三位一个层级]
     *
     * @param parentId=树形结构节点序号头部
     * @return String=树形结构节点完整序号
     */
    public static String completionNodeFormat(String parentId) {
        while (parentId.length() < 30) {
            parentId = parentId + "000";
        }
        return parentId;
    }

    /**
     * 字符串补位
     *
     * @param source 源字符串
     * @param size   目标字符串长度
     * @param gape   补位字符
     * @return String=树形结构节点完整序号
     */
    public static String fillGape(String source, int size, char gape) throws SYHCException {
        int length = source.length();
        if (length > size) {
            throw new SYHCException(SYHCException.OPERATION_CODE_THRESHOLD_OVERFLOW, "源字符串长度超过期望目标字符串长度");
        }
        char targetChar[] = new char[size];
        for (int i = 0; i < size; i++) {
            targetChar[i] = gape;
        }
        char sourceChar[] = source.toCharArray();
        for (int i = 1; i <= length; i++) {
            targetChar[size - i] = sourceChar[length - i];
        }
        String target = String.valueOf(targetChar);
        targetChar = null;
        sourceChar = null;
        return target;
    }

}

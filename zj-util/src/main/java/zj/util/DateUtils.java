package zj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date工具类 .
 *
 * @author lzj 2014年12月19日
 */

public class DateUtils {
    /**
     * yyyy-MM-dd HH:mm:ss .
     */
    public static final String FULLE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd .
     */
    public static final String STD_TIME = "yyyy-MM-dd";

    /**
     * 把string转换为date .
     *
     * @param date    表示日期的字符串
     * @param pattern 需要的日期格式
     */
    public static Date transStringToDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 把date转换为string .
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 指定格式的日期字符串
     */
    public static String transDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * get a date like yyyy-MM-dd HH:mm:ss .
     *
     * @param date the string represent the date to parse
     */
    public static Date getFullDate(String date) {
        return transStringToDate(date, FULLE_TIME);
    }

    /**
     * get a string like yyyy-MM-dd HH:mm:ss .
     *
     * @param date the date represent the date to parse
     */
    public static String getFullString(Date date) {
        return transDateToString(date, FULLE_TIME);
    }

}

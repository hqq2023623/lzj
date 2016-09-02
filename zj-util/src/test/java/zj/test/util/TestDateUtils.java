package zj.test.util;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import zj.util.DateUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * 测试DateUtils .
 *
 * @author lzj 2014年12月19日
 */
@RunWith(Theories.class)
public class TestDateUtils {

    @DataPoint
    public static final String FULL_DATE = "2015-02-17 12:43:11";
    @DataPoint
    public static final String FULL_DATE2 = "2015-02-15 12:43:11";
    @DataPoint
    public static final String FULL_DATE3 = "8765-43-21 12:43:11";
    @DataPoint
    public static final String FULL_DATE4 = DateUtils.getFullString(new Date());
    @DataPoint
    public static final String STD_DATE = "2015-02-17";
    @DataPoint
    public static final String STD_DATE2 = "2015-02-15";
    @DataPoint
    public static final String STD_DATE3 = "1234-56-78";

    /**
     * 测试yyyy-MM-dd HH:mm:ss .
     */
    @Theory
    public void testGetFullTime(String fullDate) {
        Pattern pattern = Pattern
                .compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(fullDate);
        // 排除不符合条件的值
        assumeTrue(matcher.matches());
        // 判断长度
        assertEquals(19, fullDate.length());
    }

    /**
     * 测试yyyy-MM-dd .
     */
    @Theory
    public void testGetStandardTime(String stdDate) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(stdDate);
        assumeTrue(matcher.matches());
        assertEquals(10, stdDate.length());
    }

}

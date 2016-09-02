package zj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类 .
 *
 * @author lzj 2015年1月14日
 */
public class RegexUtils {

    /**
     * 检测字符串是否符合正则表达式 .
     *
     * @param regex 正则表达式
     * @param input 用于匹配的字符串
     * @return 匹配结果
     */
    public static boolean checkMatch(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 获取所有匹配的子串 .
     *
     * @param regex 正则表达式
     * @param input 用于匹配的字符串
     * @return 所有匹配的子串
     */
    public static List<String> getMatchStrings(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }
}

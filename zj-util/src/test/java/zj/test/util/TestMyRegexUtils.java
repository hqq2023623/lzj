package zj.test.util;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import zj.util.RegexUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeTrue;

/**
 * 测试正则表达式工具类 .
 *
 * @author lzj 2015年1月16日
 */
@RunWith(Theories.class)
public class TestMyRegexUtils {

    @DataPoint
    public static final String input = "2149017240";
    @DataPoint
    public static final String input2 = "2149017240cxpoaur9023ura09sgd0dsa0901aoisfia2904aw a90s ua";
    @DataPoint
    public static final String input3 = "2zxczxc149017qweras2402143";
    @DataPoint
    public static final String regex = "\\d";
    @DataPoint
    public static final String regex2 = ".*";
    @DataPoint
    public static final String regex3 = "20149017240";

    /**
     * 测试checkMatch .
     */
    @Theory
    public void testCheckMatch(String regex, String input) {
        assumeTrue(RegexUtils.checkMatch(regex, input));
        assertFalse(RegexUtils.checkMatch(regex + "ax", input));
    }

    /**
     * 测试获取匹配的字符串 .
     */
    // @Theory
    // public void testGetMatchStrings() {
    // String regex = "901";
    // String input =
    // "2149017240cxzvipoaur9023ura09sgd0dsa0901aoisfia2904aw a90s ua";
    // List<String> matches = MyRegexUtils.getMatchStrings(regex, input);
    // for (String str : matches) {
    // assertEquals("901", str);
    // }
    // }

}

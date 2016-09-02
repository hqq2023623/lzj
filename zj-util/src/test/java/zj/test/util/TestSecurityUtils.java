package zj.test.util;

import org.junit.Test;
import zj.util.PasswdUtils;

import static org.junit.Assert.assertEquals;

/**
 * 测试MySecurityUtils .
 *
 * @author lzj 2015年1月16日
 */
public class TestSecurityUtils {

    /**
     * 使用MD5加密 .
     */
    @Test
    public void testMd5() {
        assertEquals("e10adc3949ba59abbe56e057f20f883e",
                PasswdUtils.md5("123456"));

        assertEquals("ca4f3ad0c7504681c2642a697012ecb1",
                PasswdUtils.md5("lzj", "m123"));
    }
}

package zj.test.util;

import org.junit.Test;
import zj.test.model.User;
import zj.util.ReflectionUtils;

/**
 * @author Lzj Created on 2015/12/18.
 */
public class TestReflectionUtils {

    @Test
    public void test01() {

        Class<User> clz = ReflectionUtils.getClz("zj.test.model", "User", true);
        User u = null;
        try {
            u = clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        u.setId(1L);
        u.setName("lzj");
        u.printAll();

    }


}

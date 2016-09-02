package zj.exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;
import zj.exceptions.StringManager;

import java.util.ResourceBundle;

/**
 * @author Lzj Created on 2015/12/18.
 */
public class TestStringManager {

    @Test
    public void test01() {

        StringManager manager = StringManager.getManager("zj.exceptions");
        StringManager manager2 = StringManager.getManager("zj.exceptions");
        assertTrue(manager == manager2);
        assertEquals("test msg", manager.getString("test.msg"));

    }



}

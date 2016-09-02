package zj.test.util;

import org.junit.Test;
import zj.util.PropUtils;

import java.util.Map;
import java.util.Properties;

/**
 * 测试MyPropertiesUtils .
 *
 * @author lzj 2015年1月16日
 */
public class TestMyPropertiesUtils {

    private Map<String, String> map;

    /**
     * 测试读取数据 .
     */
    @Test
    public void testRead() {
        Properties props = PropUtils.getDefaultProperties();
        System.out.println(props.get("pageSize"));
        map = PropUtils.getAllProps(props);
        printMap(map);
    }

    private void printMap(Map<String, String> map) {
        for (String key : map.keySet()) {
            System.out.println("key : " + key + "--value : " + map.get(key));
        }
    }

}

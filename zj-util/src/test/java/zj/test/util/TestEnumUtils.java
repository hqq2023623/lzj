package zj.test.util;

import org.junit.Test;
import zj.test.model.TestEnumModel;
import zj.util.EnumUtils;

import java.util.List;
import java.util.Map;

/**
 * 测试枚举工具类 .
 *
 * @author lzj 2015年1月16日
 */
public class TestEnumUtils {

    /**
     * 测试将枚举中的值转换为一组序数列表 .
     */
    @Test
    public void testEnum2Ordinal() {
        List<Integer> list = EnumUtils.enum2Ordinal(TestEnumModel.class);
        for (Integer i : list) {
            System.out.println(i);
        }
    }

    /**
     * 测将枚举中的值转换为相应的名称字符串列表 .
     */
    @Test
    public void testEnum2Name() {
        List<String> list = EnumUtils.enum2Name(TestEnumModel.class);
        for (String i : list) {
            System.out.println(i);
        }
    }

    /**
     * 将枚举中的值转换为序号和名称的map .
     */
    @Test
    public void testEum2BasicMap() {
        Map<Integer, String> map = EnumUtils.enum2BasicMap(TestEnumModel.class);
        for (Integer key : map.keySet()) {
            System.out.println(map.get(key));
        }
    }

    /**
     * 将枚举中的值的某个属性转换为字符串列表 .
     */
    @Test
    public void testEnumProp2List() {
        List<String> list = EnumUtils.enumProp2List(TestEnumModel.class, "color");
        for (String i : list) {
            System.out.println(i);
        }
    }

    /**
     * 将枚举中的值的某个属性转换为序号和字符串列表 .
     */
    @Test
    public void testEnumProp2OrdinalMap() {
        Map<Integer, String> map = EnumUtils.enumProp2OrdinalMap(
                TestEnumModel.class, "color");
        for (Integer key : map.keySet()) {
            System.out.println(map.get(key));
        }
    }

    /**
     * 将枚举中的值的某个属性转换为序号和字符串列表 .
     */
    @Test
    public void testEnumProp2NameMap() {
        Map<String, String> map = EnumUtils.enumProp2NameMap(TestEnumModel.class,
                "color");
        for (String key : map.keySet()) {
            System.out.println(map.get(key));
        }
    }

}

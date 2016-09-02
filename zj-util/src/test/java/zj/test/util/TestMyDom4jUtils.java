package zj.test.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.junit.BeforeClass;
import org.junit.Test;
import zj.util.Dom4jUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试Dom4jUtils .
 *
 * @author lzj 2015年1月30日
 */
public class TestMyDom4jUtils {

    private static Document doc;

    @BeforeClass
    public static void setUpClass() {
        doc = Dom4jUtils.getClasspathDocument("dom4j.xml");
    }

    /**
     * 测试输出内容到控制台 .
     */
    @Test
    public void testPrint() {
        Dom4jUtils.print(doc.getRootElement());
    }

    /**
     * 测试xpath查询 .
     */
    @Test
    public void testXpath() {
        List<String> list = Dom4jUtils.getTexts(doc, "/class/student/age");
        list.forEach(System.out::println);
    }

    /**
     * 测试创建xml节点 .
     */
    @Test
    public void testCreateElement() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("age", "20");
        attributes.put("username", "lzj");
        Element ele = Dom4jUtils.createElement("student", attributes,
                new Namespace(null, "http://www.zj.com"));
        System.out.println(Dom4jUtils.nodeToString(ele));
    }

    /**
     * 测试写入到文件 .
     */
    @Test
    public void testWriteToFile() {
        Element root = doc.getRootElement();
        Element student = Dom4jUtils.createElement("student", null, null);
        root.add(student);
        Dom4jUtils.writeToFile(doc, "c:/123.xml");
    }

    /**
     * 生成只带xml声明的xml文件 .
     */
    @Test
    public void testA() {
        Dom4jUtils.createEmptyDocument("c:/23532.xml");
    }

}

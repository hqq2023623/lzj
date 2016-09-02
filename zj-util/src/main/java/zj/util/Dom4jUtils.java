package zj.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Dom4j工具类 .
 *
 * @author lzj 2015年1月30日
 */
public class Dom4jUtils {

    /**
     * 获取指定xml文件的document对象 .
     *
     * @param file xml文件
     */
    private static Document readDocumentBySaxReader(File file) {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 在指定路径下生成一个新的document .
     *
     * @param path 路径
     */
    public static Document createEmptyDocument(String path) {
        File file = new File(path);
        try {
            // 生成新文件
            FileOutputStream os = new FileOutputStream(file);
            os.write("<xml encoding=\"UTF-8\" version=\"1.0\"></xml>".getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = readDocumentBySaxReader(file);
        return doc;
    }

    /**
     * 获取指定路径的xml文件document .
     *
     * @param path xml文件路径
     */
    public static Document getFileSystemDocument(String path) {
        Document doc = readDocumentBySaxReader(new File(path));
        return doc;
    }

    /**
     * 获取类路径下(src/main/resources)的xml文件document .
     *
     * @param path xml文件路径。如 根路径下 : "123.xml"、 指定文件夹下面:"/xml/123.xml" 当前包下面:
     *             "/123.xml"
     */
    public static Document getClasspathDocument(String path) {
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(Dom4jUtils.class.getClassLoader().getResourceAsStream(path));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

    //

    /**
     * 遍历输出节点的内容到控制台 .
     *
     * @param element 指定节点,如为跟节点则输出整个xml文件的内容(不包括属性)
     */
    @SuppressWarnings("rawtypes")
    public static void print(Element element) {
        System.out.println(element.getName() + ": " + element.getTextTrim());
        // 用Element的element()方法得到子节点
        Iterator it = element.elementIterator();
        while (it.hasNext()) {
            Element ele = (Element) it.next();
            // 递归
            print(ele);
        }
    }

    /**
     * 将xml Node 对象转换成 String .
     *
     * @param node
     * @return string
     */
    public static String nodeToString(Node node) {
        return node.asXML();
    }

    /**
     * 将字符串解析成Node .
     *
     * @param xmlString xml格式字符串
     * @return node
     */
    public static Node stringToNode(String xmlString) {
        Node node = null;
        try {
            node = DocumentHelper.parseText(xmlString);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return node;
    }

    /**
     * 使用xPath快速找到目标节点 .
     *
     * @param document
     * @param str      xPath路径。如("/lzj/age")
     */
    @SuppressWarnings("rawtypes")
    public static List<String> getTexts(Document document, String str) {
        List<String> list = new ArrayList<String>();
        List nodes = document.selectNodes(str);
        Iterator it = nodes.iterator();
        Element ele;
        while (it.hasNext()) {
            ele = (Element) it.next();
            list.add(ele.getText());
        }
        return list;
    }

    /**
     * 创建一个xml 元素 .
     *
     * @param name       xml 元素的名称
     * @param attributes 元素属性
     * @param ns         命名空间
     */
    public static Element createElement(String name, Map<String, String> attributes, Namespace ns) {
        Element element = null;
        if (ns == null) {
            element = DocumentHelper.createElement(name);
        } else {
            element = DocumentHelper.createElement(new QName(name, ns));
        }
        if (attributes != null && !attributes.isEmpty()) {
            for (String key : attributes.keySet()) {
                element.addAttribute(key, attributes.get(key));
            }
        }
        return element;
    }

    /**
     * 将document 对象写入指定的文件 .
     *
     * @param doc      指定的document对象
     * @param filePath 文件路径
     */
    public static void writeToFile(Document doc, String filePath) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

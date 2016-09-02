package zj.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 操作.properties文件工具类 .
 *
 * @author lzj 2014-10-3
 */
public class PropUtils {
    /**
     * 默认配置文件的名称 .
     */
    private static final String DEFAULT_PROP = "params.properties";

    /**
     * 获取params.properties
     */
    public static Properties getDefaultProperties() {
        return getClassPathProperties(DEFAULT_PROP);
    }

    /**
     * 获取类路径下的prop,并完成输入流的读入 .
     */
    public static Properties getClassPathProperties(String path) {
        Properties props = null;
        InputStream is = new BufferedInputStream(PropUtils.class
                .getClassLoader().getResourceAsStream(path));
        try {
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * 获取指定路径下的prop,并完成输入流的读入 .
     */
    public static Properties getFileSystemProperties(String path) {
        Properties props = null;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(new File(
                    path)));
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * 读取properties文件的全部信息 .
     *
     * @return 转换为map的props
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> getAllProps(Properties props) {
        Map<String, String> datas = new HashMap<String, String>();
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String value = props.getProperty(key);
            datas.put(key, value);
        }
        return datas;
    }

    /**
     * 根据key读取value .
     */
    public static String getValue(Properties props, String key) {
        return props.getProperty(key);
    }

    /**
     * 写入properties信息 .
     */
    public static void setValue(Properties props, String path, String key,
                                String value) {
        if (path == null) {
            // 路径不能有中文
            try (OutputStream out = new FileOutputStream(PropUtils.class
                    .getClassLoader().getResource(DEFAULT_PROP).getPath())) {
                props.setProperty(key, value);
                props.store(out, key);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

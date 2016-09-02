package zj.util;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * 获取项目路径的方法
 * Created by Administrator on 2016/6/6.
 */
public class ProjectPathUtils {

    //webapp
    public static final String WEB_APP = System.getProperty("user.dir")
            + "/src/main/webapp";
    //java目录
    public static final String JAVA_PATH = System.getProperty("user.dir")
            + "/src/main/java";
    //class文件目录
    public static final String CLASS_PATH = System.getProperty("user.dir")
            + "/target/classes";

    //test-class文件目录
    public static final String TEST_CLASS_PATH = System.getProperty("user.dir")
            + "/target/test-classes";


    public static String rootByServletRequest(HttpServletRequest request) {
        return request.getServletContext().getContextPath();
    }

    /**
     * 获取指定文件的inputStream
     *
     * @param request
     * @param fileName /WEB-INF/xxx 、 /WEB-INF/classes/xxx
     * @return
     */
    public static InputStream streamByRequest(HttpServletRequest request, String fileName) {
        return request.getServletContext().getResourceAsStream(fileName);
    }


}

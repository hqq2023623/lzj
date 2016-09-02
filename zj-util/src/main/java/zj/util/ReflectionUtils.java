package zj.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzj
 * @date 2015/12/9.
 */
public class ReflectionUtils {

    /**
     * 根据类的包名和类名获取test文件下的类的Class对象
     *
     * @param pkgPath   zj.a
     * @param className 类名
     * @param isTest    是否是test文件夹下的类
     */
    public static Class getClz(String pkgPath, String className, boolean isTest) {
        String filePath = pkgPath.replace(".", "/");
        URLClassLoader loader = null;
        try {
            // create a URLClassLoader
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath;
            if (isTest) {
                classPath = new File(Constants.TEST_CLASS_PATH
                        + filePath);
            } else {
                classPath = new File(Constants.JAVA_PATH
                        + filePath);
            }
            String repository = (new URL("file", null,
                    classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class clz = null;
        try {
            clz = loader.loadClass(pkgPath + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clz;
    }

    /**
     * 获取包下的所有类名
     *
     * @param pkg 包名
     * @return 所有类名
     */
    public static List<String> getClassNamesByPkg(String pkg) {
        List<String> result = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = pkg.replace(".", "/");
        URL url = loader.getResource(packagePath);
        File dir = new File(url.getPath());
        for (File f : dir.listFiles()) {
            result.add(f.getName().replace(".class", ""));
        }
        return result;
    }

    /**
     * 获取全路径
     *
     * @param pkg 包名
     * @return 全路径--com.zj.test.model.util.Xxx
     */
    public static List<String> getFullPathByPkg(String pkg) {
        List<String> result = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = pkg.replace(".", "/");
        URL url = loader.getResource(packagePath);
        File dir = new File(url.getPath());
        StringBuilder str = new StringBuilder();
        for (File f : dir.listFiles()) {
            str.append(pkg).append(".");
            str.append(f.getName().replace(".class", ""));
            result.add(str.toString());
            str.delete(0, str.length());
        }
        return result;
    }


}

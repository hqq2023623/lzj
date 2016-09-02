package zj.util;

import java.io.File;

/**
 * @author Lzj Created on 2015/12/18.
 */
public class FileUtils {

    /**
     * 从resources文件夹中获取文件
     *
     * @param fileName 从根路径开始的全名 /a/b.txt
     */
    public static File getFileFromResources(String fileName) {
        String filePath = FileUtils.class.getClassLoader().getResource("").getPath() + fileName;
        File f = new File(filePath);
        return f;
    }


}

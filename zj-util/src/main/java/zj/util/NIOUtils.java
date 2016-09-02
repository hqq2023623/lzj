package zj.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by lzj on 2016/6/1.
 */
public class NIOUtils {

    /**
     * 按行读取指定文件
     *
     * @param filePath /home/123.txt || c:/123.txt
     * @return null if file not found
     */
    public static List<String> readFileByLines(String filePath) {
        Path path = new File(filePath).toPath();
        return readFileByLines(path);
    }

    /**
     * 按行读取指定文件
     *
     * @param file
     * @return null if file not found
     */
    public static List<String> readFileByLines(File file) {
        return readFileByLines(file.toPath());
    }

    /**
     * 按行读取指定文件
     * @param path
     * @return null if file not found
     */
    private static List<String> readFileByLines(Path path) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path,
                    Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


}

package zj.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson工具类
 *
 * @author lzj
 * @date 2015年7月24日
 */

public class GsonUtils {

    private static final GsonBuilder builder = new GsonBuilder();

    public static Gson getGson() {
        return builder.create();
    }

    public static Gson getExcludeGson() {
        return builder.excludeFieldsWithoutExposeAnnotation().create();
    }

    public static GsonBuilder getGsonBuilder() {
        return builder;
    }

}

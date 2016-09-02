package zj.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

/**
 * 处理json .
 *
 * @author lzj 2014/08/19
 */
public class JacksonUtils {

    private static JsonFactory jsonFactory;
    private static ObjectMapper mapper;

    /**
     * 把object转换为json .
     *
     * @param obj 需要转换的对象
     * @return json字符串
     */
    public static String obj2Json(Object obj) {
        JsonGenerator jsonGenerator = null;
        try {
            jsonFactory = getFactory();
            mapper = getMapper();
            StringWriter out = new StringWriter();
            jsonGenerator = jsonFactory.createGenerator(out);
            mapper.writeValue(jsonGenerator, obj);
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeJsonGenerator(jsonGenerator);
        }
        return null;
    }

    /**
     * 把json转换为object .
     *
     * @param json 需要转换的json字符串
     * @param clz  相应对象的class
     */
    public static Object json2Obj(String json, Class<?> clz) {
        try {
            mapper = getMapper();
            return mapper.readValue(json, clz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把Collection转换为json .
     */
    public static String collection2Json(Collection<Object> collection) {
        JsonGenerator jsonGenerator = null;
        try {
            jsonFactory = getFactory();
            mapper = getMapper();
            StringWriter out = new StringWriter();
            jsonGenerator = jsonFactory.createGenerator(out);
            mapper.writeValue(jsonGenerator, collection);
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeJsonGenerator(jsonGenerator);
        }
        return null;
    }

    /**
     * 把json转换为Map .
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String jsonString) {
        mapper = getMapper();
        Map<String, Object> result;
        try {
            result = (Map<String, Object>) mapper.readValue(jsonString, Map.class);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓私有方法↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 获取ObjectMapper .
     */
    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            // 设置日期转换格式
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            mapper.setDateFormat(df);
        }
        return mapper;
    }

    /**
     * 获取JsonFactory .
     */
    private static JsonFactory getFactory() {
        if (jsonFactory == null) {
            jsonFactory = new JsonFactory();
        }
        return jsonFactory;
    }

    /**
     * 关闭JsonGenerator .
     */
    private static void closeJsonGenerator(JsonGenerator jsonGenerator) {
        if (jsonGenerator != null) {
            try {
                jsonGenerator.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

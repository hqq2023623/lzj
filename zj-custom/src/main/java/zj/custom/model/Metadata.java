package zj.custom.model;

import java.util.*;

/**
 * 元数据
 *
 * @author RD_haitao_ou
 */

/**
 *
 */
public class Metadata {

    // 元数据名称
    private String name;

    // 元数据ID
    private String key;

    // 元数据表名
    private String tableName;

    // 主键字段的code
    private String pkField;

    // 主键字段的key
    private String pkKey;

    // 字段列表
    private Map<String, Field> fieldList;

    // 关联表列表
    private Map<String, Entry> entryList;

    // 字段code和key的映射
    private Map<String, String> code2Key;

    public String getPkKey() {
        return pkKey;
    }

    public void setPkKey(String pkKey) {
        this.pkKey = pkKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPkField() {
        return pkField;
    }

    public void setPkField(String pkField) {
        this.pkField = pkField;
    }

    public Map<String, Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(Map<String, Field> fieldList) {
        this.fieldList = fieldList;
    }

    public Map<String, Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(Map<String, Entry> entryList) {
        this.entryList = entryList;
    }

    public Map<String, String> getCode2Key() {
        return code2Key;
    }

    public void setCode2Key(Map<String, String> code2Key) {
        this.code2Key = code2Key;
    }

    /**
     * 创建metadata
     *
     * @param dynamicObject
     * @return
     */
    public final static Metadata createMetaData(DynamicModel dynamicObject) {
        Metadata metadata = null;
        if (dynamicObject != null) {
            metadata = new Metadata();
            metadata.name = dynamicObject.getString("fname");
            metadata.key = dynamicObject.getString("fkey");
            metadata.tableName = dynamicObject.getString("ftablename");
        }
        return metadata;
    }

    /**
     * 关联字段
     *
     * @param fieldsObjects
     */
    public final void addField(List<DynamicModel> fieldsObjects) {
        if (fieldsObjects != null && fieldsObjects.size() > 0) {
            Field field;
            Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
            Map<String, String> c2k = new HashMap<>();
            for (DynamicModel dynamicObject : fieldsObjects) {
                field = Field.createField(dynamicObject);
                if (field.isPrimary()) {
                    this.setPkField(field.getCode());
                    this.setPkKey(field.getKey());
                }
                fieldMap.put(field.getKey(), field);
                c2k.put(field.getCode(), field.getKey());
            }
            this.setFieldList(fieldMap);
            this.setCode2Key(c2k);
        }
    }

    /**
     * @param entryList
     */
    public final void addEntry(List<DynamicModel> entryList) {
        if (entryList != null && entryList.size() > 0) {
            Entry entry;
            Map<String, Entry> fieldMap = new LinkedHashMap<String, Entry>();
            for (DynamicModel dynamicObject : entryList) {
                entry = Entry.createEntry(dynamicObject);
                /*
				 * if (entry.isPrimary()) { this.setPkField(entry.getCode()); }
				 */
                fieldMap.put(entry.getKey(), entry);
            }
            this.setEntryList(fieldMap);
        }
    }

    /**
     * 获取主键之外的字段集合
     *
     * @return
     */
    public Map<String, Field> getFieldsExceptPK() {
        Map<String, Field> resultMaps = new LinkedHashMap<String, Field>(
                this.getFieldList());
        if (resultMaps != null)
            resultMaps.remove(this.getPkField());
        return resultMaps;
    }

    /**
     * 获取列组合集
     *
     * @param metaData
     * @return "FID,FNAME,FVALUE"格式
     */
    public static String getColumns(Metadata metaData) {
        // 存放列组合字符集
        StringBuffer columns = new StringBuffer();
        // 动态数据集的key集合
        Set<String> dKeySet = metaData.getFieldList().keySet();
		/* dKeyList = new ArrayList<String>(dKeySet); */
        // 目标数据库表的字段
        Map<String, Field> fields = metaData.getFieldsExceptPK();
        Iterator<String> ite = dKeySet.iterator();
        // 获取field对象中的字段code并加入
        while (ite.hasNext()) {
            String key = ite.next();
            Field field = fields.get(key);
            columns.append(field.getCode() + ",");
        }
        // 去除最后一个逗号“,”
        return columns.substring(0, columns.lastIndexOf(","));
    }

    /**
     * 获取命名参数形式的占位值集
     *
     * @return ":id,:name,:value"格式
     */
    public static String getNamedValues(Metadata metaData) {
        StringBuffer namedValues = new StringBuffer();
        for (String key : metaData.getFieldList().keySet()) {
            namedValues.append(":" + key + ",");
        }
        return namedValues.substring(0, namedValues.lastIndexOf(","));
    }
}

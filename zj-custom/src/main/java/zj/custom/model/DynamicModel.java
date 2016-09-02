package zj.custom.model;

import zj.custom.jdk.collection.CaseInsensitiveLinkedMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 一个map,用于从数据库中查处字段不固定的数据<br/>
 *
 * @author Lzj Created on 2015/12/18.
 */
public class DynamicModel extends CaseInsensitiveLinkedMap<Object> {

    public DynamicModel() {
    }

    public DynamicModel(int initialCapacity) {
        super(initialCapacity);
    }

    public long getLong(String key) {
        try {
            return Long.parseLong((String) this.get(key));
        } catch (Exception e) {
            return 0;
        }
    }

    public double getDouble(String key) {
        try {
            return Double.valueOf(this.get(key).toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public Date getDate(String key) {
        try {
            return (Date) this.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public int getInt(String key) {
        try {
            return Integer.parseInt(this.get(key).toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public String getString(String key) {
        try {
            return this.get(key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public List<DynamicModel> getList(String key) {
        try {
            return (List<DynamicModel>) this.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public Map getMap(String key) {
        try {
            return (Map) this.get(key);
        } catch (Exception e) {
            return null;
        }
    }




}

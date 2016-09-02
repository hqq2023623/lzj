package zj.custom.jdk.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 参照 {@link org.springframework.util.LinkedCaseInsensitiveMap}实现忽略大小写的map<br/>
 * 有一个成员变量Map caseInsensitiveKeys，小写后的Key与真实key的映射关系，get的时候先把你传入的key转小写得到真实的
 * KEY，然后得到对应value，所以大小写不敏感了。
 * 但其实key还保持本来的，所以如果序列化为json或者xml格式，key是大写，这是需要注意的。
 *
 * @author Lzj Created on 2015/12/18.
 */
public class CaseInsensitiveLinkedMap<V> extends LinkedHashMap<String, V> {

    //放的是小写的key和真实的key的映射, lowerCase->upperCase
    private final Map<String, String> caseInsensitiveKeys;

    //创建默认大小的map,1 << 4; // aka 16,自动增加容量的因子是0.75f
    public CaseInsensitiveLinkedMap() {
        super();
        this.caseInsensitiveKeys = new HashMap<String, String>();
    }

    //创建指定容量大小的map
    public CaseInsensitiveLinkedMap(int initialCapacity) {
        super(initialCapacity);
        this.caseInsensitiveKeys = new HashMap<String, String>(initialCapacity);
    }

    @Override
    public V put(String key, V value) {
        //存入大小写映射,key为小写,value为传入的key,返回的是lowerCase
        String oldKey = this.caseInsensitiveKeys.put(convertKey(key), key);
        //多次put相同key的情况下caseInsensitiveKeys存储的只有最新的key(User),而此时值map里面则会有两个不同的key(USER和User)
        if (oldKey != null && !oldKey.equals(key)) {
            super.remove(oldKey);
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        if (map.isEmpty()) {
            return;
        }
        for (Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return (key instanceof String && this.caseInsensitiveKeys.containsKey(convertKey((String) key)));
    }

    @Override
    public V get(Object key) {
        if (key instanceof String) {
            return super.get(this.caseInsensitiveKeys.get(convertKey((String) key)));
        } else {
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        if (key instanceof String) {
            return super.remove(this.caseInsensitiveKeys.remove(convertKey((String) key)));
        } else {
            return null;
        }
    }

    //除了清空map中的数据外也要清空映射的map
    @Override
    public void clear() {
        this.caseInsensitiveKeys.clear();
        super.clear();
    }

    /**
     * 把传入的key转成小写返回
     *
     * @param key 用户指定的key
     * @return 转成小写后存入caseInsensitiveKeys的key
     */
    protected String convertKey(String key) {
        return key.toLowerCase();
    }


}

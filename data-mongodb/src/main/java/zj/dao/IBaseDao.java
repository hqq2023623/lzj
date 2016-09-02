package zj.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
public interface IBaseDao<T> {

    /**
     * 获取查询数据的数量
     */
    public Long findCount(Query query);

    /**
     * 查询数据
     */
    public List<T> find(Query query);

    /**
     * 分页查询数据
     */
    public List<T> findList(int start, int limit);

    /**
     * 根据id获取单个数据
     */
    public T findOne(String id);

    /**
     * 插入一条数据
     */
    public void insert(T entity);

    /**
     * 更新数据
     */
    public void update(T entity) throws Exception;

    /**
     * 更新数据
     */
    public void update(Query query, Update update);

    /**
     * 删除数据
     */
    public void remove(T entity);


}

package zj.base.dao;


import zj.base.common.Pager;
import zj.base.common.QueryCondition;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 公共的DAO处理对象，这个对象中包含了Hibernate的所有基本操作和对SQL的操作
 *
 * @author lzj 2014/08/19
 */
public interface IBaseDao<T, ID extends Serializable> {

    // 判断是否存在
    boolean isExist(String propertyName, Object value);

    /**
     * 刷新session.
     */
    public void flush();

    /**
     * 清除Session.
     */
    public void clear();

    /**
     * 获取记录总数
     */
    public Long getTotalCount();

    /**
     * 添加对象的时候返回该对象，因为有可能用它做点事情
     */
    T save(T t);

    /**
     * 更新对象
     */
    void update(T t);

    /**
     * 根据id删除对象
     */
    void delete(ID id);

    void delete(T entity);

    /**
     * 根据ID数组删除实体对象.
     *
     * @param ids ID数组
     */
    int delete(ID[] ids);

    /**
     * 根据id加载对象
     */
    T load(ID id);

    T get(ID id);

    T get(String propertyName, Object value);

    List<T> load(ID[] ids);

    /**
     * 根据属性名和属性值获取实体对象.
     *
     * @param propertyName 属性名称
     * @param value        属性值
     * @return 实体对象
     */
    T loadByParam(String propertyName, Object value);

    /**
     * 根据属性名和属性值获取实体对象集合.
     *
     * @param propertyName 属性名称
     * @param value        属性值
     * @return 实体对象集合
     */
    List<T> listByParam(String propertyName, Object value);

    /**
     * 查询所有数据
     */
    List<T> listAll();

    Pager<T> findAll();

    /**
     * 不分页列表对象
     *
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return 一组不分页的列表对象
     */

    List<T> list(StringBuilder hql, Object[] args);

    List<T> list(StringBuilder hql, Object arg);

    List<T> list(StringBuilder hql);

    /**
     * 基于别名和查询参数的混合列表对象
     *
     * @param hql
     * @param args
     * @param alias
     * @return 别名: from User where username=:username and password = ?
     * setParameter("username","lzj");
     */

    List<T> list(StringBuilder hql, Object[] args, Map<String, Object> alias);

    List<T> listByAlias(StringBuilder hql, Map<String, Object> alias);

    /**
     * 分页列表对象,用find()来查询
     *
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return 一组不分页的列表对象
     */

    Pager<T> find(StringBuilder hql, Object[] args);

    Pager<T> find(StringBuilder hql, Object arg);

    Pager<T> find(StringBuilder hql);

    /**
     * 基于别名和查询参数的混合列表对象
     *
     * @param hql
     * @param args
     * @param alias
     * @return
     */

    Pager<T> find(StringBuilder hql, Object[] args, Map<String, Object> alias);

    Pager<T> findByAlias(StringBuilder hql, Map<String, Object> alias);

    /**
     * 根据hql查询一组对象
     *
     * @param hql
     * @param args
     * @return
     */

    Object queryObject(StringBuilder hql, Object[] args, Map<String, Object> alias);

    Object queryObjectByAlias(StringBuilder hql, Map<String, Object> alias);

    Object queryObject(StringBuilder hql, Object[] args);

    Object queryObject(StringBuilder hql, Object arg);

    Object queryObject(StringBuilder hql);

    /**
     * 根据hql更新对象
     *
     * @param hql
     * @param args
     */

    int updateByHql(StringBuilder hql, Object[] args);

    int updateByHql(StringBuilder hql, Object arg);

    int updateByHql(StringBuilder hql);

    /**
     * 根据原生态sql查询对象,不包含关联对象
     *
     * @param sql
     * @param args      查询条件
     * @param clz       查询的实体对象
     * @param hasEntity 是否被hibernate管理,如果不是就要使用setResultTransform查询
     * @return 一组对象
     */

    <N extends Object> List<N> listBySql(StringBuilder sql, Object[] args, Class<?> clz, boolean hasEntity);

    <N extends Object> List<N> listBySql(StringBuilder sql, Object arg, Class<?> clz, boolean hasEntity);

    <N extends Object> List<N> listBySql(StringBuilder sql, Class<?> clz, boolean hasEntity);

    <N extends Object> List<N> listBySql(StringBuilder sql, Object[] args, Map<String, Object> alias, Class<?> clz,
                                         boolean hasEntity);

    <N extends Object> List<N> listByAliasSql(StringBuilder sql, Map<String, Object> alias, Class<?> clz,
                                              boolean hasEntity);

    /**
     * 带分页的根据原生态sql查询对象,不包含关联对象
     *
     * @param sql
     * @param args      查询条件
     * @param clz       查询的实体对象
     * @param hasEntity 是否被hibernate管理,如果不是就要使用setResultTransform查询
     * @return 一组对象
     */

    <N extends Object> Pager<N> findBySql(StringBuilder sql, Object[] args, Class<?> clz, boolean hasEntity);

    <N extends Object> Pager<N> findBySql(StringBuilder sql, Object arg, Class<?> clz, boolean hasEntity);

    <N extends Object> Pager<N> findBySql(StringBuilder sql, Class<?> clz, boolean hasEntity);

    <N extends Object> Pager<N> findBySql(StringBuilder sql, Object[] args, Map<String, Object> alias, Class<?> clz,
                                          boolean hasEntity);

    <N extends Object> Pager<N> findByAliasSql(StringBuilder sql, Map<String, Object> alias, Class<?> clz,
                                               boolean hasEntity);

    /**
     * 多条件不分页查询
     */
    List<T> listByConditions(List<QueryCondition> conditions);

    /**
     * 多条件不分页查询
     */
    Pager<T> findByConditions(List<QueryCondition> conditions);

}

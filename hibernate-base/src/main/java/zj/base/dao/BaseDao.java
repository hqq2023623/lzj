package zj.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zj.base.common.Pager;
import zj.base.common.QueryCondition;
import zj.base.common.SystemContext;


/**
 * @author lzj 2014/08/19
 */
@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDao<T, ID extends Serializable> implements IBaseDao<T, ID> {

    /**
     * Dao只做关于数据库的处理
     */
    @Autowired
    private SessionFactory sessionFactory;
    /**
     * 创建一个Class的对象来获取泛型的class
     */
    private Class<?> clz;

    public Class<?> getClz() {
        if (clz == null) {
            // 获取泛型的Class对象
            clz = ((Class<?>) (((ParameterizedType) (this.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[0]));
        }
        return clz;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void clear() {
        getSession().clear();
    }

    @Override
    public Long getTotalCount() {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ").append(getClz().getName());
        return (Long) queryObject(sb);
    }

    @Override
    public T save(T t) {
        getSession().save(t);
        return t;
    }

    @Override
    public void update(T t) {
        getSession().update(t);
    }

    @Override
    public void delete(ID id) {
        getSession().delete(this.load(id));
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public int delete(ID[] ids) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete ").append(getClz().getName()).append(" o where o.id in ?1");
        return updateByHql(sb, ids);
    }

    @Override
    public T load(ID id) {
        return (T) getSession().load(getClz(), id);
    }

    @Override
    public List<T> load(ID[] ids) {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(getClz().getName()).append("o where o.id in :ids");
        return getSession().createQuery(sb.toString()).setParameter("ids", ids).list();
    }

    @Override
    public T get(ID id) {
        return (T) getSession().get(getClz(), id);
    }

    @Override
    public T loadByParam(String propertyName, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(getClz().getName()).append("o where o.").append(propertyName).append("= ?1");
        return (T) queryObject(sb, value);
    }

    @Override
    public List<T> listByParam(String propertyName, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(getClz().getName()).append("o where o.").append(propertyName).append("= ?1 order by createDate");
        return list(sb, value);
    }

    @Override
    public List<T> listAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(getClz().getName());
        return list(sb);
    }

    @Override
    public Pager<T> findAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(getClz().getName());
        return find(sb);
    }

    // *******************list列表对象不分页 +别名和查询参数的查询 开始********
    // 初始化排序
    private StringBuilder initSort(StringBuilder hql) {
        String order = SystemContext.getOrder();
        String sort = SystemContext.getSort();
        if (sort != null && !sort.isEmpty()) {
            hql.append(" order by ").append(sort);
            if (!"desc".equals(order))
                hql.append(" asc");
            else
                hql.append(" desc");
        }
        return hql;
    }

    /**
     * 多条件不分页查询
     */
    @Override
    public List<T> listByConditions(List<QueryCondition> conditions) {
        StringBuilder hql = new StringBuilder();
        hql.append("from ").append(getClz().getName()).append(" o where");
        for (QueryCondition con : conditions) {
            hql.append(" o.").append(con.getKey()).append(con.getOperator()).append(":").append(con.getKey()).append(" ").append(con.getLogicOperator());
        }
        hql = initSort(hql);
        Query query = getSession().createQuery(hql.toString());
        setConditionParameter(query, conditions);
        return query.setCacheable(true).list();
    }

    @Override
    public Pager<T> findByConditions(List<QueryCondition> conditions) {
        StringBuilder hql = new StringBuilder();
        hql.append("from ").append(getClz().getName()).append(" o where 1=1 and");
        for (QueryCondition con : conditions) {
            hql.append(" o.").append(con.getKey()).append(" ").append(con.getOperator()).append(":").append(con.getKey()).append(" ").append(con.getLogicOperator());
        }
        hql = initSort(hql);
        String cq = getCountHql(hql, true);
        Query cquery = getSession().createQuery(cq);
        Query query = getSession().createQuery(hql.toString());
        // 设置别名参数
        setConditionParameter(query, conditions);
        setConditionParameter(cquery, conditions);
        // 设置分页量化数据
        Pager<T> pages = new Pager<>();
        setPagers(query, pages);
        List<T> datas = query.setCacheable(true).list();
        pages.setRows(datas);
        long total = (Long) cquery.uniqueResult();
        pages.setTotal(total);
        return pages;
    }

    // 设置有多条件查询的值
    private void setConditionParameter(Query query, List<QueryCondition> conditions) {
        for (QueryCondition con : conditions) {
            query.setParameter(con.getKey(), con.getValue());
        }
    }

    // 设置有别名的查询参数和多条件查询 用(:xxx)
    @SuppressWarnings("rawtypes")
    private void setAliasParameter(Query query, Map<String, Object> collection) {
        if (collection != null) {
            Set<String> keys = collection.keySet();
            for (String key : keys) {
                Object val = collection.get(key);
                if (val instanceof Collection) {
                    // 查询条件是列表
                    query.setParameterList(key, (Collection) val);
                } else {
                    query.setParameter(key, val);
                }
            }
        }
    }

    // 设置查询用(?)参数
    private void setParameter(Query query, Object[] args) {
        if (args != null && args.length > 0) {
            StringBuilder sb;
            for (int i = 0; i < args.length; i++) {
                sb = new StringBuilder();
                if (i == 0) {
                    query.setParameter(sb.append(i + 1).toString(), args[i]);
                } else {
                    query.setParameter(sb.append(i).toString(), args[i]);
                }
            }
        }
    }

    public List<T> list(StringBuilder hql, Object[] args) {
        return this.list(hql, args, null);
    }

    public List<T> list(StringBuilder hql, Object arg) {
        return this.list(hql, new Object[]{arg});
    }

    public List<T> list(StringBuilder hql) {
        return this.list(hql, null);
    }

    public List<T> list(StringBuilder hql, Object[] args, Map<String, Object> alias) {
        hql = initSort(hql);
        Query query = getSession().createQuery(hql.toString());
        setAliasParameter(query, alias);
        setParameter(query, args);
        return query.setCacheable(true).list();
    }

    public List<T> listByAlias(StringBuilder hql, Map<String, Object> alias) {
        return this.list(hql, null, alias);
    }

    // *******************list列表对象不分页 +别名和查询参数的查询 结束********

    // *****************************分页查询开始******************
    // 设置分页对象，初始化pageSize为10
    @SuppressWarnings("rawtypes")
    private void setPagers(Query query, Pager pages) {
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        if (pageOffset == null || pageOffset < 0)
            pageOffset = 0;
        if (pageSize == null || pageSize < 0)
            pageSize = 10;
        pages.setOffset(pageOffset);
        pages.setSize(pageSize);
        query.setFirstResult(pageOffset).setMaxResults(pageSize);
    }

    // 使用Count()函数的查询
    private String getCountHql(StringBuilder hql, boolean isHql) {
        // 截取从from字符开始到最后的子串
        String e = hql.substring(hql.indexOf("from"));
        String c = "select count(*) " + e;
        // 不要fetch
        if (isHql)
            c = c.replace("fetch", "");
        return c;
    }

    public Pager<T> find(StringBuilder hql, Object[] args) {
        return this.find(hql, args, null);
    }

    public Pager<T> find(StringBuilder hql, Object arg) {
        return this.find(hql, new Object[]{arg});
    }

    public Pager<T> find(StringBuilder hql) {
        return this.find(hql, null);
    }

    public Pager<T> find(StringBuilder hql, Object[] args, Map<String, Object> alias) {
        hql = initSort(hql);
        String cq = getCountHql(hql, true);
        Query cquery = getSession().createQuery(cq);
        Query query = getSession().createQuery(hql.toString());
        // 设置别名参数
        setAliasParameter(query, alias);
        setAliasParameter(cquery, alias);
        // 设置参数
        setParameter(query, args);
        setParameter(cquery, args);
        // 设置分页量化数据
        Pager<T> pages = new Pager<>();
        setPagers(query, pages);
        List<T> datas = query.setCacheable(true).list();
        pages.setRows(datas);
        long total = (Long) cquery.uniqueResult();
        pages.setTotal(total);
        return pages;
    }

    public Pager<T> findByAlias(StringBuilder hql, Map<String, Object> alias) {
        return this.find(hql, null, alias);
    }

    // *****************************分页查询完毕******************

    // ******************查询单个对象开始***************************
    public Object queryObject(StringBuilder hql, Object[] args, Map<String, Object> alias) {
        Query query = getSession().createQuery(hql.toString());
        setAliasParameter(query, alias);
        setParameter(query, args);
        return query.uniqueResult();
    }

    public Object queryObjectByAlias(StringBuilder hql, Map<String, Object> alias) {
        return this.queryObject(hql, null, alias);
    }

    public Object queryObject(StringBuilder hql, Object[] args) {
        return this.queryObject(hql, args, null);
    }

    public Object queryObject(StringBuilder hql, Object arg) {
        return this.queryObject(hql, new Object[]{arg});
    }

    public Object queryObject(StringBuilder hql) {
        return this.queryObject(hql, null);
    }

    // ******************查询单个对象完毕***************************

    // *********************更新对象开始*******************
    public int updateByHql(StringBuilder hql, Object[] args) {
        Query query = getSession().createQuery(hql.toString());
        setParameter(query, args);
        return query.executeUpdate();
    }

    public int updateByHql(StringBuilder hql, Object arg) {
        return this.updateByHql(hql, new Object[]{arg});
    }

    public int updateByHql(StringBuilder hql) {
        return this.updateByHql(hql, null);
    }

    // *********************更新对象完毕*******************

    // ******************原生sql开始********************
    public <N> List<N> listBySql(StringBuilder sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.listBySql(sql, args, null, clz, hasEntity);
    }

    public <N> List<N> listBySql(StringBuilder sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.listBySql(sql, new Object[]{arg}, clz, hasEntity);
    }

    public <N> List<N> listBySql(StringBuilder sql, Class<?> clz, boolean hasEntity) {
        return this.listBySql(sql, null, clz, hasEntity);
    }

    public <N> List<N> listBySql(StringBuilder sql, Object[] args, Map<String, Object> alias,
                                 Class<?> clz, boolean hasEntity) {
        sql = initSort(sql);
        SQLQuery sq = getSession().createSQLQuery(sql.toString());
        setAliasParameter(sq, alias);
        setParameter(sq, args);
        if (hasEntity) {
            sq.addEntity(clz);
        } else
            sq.setResultTransformer(Transformers.aliasToBean(clz));
        return sq.list();
    }

    public <N> List<N> listByAliasSql(StringBuilder sql, Map<String, Object> alias, Class<?> clz,
                                      boolean hasEntity) {
        return this.listBySql(sql, null, alias, clz, hasEntity);
    }

    // ************************不分页原生sql结束**********************

    // **************分页原生sql开始 **********************

    public <N> Pager<N> findBySql(StringBuilder sql, Object[] args, Class<?> clz, boolean hasEntity) {
        return this.findBySql(sql, args, null, clz, hasEntity);
    }

    public <N> Pager<N> findBySql(StringBuilder sql, Object arg, Class<?> clz, boolean hasEntity) {
        return this.findBySql(sql, new Object[]{arg}, clz, hasEntity);
    }

    public <N> Pager<N> findBySql(StringBuilder sql, Class<?> clz, boolean hasEntity) {
        return this.findBySql(sql, null, clz, hasEntity);
    }

    public <N> Pager<N> findBySql(StringBuilder sql, Object[] args, Map<String, Object> alias,
                                  Class<?> clz, boolean hasEntity) {
        sql = initSort(sql);
        String cq = getCountHql(sql, false);
        SQLQuery sq = getSession().createSQLQuery(sql.toString());
        SQLQuery cquery = getSession().createSQLQuery(cq);
        setAliasParameter(sq, alias);
        setAliasParameter(cquery, alias);
        setParameter(sq, args);
        setParameter(cquery, args);
        Pager<N> pages = new Pager<>();
        setPagers(sq, pages);
        if (hasEntity) {
            sq.addEntity(clz);
        } else {
            sq.setResultTransformer(Transformers.aliasToBean(clz));
        }
        List<N> datas = sq.list();
        pages.setRows(datas);
        long total = ((BigInteger) cquery.uniqueResult()).longValue();
        pages.setTotal(total);
        return pages;
    }

    public <N> Pager<N> findByAliasSql(StringBuilder sql, Map<String, Object> alias, Class<?> clz,
                                       boolean hasEntity) {
        return this.findBySql(sql, null, alias, clz, hasEntity);
    }

    @Override
    public boolean isExist(String propertyName, Object value) {
        T object = get(propertyName, value);
        return (object != null);
    }

    @Override
    public T get(String propertyName, Object value) {
        String hql = "from " + getClz().getName() + " as model where model." + propertyName + " = ?";
        return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
    }

    // **************分页原生sql结束 *************************

}

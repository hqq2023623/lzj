package zj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import zj.mongo.MongoFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * @author Lzj Created on 2015/12/18.
 */
public class BaseDao<T> implements IBaseDao<T> {

    private Class<T> clazz;

    public BaseDao() {
        ParameterizedType type = (ParameterizedType) getClass()
                .getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoFactory mongoFactory;


    @Override
    public Long findCount(Query query) {
        return mongoTemplate.count(query, clazz);
    }

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, clazz);
    }

    @Override
    public List<T> findList(int start, int limit) {
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "_id")));
        query.skip(start).limit(limit);
        return find(query);
    }

    @Override
    public T findOne(String id) {
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        return mongoTemplate.findOne(query, clazz);
    }

    @Override
    public void insert(T entity) {
        mongoTemplate.insert(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        Map<String, Object> map = mongoFactory.converObjectToParams(entity);

        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(map.get("id")));
        Update update = (Update) map.get("update");

        this.update(query, update);
    }

    @Override
    public void update(Query query, Update update) {
        mongoTemplate.updateFirst(query, update, clazz);
    }

    @Override
    public void remove(T entity) {
        mongoTemplate.remove(entity);
    }

}

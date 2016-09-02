package zj.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import zj.model.Users;
import zj.mongo.Pagination;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
@Repository
public class UsersDao extends BaseDao<Users> implements IUsersDao {

    @Override
    public Users findById(String id) {
        return findOne(id);
    }

    @Override
    public void insertOne(Users entity) {
        insert(entity);
    }

    @Override
    public Long findCount(String username) {
        Query query = createQuery(username);
        return this.findCount(query);
    }

    private Query createQuery(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return query;
    }

    @Override
    public List<Users> findListByPage(Pagination page, String username) {
        Query query = createQuery(username);
        query.with(page.getSort());
        query.skip(page.getSkip()).limit(page.getPageSize());// 从skip开始,取多少条记录
        return this.find(query);
    }


}

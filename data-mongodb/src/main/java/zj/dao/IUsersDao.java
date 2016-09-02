package zj.dao;

import zj.model.Users;
import zj.mongo.Pagination;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
public interface IUsersDao extends IBaseDao<Users> {

    Users findById(String id);

    void insertOne(Users entity);

    /**
     * 根据username属性获取数据的数量
     */
    public Long findCount(String username);

    /**
     * 分页获取数据
     */
    public List<Users> findListByPage(Pagination page, String username);

}

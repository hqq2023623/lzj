package zj.base.dao;

import org.springframework.stereotype.Repository;
import zj.base.model.User;

import java.util.HashMap;
import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
@Repository
public class UserDao extends BaseDao<User, Long> implements IUserDao {

    @Override
    public List<User> listAll() {
        return super.listAll();
    }

}

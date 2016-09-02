package zj.test2.dao;

import org.springframework.stereotype.Repository;
import zj.base.dao.BaseDao;
import zj.model.User;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
@Repository
public class Test2UserDao extends BaseDao<User, Long> implements ITest2UserDao {

    //不知为毛如果用hql的话就会报错:No row with the given identifier exists: [zj.model.User#1]
    @Override
    public List<User> listAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from t_user");
        return listBySql(sb,User.class,true);
//        return super.listAll();
    }

}

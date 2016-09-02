package zj.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zj.base.dao.IUserDao;
import zj.base.model.User;
import zj.test.util.DbunitTestBase;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
@ContextConfiguration("/test_base.xml")
public class TestUserDao extends DbunitTestBase {

    @Autowired
    private IUserDao userDao;

    @Test
    public void test01() {
        List<User> list = userDao.listAll();
        list.forEach(zj.base.model.User::printAll);
    }

    @Override
    public void doBefore() {
        executeXml();
    }


}

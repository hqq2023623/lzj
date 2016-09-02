package zj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zj.test.dao.IUserDao;
import zj.test2.dao.ITest2UserDao;

/**
 * @author Lzj Created on 2015/12/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_base.xml")
public class TestDao {

    @Autowired
    private IUserDao testDao;

    @Autowired
    private ITest2UserDao test2Dao;

    //test数据库
    @Test
    public void test01() {
        System.out.println("test=====================");
        testDao.listAll();
    }

    @Test
    public void test02() {
        System.out.println("test2--------------------");
        test2Dao.listAll();
    }


}

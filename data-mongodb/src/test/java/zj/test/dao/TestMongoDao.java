package zj.test.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zj.dao.IUsersDao;
import zj.model.Users;
import zj.mongo.Pagination;
import zj.service.IUsersService;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_base.xml")
public class TestMongoDao {

    @Autowired
    private IUsersDao dao;
    @Autowired
    private IUsersService usersService;

    @Test
    public void test01() {
        Users u = new Users();
        u.setId("1");
        u.setUsername("lzj");
        dao.insertOne(u);
        Users u2 = dao.findById("1");
        assertEquals(u.getUsername(), u2.getUsername());

        Pagination users = usersService.findArticles(0, "lzj");
        for (Object us : users.getItems()) {
            Users o = (Users) us;
            System.out.println(o.getUsername());
        }

    }


}

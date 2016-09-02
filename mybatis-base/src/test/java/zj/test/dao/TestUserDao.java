package zj.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zj.base.mapper.UserMapper;
import zj.base.model.User;
import zj.base.service.IUserService;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_base.xml")
public class TestUserDao {


    @Autowired
    private IUserService userService;

    @Test
    public void test01() {
        List<User> list = userService.listAll();
        list.forEach(zj.base.model.User::printAll);
    }


}

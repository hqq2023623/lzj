package zj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zj.dao.IUsersDao;
import zj.mongo.Pagination;

/**
 * @author Lzj Created on 2015/12/18.
 */
@Service
public class UsersService implements IUsersService {

    @Autowired
    private IUsersDao usersDao;


    @Override
    public Pagination findArticles(Integer currentPage, String username) {
        Long count = usersDao.findCount(username);
        Pagination page = new Pagination(currentPage, count);
        page.setSort("creatTime", false);
        page.setItems(usersDao.findListByPage(page, username));
        return page;
    }


}

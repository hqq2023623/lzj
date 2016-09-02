package zj.service;

import zj.mongo.Pagination;

/**
 * @author Lzj Created on 2015/12/18.
 */
public interface IUsersService {

    Pagination findArticles(Integer currentPage, String username);

}

package zj.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zj.base.mapper.UserMapper;
import zj.base.model.User;

import java.util.List;

/**
 * @author Lzj Created on 2015/12/18.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listAll() {
        return userMapper.listAll();
    }

}

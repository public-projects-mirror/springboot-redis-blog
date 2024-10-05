package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.UserDO;
import com.example.exception.ForbiddenException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService extends ServiceImpl<UserMapper, UserDO> {

    @Transactional
    public void authenticate(String username, String password) {
        UserDO userDO = this.baseMapper.findByUsername(username);
        if (userDO == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        if (!userDO.getPassword().equals(password)) {
            throw new ForbiddenException("Wrong password with username: " + username);
        }
    }
}

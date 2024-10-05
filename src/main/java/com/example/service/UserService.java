package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.UserDTO;
import com.example.entity.UserDO;
import com.example.exception.UserAlreadyExistsException;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService extends ServiceImpl<UserMapper, UserDO> {

    @Transactional
    public void checkUserExists(String username) {
        UserDO userDO = this.baseMapper.findByUsername(username);
        if (userDO != null) {
            throw new UserAlreadyExistsException("User already exists with username: " + username);
        }
    }

    public UserDTO saveUser(String username, String password) {
        UserDO userDO = new UserDO();
        UUID uuid = UUID.randomUUID();
        userDO.setUserId(uuid.toString());
        userDO.setUsername(username);
        userDO.setPassword(password);
        this.save(userDO);
        return UserDTO.newFromDO().apply(userDO);
    }

    public UserDTO findUserByUsername(String username) {
        UserDO userDO = this.baseMapper.findByUsername(username);
        return UserDTO.newFromDO().apply(userDO);
    }

    public UserDTO findUserByUserId(String userId) {
        UserDO userDO = this.baseMapper.findByUserId(userId);
        return UserDTO.newFromDO().apply(userDO);
    }

    public void editPassword(String userId, String newPassword) {
        UserDO userDO = this.baseMapper.findByUserId(userId);
        userDO.setPassword(newPassword);
        this.updateById(userDO);
    }
}

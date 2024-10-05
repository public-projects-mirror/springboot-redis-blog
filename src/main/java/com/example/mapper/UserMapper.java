package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    @Select("SELECT * FROM users WHERE username = #{username}")
    UserDO findByUsername(String username);

    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    UserDO findByUserId(String userId);
}

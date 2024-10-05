package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.SessionDO;
import org.apache.ibatis.annotations.Select;

public interface SessionMapper extends BaseMapper<SessionDO> {
    @Select("SELECT * FROM session WHERE session_id = #{sessionId}")
    SessionDO findBySessionId(String sessionId);
}

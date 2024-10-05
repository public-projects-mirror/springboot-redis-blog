package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SessionDO;
import com.example.exception.SessionNotFoundException;
import com.example.mapper.SessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class SessionService extends ServiceImpl<SessionMapper, SessionDO> {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SESSION_CACHE_PREFIX = "session:";

    @Transactional
    public void checkSessionExists(SessionDO sessionDO) {
        if (sessionDO == null) {
            throw new SessionNotFoundException("Session not found");
        }
    }

    public void saveSession(SessionDO sessionDO) {
        this.save(sessionDO);
        // 同时缓存到 Redis，设置过期时间（例如 30 分钟）
        String cacheKey = SESSION_CACHE_PREFIX + sessionDO.getSessionId();
        redisTemplate.opsForValue().set(cacheKey, sessionDO, 30, TimeUnit.MINUTES);
    }

    @Transactional
    public void deleteSession(SessionDO sessionDO) {
        checkSessionExists(sessionDO);  // 确保 Session 存在

        // 从数据库删除
        this.removeById(sessionDO.getSessionId());

        // 从 Redis 删除
        String cacheKey = SESSION_CACHE_PREFIX + sessionDO.getSessionId();
        redisTemplate.delete(cacheKey);
    }

    @Transactional
    public SessionDO getSessionBySessionId(String sessionId) {
        SessionDO sessionDO = this.baseMapper.findBySessionId(sessionId);
        checkSessionExists(sessionDO);
        return sessionDO;
    }
}
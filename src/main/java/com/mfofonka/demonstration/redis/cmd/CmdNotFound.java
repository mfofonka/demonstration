package com.mfofonka.demonstration.redis.cmd;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class CmdNotFound extends RedisCmd {

	public CmdNotFound(CacheRepository cacherepository) {
		super(null);
	}

	@Override
	public String execute(String[] args) {
		return "command not found";
	}

}

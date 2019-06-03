package com.mfofonka.demonstration.redis.cmd;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class DelCommand extends RedisCmd {
	
	public DelCommand(CacheRepository cacherepository) {
		super(cacherepository);
	}
	
	@Override
	public String execute(String[] args) {
		cacherepository.deleteByKey(args[1]);
		return null;
	}

}

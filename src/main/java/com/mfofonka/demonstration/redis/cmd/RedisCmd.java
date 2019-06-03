package com.mfofonka.demonstration.redis.cmd;

import com.mfofonka.demonstration.cache.CacheRepository;

public abstract class RedisCmd {
	protected CacheRepository cacherepository;
	
	public RedisCmd(CacheRepository cacherepository) {
		this.cacherepository = cacherepository;
	}
	
	public abstract String execute(String[] args);
}

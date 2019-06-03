package com.mfofonka.demonstration.redis.cmd;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class DbSizeCommand extends RedisCmd {

	public DbSizeCommand(CacheRepository cacherepository) {
		super(cacherepository);
		this.cacherepository = cacherepository;
	}

	@Override
	public String execute(String[] args) {
		return String.valueOf(cacherepository.getDBSize());
	}

}

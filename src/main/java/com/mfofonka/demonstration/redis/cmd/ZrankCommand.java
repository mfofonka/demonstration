package com.mfofonka.demonstration.redis.cmd;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class ZrankCommand extends RedisCmd {
	
	public ZrankCommand(CacheRepository cacherepository) {
		super(cacherepository);
	}

	@Override
	public String execute(String[] args) {
		return String.valueOf(cacherepository.findByValueKey(args[1], args[2]).getRank() - 1);
	}
}

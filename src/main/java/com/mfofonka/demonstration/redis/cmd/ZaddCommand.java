package com.mfofonka.demonstration.redis.cmd;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.bean.dto.KeyValuePairDTO;
import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class ZaddCommand extends RedisCmd {

	public ZaddCommand(CacheRepository cacherepository) {
		super(cacherepository);
	}

	@Override
	public String execute(String[] args) {
		KeyValuePairDTO keyValuePairDTO = new KeyValuePairDTO(args[1], args[3], Integer.parseInt(args[2]));
		cacherepository.create(keyValuePairDTO, Boolean.FALSE);
		return null;
	}

}

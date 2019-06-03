package com.mfofonka.demonstration.redis.cmd;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.bean.dto.KeyValuePairDTO;
import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class SetCommand extends RedisCmd {

	public SetCommand(CacheRepository cacherepository) {
		super(cacherepository);
	}

	@Override
	public String execute(String[] args) {
		KeyValuePairDTO keyValuePair = null;
		if (args.length == 3) {
			 keyValuePair = new KeyValuePairDTO(args[1], args[2]);
		} else if (args.length == 5) {
			keyValuePair = new KeyValuePairDTO(args[1], args[2], 0, Integer.valueOf(args[4]));
		}
		cacherepository.create(keyValuePair, Boolean.TRUE);
		return null;
	}
}

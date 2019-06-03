package com.mfofonka.demonstration.redis.cmd;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.bean.dto.KeyValuePairDTO;
import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class GetCommand extends RedisCmd {

	public GetCommand(CacheRepository cacherepository) {
		super(cacherepository);
		this.cacherepository = cacherepository;
	}

	@Override
	public String execute(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		KeyValuePairDTO findResult = cacherepository.findByKey(args[1]);
		if (Objects.equals(0L, findResult.getTimeToLive()) || findResult.getTimeToLive() > currentTimeMillis) {
			return findResult.getValue();
		}
		return null;
	}

}

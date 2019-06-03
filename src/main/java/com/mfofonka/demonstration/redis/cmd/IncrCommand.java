package com.mfofonka.demonstration.redis.cmd;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.bean.dto.KeyValuePairDTO;
import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class IncrCommand extends RedisCmd {
	
	public IncrCommand(CacheRepository cacherepository) {
		super(cacherepository);
	}
	
	@Override
	public String execute(String[] args) {
		String value = cacherepository.findByKey(args[1]).getValue();
		KeyValuePairDTO keyValuePair = new KeyValuePairDTO(args[1], convertToDecimal(value));
		cacherepository.create(keyValuePair, Boolean.TRUE);
		return null;
	}

	private String convertToDecimal(String input) {
		int base = 10;
		Long result = 0L;
		for (char c : input.toCharArray()) {
			result = result * base + Character.digit(c, base);
		}
		return String.valueOf(result);
	}
}

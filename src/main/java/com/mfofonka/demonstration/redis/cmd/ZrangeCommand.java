package com.mfofonka.demonstration.redis.cmd;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.bean.dto.KeyValuePairDTO;
import com.mfofonka.demonstration.cache.CacheRepository;

@Component
public class ZrangeCommand extends RedisCmd {

	private BiFunction<Integer, Integer, Integer> calculateToIndex = (stopIndex, collectionSize) -> {
		if (stopIndex > 0) {
			return collectionSize >= stopIndex ? collectionSize : collectionSize - stopIndex;
		} else {
			return collectionSize;
		}
	};
	
	private BiFunction<Integer, Integer, Integer> calculateFromIndex = (startIndex, collectionSize) -> {
		if (startIndex > 0) {
			return startIndex > collectionSize ? 0 : startIndex;
		} else {
			return 0;
		}
	};
	
	public ZrangeCommand(CacheRepository cacherepository) {
		super(cacherepository);
	}

	@Override
	public String execute(String[] args) {
		int startIndex = Integer.parseInt(args[2]);
		int stopIndex = Integer.parseInt(args[3]);
		List<KeyValuePairDTO> findByKey = cacherepository.findListByKey(args[1]);
		List<String> collect = findByKey.stream().map(s -> s.getValue()).collect(Collectors.toList());
		// case negative values
		if (startIndex < 0 && stopIndex < 0) {
			// swapping values
			if (startIndex < stopIndex) {
				startIndex = startIndex + stopIndex;
				stopIndex = startIndex - stopIndex;
				startIndex = startIndex - stopIndex;
			}
			startIndex *= -1;
			stopIndex *= -1;
		}
		int fromIndex = calculateFromIndex.apply(startIndex, collect.size());
		int toIndex = calculateToIndex.apply(stopIndex, collect.size());
		return String.valueOf(collect.subList(fromIndex, toIndex));
	}
}

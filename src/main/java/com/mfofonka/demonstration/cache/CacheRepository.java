package com.mfofonka.demonstration.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.bean.dto.KeyValuePairDTO;

@Component
public class CacheRepository extends AbstractCache {

	public CacheRepository(CacheManager cacheManager) {
		super(cacheManager);
	}

	@PostConstruct
	private void populate() {
		create("ken", "usa", false);
		create("guile", "usa", false);
		create("ryu", "japan", false);
		create("honda", "japan", false);
		create("bison", "tailand", false);
		create("blanka", "brazil", false);
		create("chun-li", "china", false);
	}

	public KeyValuePairDTO create(String key, String value, Boolean isOverride) {
		KeyValuePairDTO simpleKeyValuePairDTO = new KeyValuePairDTO(key, value);
		create(simpleKeyValuePairDTO, isOverride);
		return simpleKeyValuePairDTO;
	}

	public KeyValuePairDTO create(KeyValuePairDTO simpleKeyValuePairDTO, Boolean isOverride) {
		List<KeyValuePairDTO> listTemp;
		if(isOverride) {
			listTemp = new ArrayList<>();
		} else {
			listTemp = handleCachedValue(get.apply(simpleKeyValuePairDTO.getKey()));
		}
		listTemp.add(simpleKeyValuePairDTO);
		put.accept(simpleKeyValuePairDTO.getKey(), listTemp);
		return simpleKeyValuePairDTO;
	}

	public List<KeyValuePairDTO> findListByKey(String key) {
		return handleCachedValue(get.apply(key));
	}

	public KeyValuePairDTO findByKey(String key) {
		List<KeyValuePairDTO> listTemp = findListByKey(key);
		return listTemp.isEmpty() ? new KeyValuePairDTO() : listTemp.get(0);
	}

	public KeyValuePairDTO findByValueKey(String key,  String value) {
		List<KeyValuePairDTO> allValues = findListByKey(key);
		return allValues.stream().filter(kv -> Objects.equals(value, kv.getValue())).findFirst().orElse(new KeyValuePairDTO());
	}
	
	public Integer countByRank(String key) {
		return findListByKey(key).size();
	}

	public void deleteByKey(String key) {
		evict.accept(key);
	}

	@SuppressWarnings("unchecked")
	private List<KeyValuePairDTO> handleCachedValue(ValueWrapper cachedValue) {
		List<KeyValuePairDTO> listTemp;
		if(null != cachedValue) {
			listTemp = (List<KeyValuePairDTO>) cachedValue.get();
		} else {
			listTemp = new ArrayList<>();
		}
		return listTemp;
	}

}

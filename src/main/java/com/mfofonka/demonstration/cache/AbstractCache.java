package com.mfofonka.demonstration.cache;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;

import com.mfofonka.demonstration.bean.dto.KeyValuePairDTO;

import net.sf.ehcache.Cache;

public abstract class AbstractCache {

	protected CacheManager cacheManager;

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	/**
	 * thread Safe Read from cache
	 */
	protected Function<String, ValueWrapper> get = (String key) -> {
		readLock.lock();
		try {
			return getCache().get(key);
		} finally {
			readLock.unlock();
		}
	};

	/**
	 * thread Safe Write
	 */
	protected BiConsumer<String, List<KeyValuePairDTO>> put = (key, list) -> {
		writeLock.lock();
		try {
			getCache().put(key, list);
		} finally {
			writeLock.unlock();
		}
	};

	/**
	 * thread Safe Evict
	 */
	protected Consumer<String> evict = (key) -> {
		writeLock.lock();
		try {
			getCache().evict(key);
		} finally {
			writeLock.unlock();
		}
	};

	/**
	 * Constructor
	 * 
	 * @return
	 **/
	public AbstractCache(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@SuppressWarnings("unchecked")
	protected List<String> getKeys() {
		readLock.lock();
		try {
			return getNativeCache().getKeys();
		} finally {
			readLock.unlock();
		}
	}

	public Integer getDBSize() {
		readLock.lock();
		try {
			return getNativeCache().getSize();
		} finally {
			readLock.unlock();
		}
	}

	private Cache getNativeCache() {
		return (Cache) getCache().getNativeCache();
	}

	private org.springframework.cache.Cache getCache() {
		return cacheManager.getCache(CachingConfig.CACHE_KEY);
	}
}
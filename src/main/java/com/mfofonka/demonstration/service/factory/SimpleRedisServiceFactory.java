package com.mfofonka.demonstration.service.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mfofonka.demonstration.redis.cmd.CmdNotFound;
import com.mfofonka.demonstration.redis.cmd.DbSizeCommand;
import com.mfofonka.demonstration.redis.cmd.DelCommand;
import com.mfofonka.demonstration.redis.cmd.GetCommand;
import com.mfofonka.demonstration.redis.cmd.IncrCommand;
import com.mfofonka.demonstration.redis.cmd.RedisCmd;
import com.mfofonka.demonstration.redis.cmd.SetCommand;
import com.mfofonka.demonstration.redis.cmd.ZaddCommand;
import com.mfofonka.demonstration.redis.cmd.ZcardCommand;
import com.mfofonka.demonstration.redis.cmd.ZrangeCommand;
import com.mfofonka.demonstration.redis.cmd.ZrankCommand;

@Component
public class SimpleRedisServiceFactory {

	@Autowired
	private GetCommand getCommand;
	@Autowired
	private SetCommand setCommand;
	@Autowired
	private DbSizeCommand dbSizeCommand;
	@Autowired
	private DelCommand delCommand;
	@Autowired
	private IncrCommand incrCommand;
	@Autowired
	private ZcardCommand zcardCommand;
	@Autowired
	private ZaddCommand zaddCommand;
	@Autowired
	private ZrangeCommand zrangeCommand;
	@Autowired
	private ZrankCommand zrankCommand;
	@Autowired
	private CmdNotFound redisCmdNotFound;

	private Map<ServiceTypeEnum, RedisCmd> cmdMap = new HashMap<>();

	@PostConstruct
	public void populateCmdMap() {
		cmdMap.put(ServiceTypeEnum.GET, getCommand);
		cmdMap.put(ServiceTypeEnum.SET, setCommand);
		cmdMap.put(ServiceTypeEnum.DBSIZE, dbSizeCommand);
		cmdMap.put(ServiceTypeEnum.DEL, delCommand);
		cmdMap.put(ServiceTypeEnum.INCR, incrCommand);
		cmdMap.put(ServiceTypeEnum.ZCARD, zcardCommand);
		cmdMap.put(ServiceTypeEnum.ZADD, zaddCommand);
		cmdMap.put(ServiceTypeEnum.ZRANGE, zrangeCommand);
		cmdMap.put(ServiceTypeEnum.ZRANK, zrankCommand);
		cmdMap.put(ServiceTypeEnum.NOTFOUND, redisCmdNotFound);
	}

	public RedisCmd getService(String[] args) {
		ServiceTypeEnum serviceType = ServiceTypeEnum.fromString(args[0]);
		return cmdMap.getOrDefault(serviceType, redisCmdNotFound);
	}

}
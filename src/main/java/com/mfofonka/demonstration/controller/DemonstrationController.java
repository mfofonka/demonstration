package com.mfofonka.demonstration.controller;

import org.apache.tools.ant.types.Commandline;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mfofonka.demonstration.redis.cmd.RedisCmd;
import com.mfofonka.demonstration.service.factory.SimpleRedisServiceFactory;

@RestController
public class DemonstrationController {

	private SimpleRedisServiceFactory simpleRedisServiceFactory;

	public DemonstrationController(SimpleRedisServiceFactory simpleRedisServiceFactory) {
		this.simpleRedisServiceFactory = simpleRedisServiceFactory;
	}

	@RequestMapping("/")
	public String index(@RequestParam("cmd") String cmd) throws RuntimeException {
		String[] args = Commandline.translateCommandline(cmd.replaceAll("\"", ""));
		return call(args);
	}

	public String call(String[] args) {
		RedisCmd cmd = simpleRedisServiceFactory.getService(args);
		return cmd.execute(args);
	}
}

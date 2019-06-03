package com.mfofonka.demonstration.service.factory;

public enum ServiceTypeEnum {
	SET("set"), GET("get"), NOTFOUND(""), DBSIZE("dbsize"), DEL("del"), INCR("incr"), ZCARD("zcard"), ZADD("zadd"), ZRANGE("zrange"), ZRANK("zrank");
	private String text;
	
	private ServiceTypeEnum(String text) {
		this.text = text;
	}

	public static ServiceTypeEnum fromString(String text) {
		for (ServiceTypeEnum b : ServiceTypeEnum.values()) {
			if (b.text.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return NOTFOUND;
	}
}
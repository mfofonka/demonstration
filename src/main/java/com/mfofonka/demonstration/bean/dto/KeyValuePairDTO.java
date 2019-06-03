package com.mfofonka.demonstration.bean.dto;

import java.io.Serializable;

public class KeyValuePairDTO implements Serializable {

	private static final long serialVersionUID = 6880349302657197815L;
	private String key;
	private String value;
	private Integer rank = 0;
	private Long timeToLive = 0L;

	public KeyValuePairDTO() {
		super();
	}

	public KeyValuePairDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public KeyValuePairDTO(String key, String value, Integer rank) {
		super();
		this.key = key;
		this.rank = rank;
		this.value = value;
	}

	public KeyValuePairDTO(String key, String value, Integer rank, Integer secondsToLive) {
		super();
		this.key = key;
		this.rank = rank;
		this.value = value;
		this.timeToLive = System.currentTimeMillis() + (secondsToLive * 1000);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Long timeToLive) {
		this.timeToLive = timeToLive;
	}

	@Override
	public String toString() {
		return "SimpleKeyValuePairDTO [key=" + key + ", value=" + value + ", rank=" + rank + "]";
	}
}

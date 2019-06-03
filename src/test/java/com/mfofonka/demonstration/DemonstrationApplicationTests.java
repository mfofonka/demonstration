package com.mfofonka.demonstration;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemonstrationApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void shouldFindRyuCountry() throws Exception {
		ResponseEntity<String> result = restTemplate.getForEntity("/?cmd=GET ryu", String.class);
		Assert.assertEquals("I'm expecting to see japan", "japan", result.getBody());
	}

	@Test
	public void shouldDeleteAnEntry() throws Exception {
		ResponseEntity<String> resultDbSize = restTemplate.getForEntity("/?cmd=DBSIZE", String.class);
		int parseIntDbSize = Integer.parseInt(resultDbSize.getBody());
		restTemplate.getForEntity("/?cmd=del chun-li", String.class);
		ResponseEntity<String> result = restTemplate.getForEntity("/?cmd=DBSIZE", String.class);
		Assert.assertEquals("Expected to see less values", String.valueOf(parseIntDbSize - 1), result.getBody());
	}

	@Test
	// set key value
	public void shouldSetAnEntry() throws Exception {
		ResponseEntity<String> resultDbSize = restTemplate.getForEntity("/?cmd=DBSIZE", String.class);
		restTemplate.getForEntity("/?cmd=set Fei-Long Hong-Kong", String.class);
		ResponseEntity<String> result = restTemplate.getForEntity("/?cmd=DBSIZE", String.class);
		Assert.assertEquals("Expected to see more values", String.valueOf(Integer.parseInt(resultDbSize.getBody()) + 1),
				result.getBody());
	}

	@Test
	// set key value EX 10
	public void shouldSetAnEntryWithTimeToLiveSetOn() throws Exception {
		// set for 3 seconds
		restTemplate.getForEntity("/?cmd=set Fei-Long10 Hong-Kong EX 3", String.class);
		ResponseEntity<String> result1 = restTemplate.getForEntity("/?cmd=get Fei-Long10", String.class);
		Assert.assertEquals("Expected to see \"Hong-Kong\"", "Hong-Kong", result1.getBody());
		// Wait 3 seconds and check again
		TimeUnit.SECONDS.sleep(3);
		ResponseEntity<String> result2 = restTemplate.getForEntity("/?cmd=get Fei-Long10", String.class);
		Assert.assertEquals("Expected to see no values", null, result2.getBody());
	}

	@Test
	// zadd(String, String, int)
	public void shouldAddRankedEntry() throws Exception {
		ResponseEntity<String> resultDbSize = restTemplate.getForEntity("/?cmd=DBSIZE", String.class);
		restTemplate.getForEntity("/?cmd=zadd myrankedset 1 Hong-Kong", String.class);
		restTemplate.getForEntity("/?cmd=zadd myrankedset 2 Hill-Kong", String.class);
		restTemplate.getForEntity("/?cmd=zadd myrankedset 3 Hell-Kong", String.class);
		ResponseEntity<String> result = restTemplate.getForEntity("/?cmd=DBSIZE", String.class);
		Assert.assertEquals("Expected to see more values", String.valueOf(Integer.parseInt(resultDbSize.getBody()) + 1),
				result.getBody());
	}

	@Test
	// zrank(String, String)
	public void shouldReturnRankedPosition() {
		restTemplate.getForEntity("/?cmd=zadd myrankedset 1 1Hong-Kong", String.class);
		restTemplate.getForEntity("/?cmd=zadd myrankedset 2 2Hill-Kong", String.class);
		restTemplate.getForEntity("/?cmd=zadd myrankedset 3 3Hell-Kong", String.class);
		ResponseEntity<String> result = restTemplate.getForEntity("/?cmd=zrank myrankedset 2Hill-Kong", String.class);
		Assert.assertEquals("1", result.getBody());
	}

	@Test
	// zcard(String)
	public void shouldReturnElementsInRankedSet() {
		restTemplate.getForEntity("/?cmd=zadd myrankedset2 1 1Hong-Kong", String.class);
		restTemplate.getForEntity("/?cmd=zadd myrankedset2 2 2Hill-Kong", String.class);
		restTemplate.getForEntity("/?cmd=zadd myrankedset2 3 3Hell-Kong", String.class);
		ResponseEntity<String> result = restTemplate.getForEntity("/?cmd=zcard myrankedset2", String.class);
		Assert.assertEquals("3", result.getBody());
	}

	@Test
	// zrange(String, int, int)
	public void shouldReturnElementsInTheSet() {
		restTemplate.getForEntity("/?cmd=ZADD myzset 1 \"one\"", String.class);
		restTemplate.getForEntity("/?cmd=ZADD myzset 2 \"two\"", String.class);
		restTemplate.getForEntity("/?cmd=ZADD myzset 3 \"three\"", String.class);
		// full set
		ResponseEntity<String> result = restTemplate.getForEntity("/?cmd=ZRANGE myzset 0 -1", String.class);
		Assert.assertEquals("[one, two, three]", result.getBody());
		// partial set
		ResponseEntity<String> result2 = restTemplate.getForEntity("/?cmd=ZRANGE myzset 2 3", String.class);
		Assert.assertEquals("[three]", result2.getBody());
		// reverting indexes
		ResponseEntity<String> result3 = restTemplate.getForEntity("/?cmd=ZRANGE myzset -2 -1", String.class);
		Assert.assertEquals("[two, three]", result3.getBody());
	}

	@Test
	public void shouldTurnStringInto11() throws Exception {
		restTemplate.getForEntity("/?cmd=set myset OK", String.class);
		restTemplate.getForEntity("/?cmd=incr myset", String.class);
		ResponseEntity<String> forEntity = restTemplate.getForEntity("/?cmd=get myset", String.class);
		Assert.assertEquals("Expected to see -11 value", "-11", forEntity.getBody());
	}
}

package com.example.demo.modules.weather.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import com.example.demo.modules.weather.vo.WeatherResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WeatherDataService 实现.
 * 
 * @since 1.0.0 2017年11月22日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	private  final static Logger logger= LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	//private  final static String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";
	private  final static long TIME_OUT=3600;//1小时

	@Value("${weather.url}")
	private String WEATHER_URI;

	@Value("${spring.redis.isopen}")
	private boolean REDIS_IS_OPEN;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		return this.doGetWeahter(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_URI + "city=" + cityName;
		return this.doGetWeahter(uri);
	}
	
	private WeatherResponse doGetWeahter(String uri) {
 		//ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
		String key=uri;
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse resp = null;
		String strBody="";
		if(REDIS_IS_OPEN){
			ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
			//采用redis缓存,先查缓存，如果存在则在缓存中获取
			if(stringRedisTemplate.hasKey(key)){
				strBody=ops.get(key);
				logger.info("--- redis ---");
			}else{
				strBody = getWeatherInfo(uri);
				logger.info("--- no redis ---");
				//将数据写入缓存zho中
				ops.set(key,strBody,TIME_OUT, TimeUnit.SECONDS);
			}
		/*if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
		}*/
		}else{
			strBody = getWeatherInfo(uri);
		}

		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resp;
	}

	public static String getWeatherInfo(String url) {
		CloseableHttpClient client;
		client = HttpClients.createDefault();

		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				get.abort();
				return str;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}

}

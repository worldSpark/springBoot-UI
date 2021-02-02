package com.example.demo.modules.weather.controller;

import com.example.demo.conf.SysUserContext;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.weather.service.WeatherDataService;
import com.example.demo.modules.weather.vo.WeatherResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Weather Controller.
 * 
 * @since 1.0.0 2017年11月22日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {
	@Autowired
	private WeatherDataService weatherDataService;

	@RequestMapping("/list")
	public ModelAndView getMapView(){
		return new ModelAndView("/modules/weather/list");
	}
	
	@GetMapping("/cityId/{cityId}")
	public WeatherResponse getWeatherByCityId(@PathVariable("cityId") String cityId) {
		return weatherDataService.getDataByCityId(cityId);
	}
	
	@GetMapping("/cityName/{cityName}")
	public WeatherResponse getWeatherByCityName(@PathVariable("cityName") String cityName) {
		return weatherDataService.getDataByCityName(cityName);
	}

	@RequestMapping("/getLocalWeather")
	public WeatherResponse getLocalWeather(HttpServletRequest request) {
		SysUser sysUser= SysUserContext.getUser();
		if(sysUser!=null){
			String city=sysUser.getCity();
			if(StringUtils.isNotBlank(city)){
				return weatherDataService.getDataByCityName(city);
			}
		}
		return null;
	}
}

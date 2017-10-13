package io.practice.restAPI.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.practice.restAPI.repositories.BlackListedClientRepository;
import io.practice.restAPI.template.Country;

@Service
public class ApplicationValidationService {
	
	
	private  HashMap<String, Queue<Long>> countries;
	private int limitTimeFrame = 1000; // millisec
	private int limitNumber    = 3;		// applications/timeFrame
	private String countryCode;
	
	public ApplicationValidationService(BlackListedClientRepository blackListedClientRepo) {
		this.countries = new HashMap<String, Queue<Long>>();
	}
	
	
	
	
	
	public boolean TooManyRequests(HttpServletRequest request)
	{
		String ip = request.getRemoteAddr();
		countryCode = getCoutryCode(ip);
		
		Long time = System.currentTimeMillis();
		
		if(!countries.containsKey(countryCode))
		{
			countries.put(countryCode, (new LinkedList<Long>()));
			countries.get(countryCode).add(time);
			return false;
		}
		else
		{
			if(countries.get(countryCode).size() < limitNumber)
			{
				countries.get(countryCode).add(time);
				return false;
			}
			else {
				if (time - countries.get(countryCode).peek() > limitTimeFrame)
				{
					countries.get(countryCode).poll();
					countries.get(countryCode).add(time);
					return false;
				}
				else 
					return true;
			}
				
		}
	}
	
	public String getCoutryCode(String ip) 
	{
    	String uri = "http://ip-api.com/json" + "/" + ip;
	    RestTemplate restTemplate = new RestTemplate();
	    Country country = restTemplate.getForObject(uri, Country.class);
	    
	    if(country.status.equals("fail"))
	    	return "lv";
	    else
	    	return country.countryCode;
	}
	

	public String getCurrentCountryCode() {
		return countryCode;
	}

	public void setCurrentCountryCode(String currentCountryCode) {
		this.countryCode = currentCountryCode;
	}


}

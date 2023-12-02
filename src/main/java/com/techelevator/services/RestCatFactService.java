package com.techelevator.services;

import com.techelevator.model.CatCard;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatFact;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RestCatFactService implements CatFactService {

	private final String API_BASE_URL = "https://cat-data.netlify.app/api/facts/random";
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatFact getFact() {
		try {
			CatFact catFact = restTemplate.getForObject(API_BASE_URL, CatFact.class);
			return catFact;
		}
		catch(RestClientException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
		}
	}


}

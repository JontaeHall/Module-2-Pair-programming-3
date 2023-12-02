package com.techelevator.services;

import com.techelevator.model.CatFact;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RestCatPicService implements CatPicService {

	private final String API_BASE_URL = "https://cat-data.netlify.app/api/pictures/random";
	private final RestTemplate restTemplate = new RestTemplate();
	@Override
	public CatPic getPic() {
		try {
			CatPic catPic = restTemplate.getForObject(API_BASE_URL, CatPic.class);
			return catPic;
		}
		catch(RestClientException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
		}
	}

}	

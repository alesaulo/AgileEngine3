package org.AgileEngineExample.service;

import java.util.Optional;
import org.AgileEngineExample.Image;
import org.AgileEngineExample.model.Auth;
import org.AgileEngineExample.model.PictureBasicData;
import org.AgileEngineExample.model.Pictures;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public abstract class ImageFetchingService {

	private static final String URL = "http://interview.agileengine.com/",
		AUTH_URL = URL + "auth",
		IMAGE_URL = URL + "images",
		API_KEY = "{ \"apiKey\": \"23567b218376f79d9415\" }";
	
	private static Auth AUTH_TOKEN; 
		
	private static Pictures PICTURES;
	
	private static void performAuth() {		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Gson gson = new Gson();
		
	    headers.setContentType(MediaType.APPLICATION_JSON);	    
	    HttpEntity<String> request = 
	    	      new HttpEntity<String>(API_KEY, headers);	    
	    String result = restTemplate.postForObject(AUTH_URL, request, String.class);	    	    	    	   
	    
	    AUTH_TOKEN = gson.fromJson(result, Auth.class);
	}
	
	private static void loadImageData() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Gson gson = new Gson();
		boolean noMorePictures = false;
		int page = 2;
		
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setBearerAuth(AUTH_TOKEN.getToken());
	    
	    HttpEntity<String> request = new HttpEntity<String>(headers);	    
	    ResponseEntity<String> restp = restTemplate.exchange(IMAGE_URL, HttpMethod.GET, request, String.class);	    	    
	    Pictures pictures = gson.fromJson(restp.getBody(), Pictures.class);
	    
	    while(!noMorePictures) {
	    	restp = restTemplate.exchange(IMAGE_URL + "?page=" + page, HttpMethod.GET, request, String.class);
	    	Pictures morePictures = gson.fromJson(restp.getBody(), Pictures.class);
	    	
	    	if(morePictures.getPictureBasicData().isEmpty()) {
	    		noMorePictures = true;
	    	} else {
	    		pictures.getPictureBasicData().addAll(morePictures.getPictureBasicData());
	    		
	    		page++;
	    	}	    		    	
	    }
	    
	    PICTURES = pictures;
	}

	public static Optional<Image> getImage(String id) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Optional<Image> image = Optional.empty();
		
		Optional<PictureBasicData> pdb = PICTURES.getByID(id);
		
		if(pdb.isPresent()) {
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			HttpEntity<String> request = new HttpEntity<String>(headers);
			
			ResponseEntity<byte[]> restp = restTemplate
				.exchange(pdb.get().getCropped_picture(), HttpMethod.GET, request, byte[].class);
			
			image = Optional.of(new Image(restp.getBody()));
		}
				
		return image;
	}
	
	public static void initialize() {
		performAuth();
		loadImageData();
		CacheService.initialize(PICTURES);
	}
}

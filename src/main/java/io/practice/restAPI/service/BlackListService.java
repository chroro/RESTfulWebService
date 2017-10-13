package io.practice.restAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.practice.restAPI.entities.BlackListedClient;
import io.practice.restAPI.entities.Client;
import io.practice.restAPI.repositories.BlackListedClientRepository;

@Service
public class BlackListService {
	
	public final BlackListedClientRepository bListedRepo; 
	
	@Autowired
	public BlackListService(BlackListedClientRepository blackListedRepo) {
		this.bListedRepo = blackListedRepo;
	}
	
	public void addBlackListedClient(BlackListedClient bListedClient) {
		bListedRepo.save(bListedClient);
	}
	
	public boolean isBlackListed(Long id) {
		return (bListedRepo.findByClient(new Client(id)) != null ) ;
	}
	
	public void removBlackListedClient(Long id) {
		bListedRepo.delete(id);
	}

}

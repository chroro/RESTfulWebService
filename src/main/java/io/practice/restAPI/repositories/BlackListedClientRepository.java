package io.practice.restAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import io.practice.restAPI.entities.BlackListedClient;
import io.practice.restAPI.entities.Client;

public interface BlackListedClientRepository extends CrudRepository<BlackListedClient, Long>{
	public BlackListedClient findByClient(Client client);
}

package io.practice.restAPI.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class BlackListedClient {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(cascade = javax.persistence.CascadeType.ALL)
	private Client client;
	
	public BlackListedClient() {
		
	}
	public BlackListedClient(Client client) {
		this();
		this.client = client;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
}

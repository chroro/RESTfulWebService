package io.practice.restAPI.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ApprovedLoan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	
	@ManyToOne(cascade = javax.persistence.CascadeType.ALL)
	@JsonIgnore
	private Client client; 
	
	private String term;
	private int amount;
	private String countryCode;
	

	public ApprovedLoan() {
		
	}
	
	public ApprovedLoan(String term,int amount, String countryCode, Client client) {
		this();
		this.term = term;
		this.amount = amount;
		this.countryCode = countryCode;
		this.client = client;

	}
	
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long key) {
		this.loanId = key;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	
}

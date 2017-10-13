package io.practice.restAPI.template;

public class LoanApplication {
	private String name;
	private String surname;
	private Long personalID;
	private int amount;
	private String term;
	
	public LoanApplication() {
		
	}
	
	public LoanApplication(String name, String surname, Long personalID, int amount, String term) {
		this.name = name;
		this.surname = surname;
		this.personalID = personalID;
		this.amount = amount;
		this.term = term;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Long getPersonalID() {
		return personalID;
	}
	public void setPersonalID(Long personalID) {
		this.personalID = personalID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
}

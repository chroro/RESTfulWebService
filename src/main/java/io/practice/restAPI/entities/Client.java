package io.practice.restAPI.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

@Entity

public class Client {
	
	@Id
	@TableGenerator(name = "clientId",  allocationSize =1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="clientId")
	private Long id;
	private String name;
	private String surname;
	
	public Client() {
	}
	
	public Client(final Long id) {
		this();
		this.id = id;
		
	}
	public Client(String name, String surname) {
		this();
		this.name = name;
		this.surname = surname;
 
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		
}

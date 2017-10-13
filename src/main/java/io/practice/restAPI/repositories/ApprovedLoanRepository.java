package io.practice.restAPI.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import io.practice.restAPI.entities.Client;
import io.practice.restAPI.entities.ApprovedLoan;

public interface ApprovedLoanRepository extends CrudRepository<ApprovedLoan, Long>{
	public List<ApprovedLoan> findAll();
	public List<ApprovedLoan> findByClient(Client client);
	
}

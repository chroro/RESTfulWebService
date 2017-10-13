package io.practice.restAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.practice.restAPI.entities.ApprovedLoan;
import io.practice.restAPI.entities.Client;
import io.practice.restAPI.repositories.ApprovedLoanRepository;
import io.practice.restAPI.repositories.ClientRepository;
import io.practice.restAPI.template.LoanApplication;



@Service
public class LoanService {
	private final ApprovedLoanRepository loanRepo;
	private final ClientRepository clientRepo;
	
	@Autowired
	public LoanService( ApprovedLoanRepository loanRepo, ClientRepository clientRepo) {
		this.loanRepo = loanRepo;
		this.clientRepo = clientRepo; 
	}
	
	public List<ApprovedLoan> getAllApprovedLoans(){
		return loanRepo.findAll();
	}
	
	public List<ApprovedLoan> getLoanById(Long loanId){
		return loanRepo.findByClient(new Client(loanId));
	}
	
	public void addApprovedLoan(LoanApplication loanApplication, String countryCode) {
		Client client; 
		
		if(clientRepo.exists(loanApplication.getPersonalID()))
			client = (Client)clientRepo.findOne(loanApplication.getPersonalID());
		else
			client = new Client(loanApplication.getName(),loanApplication.getSurname());
		
		loanRepo.save(new ApprovedLoan(loanApplication.getTerm(),
				loanApplication.getAmount(),countryCode, client));
	}
	
}

package io.practice.restAPI.RESTcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.practice.restAPI.entities.ApprovedLoan;
import io.practice.restAPI.service.ApplicationValidationService;
import io.practice.restAPI.service.BlackListService;
import io.practice.restAPI.service.LoanService;
import io.practice.restAPI.template.LoanApplication;

@RestController
public class BankController {
	@Autowired
	private LoanService loanService;
	@Autowired
	private BlackListService bListedService;
	@Autowired 
	private ApplicationValidationService validationService;
	


	@RequestMapping( value = "/")
	public List<ApprovedLoan> getAllLoans(){
		return loanService.getAllApprovedLoans();
	}
	
	@RequestMapping( value = "/{id}")
	public List<ApprovedLoan> getLoansById(@PathVariable Long id){
		return loanService.getLoanById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/loan", consumes = "application/json")
	public ResponseEntity<?>  getLoansById(@RequestBody LoanApplication loan, HttpServletRequest request){
		
		if(bListedService.isBlackListed(loan.getPersonalID()))
			return new ResponseEntity<LoanApplication>(loan,HttpStatus.NOT_ACCEPTABLE);
		
		else if (validationService.TooManyRequests(request) )
			return new ResponseEntity<LoanApplication>(loan,HttpStatus.TOO_MANY_REQUESTS);
	
		else loanService.addApprovedLoan(loan, validationService.getCurrentCountryCode());
			return new ResponseEntity<LoanApplication>(loan,HttpStatus.ACCEPTED);
	}
	
	
}
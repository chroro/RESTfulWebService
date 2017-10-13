package io.practice.restAPI;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.practice.restAPI.entities.ApprovedLoan;
import io.practice.restAPI.entities.BlackListedClient;
import io.practice.restAPI.entities.Client;
import io.practice.restAPI.repositories.ApprovedLoanRepository;
import io.practice.restAPI.repositories.BlackListedClientRepository;
import io.practice.restAPI.repositories.ClientRepository;
import io.practice.restAPI.template.LoanApplication;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestService.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class ApplicationTest {
	
	ObjectMapper mapper = new ObjectMapper();

	LoanApplication loanApplication = new LoanApplication("Andy","Weinstein", 4L, 678, "Spring");
	LoanApplication blackListedClient = new LoanApplication("Lando","Vannata", 3L, 100, "Spring");

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
           Charset.forName("utf8"));
	
	private static boolean setUpDone = false;
	
	
	@Autowired
	private  WebApplicationContext webApplicationContext;
	@Autowired
	private BlackListedClientRepository bListedRepo; 
	@Autowired
	private ApprovedLoanRepository loanRepo;
	@Autowired
	private ClientRepository clientRepo;
	
	public static List<ApprovedLoan> loanList= new ArrayList();
	private  static MockMvc mockMvc ;

	@Before
	public void setup() throws Exception {
		
		
		if(!setUpDone) {
			
			
			mockMvc = webAppContextSetup(webApplicationContext).build();
			Client client;

			client = new Client("Kullua","Zoldyck"); // ID = 1
			loanList.add(loanRepo.save(new ApprovedLoan("Spring", 666, "kz", client)));
		
			client = new Client("Lando","Vannata"); // ID = 2
			loanList.add(loanRepo.save(new ApprovedLoan("Winter", 777, "us", client)));
		
			client = new Client("Lemon","Pearl"); // ID = 3 
			bListedRepo.save( new BlackListedClient(client));
			}
		
		setUpDone = true;
			
	}
	/*checkAllExistingClients*/
	@Test
	public void test1() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
	    			 .andExpect(status().isOk())
	    			 .andExpect(content().contentType(contentType))		
	    			 .andExpect(jsonPath("$", hasSize(2)))
	    			 .andExpect(jsonPath("$[0].term", is(loanList.get(0).getTerm())))
	    			 .andExpect(jsonPath("$[0].amount", is(loanList.get(0).getAmount())))
					 .andExpect(jsonPath("$[0].countryCode", is(loanList.get(0).getCountryCode())))
					 .andExpect(jsonPath("$[1].term", is(loanList.get(1).getTerm())))
	    			 .andExpect(jsonPath("$[1].amount", is(loanList.get(1).getAmount())))
					 .andExpect(jsonPath("$[1].countryCode", is(loanList.get(1).getCountryCode())));
					
					
					    
	}

	/*checkClientsFromTheList*/
	@Test
	public void test2() throws Exception {
		
	    this.mockMvc.perform(get("/1").accept(MediaType.APPLICATION_JSON_UTF8))
	    			.andExpect(status().isOk())
	    			.andExpect(content().contentType(contentType))		
	    			.andExpect(jsonPath("$", hasSize(1)))
	    			.andExpect(jsonPath("$[0].term", is(loanList.get(0).getTerm())))
	    			.andExpect(jsonPath("$[0].amount", is(loanList.get(0).getAmount())))
	    			.andExpect(jsonPath("$[0].countryCode", is(loanList.get(0).getCountryCode())));
	    

	    this.mockMvc.perform(get("/2").accept(MediaType.APPLICATION_JSON_UTF8))
	    			.andExpect(status().isOk())
	    			.andExpect(content().contentType(contentType))		
	    			.andExpect(jsonPath("$", hasSize(1)))
	    			.andExpect(jsonPath("$[0].term", is(loanList.get(1).getTerm())))
	    			.andExpect(jsonPath("$[0].amount", is(loanList.get(1).getAmount())))
	    			.andExpect(jsonPath("$[0].countryCode", is(loanList.get(1).getCountryCode())));
	}
	
	/*postingLoanApplication(*/
	@Test
	public void test3() throws Exception{
	
	    this.mockMvc.perform((post("/loan")).content(mapper.writeValueAsString(loanApplication))
	    			.contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isAccepted());
	    
	    this.mockMvc.perform(get("/4").accept(MediaType.APPLICATION_JSON_UTF8))
	    			.andExpect(status().isOk())
	    			.andExpect(content().contentType(contentType))		
	    			.andExpect(jsonPath("$", hasSize(1)))
	    			.andExpect(jsonPath("$[0].term", is("Spring")))
	    			.andExpect(jsonPath("$[0].amount", is(678)))
	    			.andExpect(jsonPath("$[0].countryCode", is("lv")));

	    
	}
	
	/*checkAddingBlackListedClient*/
	@Test
	public void test4() throws Exception{

	    this.mockMvc.perform((post("/loan")).content(mapper.writeValueAsString(blackListedClient) )
			.contentType(contentType))
			.andExpect(status().isNotAcceptable());
	}
	
	
	
	
	/*checkMaxNumberOfRequestsPerSec*/
	@Test
	public void test5() throws Exception{
		
		Thread.sleep(1000); // Wait for a new time frame
		for(int i = 0; i < 3; i ++ )
			this.mockMvc.perform((post("/loan"))
					.content(mapper.writeValueAsString(loanApplication))
					.contentType(contentType))
					.andExpect(status().isAccepted());
		
		this.mockMvc.perform((post("/loan"))
				.content(mapper.writeValueAsString(loanApplication ))
				.contentType(contentType))
				.andExpect(status().isTooManyRequests());
	}
	
	
	
	
	
	
	
	
	
	
}

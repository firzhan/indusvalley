package com.indusvalley.oauth.service;

import com.google.gson.Gson;
import com.indusvalley.oauth.dao.AccountRecoveryRepository;
import com.indusvalley.oauth.model.jpa.AccountRecovery;
import com.indusvalley.oauth.model.json.ResponseStatus;
import com.indusvalley.oauth.model.json.UserIDRequest;
import com.indusvalley.oauth.utils.Utils;

import javax.mail.MessagingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.UUID;

@Path("/indusvalley/account")
public class AccountRecoveryService {

	private AccountRecoveryRepository accountRecoveryRepository;

	public AccountRecoveryService(AccountRecoveryRepository accountRecoveryRepository) {
		this.accountRecoveryRepository = accountRecoveryRepository;
	}

	@POST
	@Path("/recovery/email")
	public Response initiateEmailRecovery(String jsonBody) throws UnsupportedEncodingException, MessagingException {


		//Check for valid user id
/*
		String insertionSQLString = String.format(SQLConstants.INSERT_ACCOUNT_RECOVERY_INFORMATION_FORMAT,emailID, UUID
				.randomUUID()
				.toString());*/
		System.out.println(jsonBody);
		Gson gson = new Gson();
		UserIDRequest accountRecoveryRequest = gson.fromJson(jsonBody, UserIDRequest.class);

		String emailId = accountRecoveryRequest.getEmailId();
		String uuidString = UUID.randomUUID().toString();
		System.out.println("Email ID:" + emailId);

		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		AccountRecovery accountRecovery = new AccountRecovery();
		accountRecovery.setToken(uuidString );
		accountRecovery.setUserID(accountRecoveryRequest.getEmailId());
		accountRecovery.setDateCreated(String.valueOf(startDate.getTime()));
		accountRecoveryRepository.createUser(accountRecovery);
		Utils.sendEmail(emailId, "Account Recovery", Utils.generateCombinedReport(emailId,uuidString),
				"firzhan007@gmail.com");

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOperation("EMAIL_RECOVERY");
		responseStatus.setStatus("EMAIL_SEND");
		return Response.ok().status(Response.Status.OK).entity(responseStatus).build();

		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}


	@GET
	@Path("/recovery/complete/{link}")
	public Response completeEmailRecovery(@PathParam("link") String link) throws UnsupportedEncodingException, MessagingException {

		String decodedURL = URLDecoder.decode(link, "UTF-8");
		String[] decStrings = decodedURL.split(":");

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOperation("EMAIL_RECOVERY");

		if(decStrings.length != 2){
			responseStatus.setStatus("RECOVERY-FAILED");
			return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseStatus).build();
		}

		AccountRecovery accountRecovery = accountRecoveryRepository.findUserAccount(decStrings[0]);

		if(accountRecovery == null){
			responseStatus.setStatus("RECOVERY-FAILED");
			return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseStatus).build();
		}

		if(accountRecovery.getToken().equals(decStrings[1])){
			accountRecoveryRepository.removeUser(accountRecovery);
			responseStatus.setStatus("RECOVERY-COMPLETE");
			return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		}

		responseStatus.setStatus("RECOVERY-FAILED");
		return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseStatus).build();

	}
}

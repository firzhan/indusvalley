package com.indusvalley.oauth.service;

import com.google.gson.Gson;
import com.indusvalley.oauth.dao.UserLoginRepository;
import com.indusvalley.oauth.dao.UserRegistrationRepository;
import com.indusvalley.oauth.dao.UserSessionRepository;
import com.indusvalley.oauth.model.jpa.UserInformation;
import com.indusvalley.oauth.model.jpa.UserLoginInformation;
import com.indusvalley.oauth.model.jpa.UserSession;
import com.indusvalley.oauth.model.jpa.UserSessionPK;
import com.indusvalley.oauth.model.json.ResponseStatus;
import com.indusvalley.oauth.model.json.UserIDRequest;
import com.indusvalley.oauth.model.json.UserIdAndPasswordRequest;
import com.indusvalley.oauth.model.json.UserRegistrationRequest;
import com.indusvalley.oauth.model.json.response.ErrorResponse;
import com.indusvalley.oauth.utils.Utils;
import com.nimbusds.jose.JOSEException;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.UUID;

@Path("/indusvalley/user")
public class UserService {

	private UserRegistrationRepository userRegistrationRepository;
	private UserLoginRepository userLoginRepository;
	private UserSessionRepository userSessionRepository;

	public UserService(UserRegistrationRepository userRegistrationRepository, UserLoginRepository
			userLoginRepository, UserSessionRepository userSessionRepository) {
		this.userRegistrationRepository = userRegistrationRepository;
		this.userLoginRepository = userLoginRepository;
		this.userSessionRepository = userSessionRepository;
	}


	@POST
	@Produces("application/json")
	@Path("/login")
	public Response loginUser(String jsonBody) throws ParseException, JOSEException, NoSuchAlgorithmException {


		Gson gson = new Gson();
		UserIdAndPasswordRequest userPasswordRequest = gson.fromJson(jsonBody, UserIdAndPasswordRequest.class);

		String userId = userPasswordRequest.getUserId();
		UserLoginInformation userLoginInformation = userLoginRepository.findUserAccount(userId);
		//ResponseStatus responseStatus = new ResponseStatus();
		//responseStatus.setOperation("LOGIN");

		if (userLoginInformation != null) {

			if(userLoginInformation.getPassword().equals(userPasswordRequest.getUserPassword())){

				UserSessionPK userSessionPK = new UserSessionPK();
				userSessionPK.setUserId(userId);
				userSessionPK.setAccessTokenSource("INDUSVALLEY");

				UserSession userSession = userSessionRepository.findUserAccount(userSessionPK);

				long creationTimeStamp = Utils.getGMTUnixTimeStamp();
				long expirationTimeStamp = Utils.addGMTUnixTimeStamp(creationTimeStamp);
				if(userSession == null){

					String systemGeneratedAccessToken = UUID.randomUUID().toString();
					String systemGeneratedRefreshToken = UUID.randomUUID().toString();


					userSession = new UserSession();
					userSession.setUserId(userId);
					userSession.setAccessToken("DIRECT_LOGIN");
					userSession.setAccessTokenType("Bearer");
					userSession.setAccessTokenSource("INDUSVALLEY");
					userSession.setSystemAccessToken(systemGeneratedAccessToken);
					userSession.setRefreshToken(systemGeneratedRefreshToken);
					userSession.setExpiresIn(String.valueOf(expirationTimeStamp));
					userSession.setDateCreated(String.valueOf(creationTimeStamp));
					userSession.setDateModified(String.valueOf(creationTimeStamp));
					userSession.setCreatedBy(userId);
					userSession.setModifiedBy(userId);

					userSessionRepository.createUser(userSession);
				}

				String finalJWTToken = Utils.createJWTToken("",
						userSession.getAccessToken(), userSession.getRefreshToken(),
						userSession.getAccessTokenType(), String.valueOf(creationTimeStamp),
						String.valueOf(expirationTimeStamp));

				//responseStatus.setStatus("SUCCESS");
				return Response.ok().status(Response.Status.OK).entity(finalJWTToken).build();
			}

		}

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCodes(Utils.ERROR_CODES.LOGIN_FAILURE);
		errorResponse.setOperation(Utils.OPERATION.LOGIN);
		errorResponse.setErrorDescription("Direct Login Failed");
		return Response.ok().status(Response.Status.OK).entity(errorResponse).build();
	}

	@POST
	@Produces("application/json")
	@Path("/profile")
	public Response login(String jsonBody) {

		Gson gson = new Gson();
		UserIDRequest userIDRequest = gson.fromJson(jsonBody, UserIDRequest.class);
		String email = userIDRequest.getEmailId();

		UserInformation userInformation = userRegistrationRepository.findUserAccount(email);
		if (userInformation != null) {
			return Response.ok().status(Response.Status.OK).entity(userInformation).build();
		}
		return Response.ok().status(Response.Status.OK).entity(new UserInformation()).build();
		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}


	@POST
	@Produces("application/json")
	@Path("/register")
	public Response registerUser(String jsonBody) {

		Gson gson = new Gson();
		UserRegistrationRequest userRegistrationRequest = gson.fromJson(jsonBody, UserRegistrationRequest.class);

		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		UserInformation userInformation = new UserInformation();
		userInformation.setUserId(userRegistrationRequest.getUserId());
		userInformation.setBirthday(userRegistrationRequest.getBirthday());
		userInformation.setCity(userRegistrationRequest.getCity());
		userInformation.setCountry(userRegistrationRequest.getCountry());
		userInformation.setDateCreated(String.valueOf(startDate.getTime()));
		userInformation.setFirstAddress(userRegistrationRequest.getFirstAddress());
		userInformation.setSecondAddress(userRegistrationRequest.getSecondAddress());
		userInformation.setFirstName(userRegistrationRequest.getFirstName());
		userInformation.setLastName(userRegistrationRequest.getLastName());
		userInformation.setGender(userRegistrationRequest.getGender());
		userInformation.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
		userInformation.setState(userRegistrationRequest.getState());

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOperation("USER-REGISTRATION");

		if (userRegistrationRepository.findUserAccount(userInformation.getUserId()) == null) {
			userRegistrationRepository.createUser(userInformation);
			responseStatus.setStatus("SUCCESS");
			return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		}

		responseStatus.setStatus("FAILURE");
		return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}

	@POST
	@Produces("application/json")
	@Path("/password")
	public Response registerUserPassword(String jsonBody) {

		Gson gson = new Gson();
		UserIdAndPasswordRequest userPasswordRequest = gson.fromJson(jsonBody, UserIdAndPasswordRequest.class);

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOperation("PASSWORD");

		if (userRegistrationRepository.findUserAccount(userPasswordRequest.getUserId()) != null) {


			UserLoginInformation userLoginInformation = new UserLoginInformation();
			userLoginInformation.setUserId(userPasswordRequest.getUserId());
			userLoginInformation.setPassword(userPasswordRequest.getUserPassword());

			UserLoginInformation userLoginInformationOld = userLoginRepository.findUserAccount(userPasswordRequest
					.getUserId());

			if(userLoginInformationOld != null){
				userLoginRepository.removeUser(userLoginInformationOld);
			}

			userLoginRepository.createUser(userLoginInformation);
			responseStatus.setStatus("SUCCESS");
			return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		}

		responseStatus.setStatus("FAILURE");
		return Response.ok().status(Response.Status.OK).entity(responseStatus).build();


	}

	@DELETE
	@Produces("application/json")
	@Path("/remove")
	public Response removeUser(String jsonBody) {

		Gson gson = new Gson();
		UserIDRequest userIDRequest = gson.fromJson(jsonBody, UserIDRequest.class);

		String email = userIDRequest.getEmailId();

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOperation("USER-REMOVE");

		UserInformation userInformation = userRegistrationRepository.findUserAccount(email);
		if (userInformation != null) {
			UserLoginInformation userLoginInformation = userLoginRepository.findUserAccount(userInformation.getUserId
					());

			if(userLoginInformation != null){
				userLoginRepository.removeUser(userLoginInformation);
				userRegistrationRepository.removeUser(userInformation);
			}

			responseStatus.setStatus("SUCCESS");
			return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		}

		responseStatus.setStatus("FAILURE");
		return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}


	@PUT
	@Produces("application/json")
	@Path("/update")
	public Response updateUser(String jsonBody) {

		Gson gson = new Gson();
		UserRegistrationRequest userRegistrationRequest = gson.fromJson(jsonBody, UserRegistrationRequest.class);

		String emailID = userRegistrationRequest.getUserId();

		UserInformation userInformation = userRegistrationRepository.findUserAccount(emailID);

		UserInformation userInformationUpdate = new UserInformation();


		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOperation("USER-PROFILE-UPDATE");


		if (userInformation != null) {

			userInformationUpdate.setBirthday(userRegistrationRequest.getBirthday());
			userInformationUpdate.setCity(userRegistrationRequest.getCity());
			userInformationUpdate.setCountry(userRegistrationRequest.getCountry());
			userInformationUpdate.setDateCreated(String.valueOf(startDate.getTime()));
			userInformationUpdate.setFirstAddress(userRegistrationRequest.getFirstAddress());
			userInformationUpdate.setSecondAddress(userRegistrationRequest.getSecondAddress());
			userInformationUpdate.setFirstName(userRegistrationRequest.getFirstName());
			userInformationUpdate.setLastName(userRegistrationRequest.getLastName());
			userInformationUpdate.setGender(userRegistrationRequest.getGender());
			userInformationUpdate.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
			userInformationUpdate.setState(userRegistrationRequest.getState());
			userInformationUpdate.setUserId(userInformation.getUserId());

			userRegistrationRepository.removeUser(userInformation);
			userRegistrationRepository.createUser(userInformationUpdate);
			responseStatus.setStatus("SUCCESS");
			return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		}

		responseStatus.setStatus("FAILURE");
		return Response.ok().status(Response.Status.OK).entity(responseStatus).build();
		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}
}


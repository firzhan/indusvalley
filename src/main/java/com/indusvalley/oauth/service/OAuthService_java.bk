/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.indusvalley.oauth.service;

import com.google.gson.Gson;
import com.indusvalley.oauth.dao.UserRegistrationRepository;
import com.indusvalley.oauth.dao.UserSessionRepository;
import com.indusvalley.oauth.model.jpa.UserInformation;
import com.indusvalley.oauth.model.jpa.UserSession;
import com.indusvalley.oauth.model.jpa.UserSessionPK;
import com.indusvalley.oauth.model.json.FBTokenResponse;
import com.indusvalley.oauth.model.json.GoogleTokenResponse;
import com.indusvalley.oauth.model.json.GoogleUserAuthResponse;
import com.indusvalley.oauth.model.json.ResponseStatus;
import com.indusvalley.oauth.model.json.callback.FBGraphAPIResponse;
import com.indusvalley.oauth.utils.Utils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 1.0
 */
@Path("/indusvalley/oauth2")
public class OAuthService {


	//public static final String FB_CLIENT_CALLBACK_URL = "http://openvalley.test.com:8080/oauth2/fb/callback";

	private static final List<String> googlePeopleAPIPropertiesList = new ArrayList<>();
	private static final List<String> fbPeopleAPIFieldsList = new ArrayList<>();

	private UserRegistrationRepository userRegistrationRepository;
	private UserSessionRepository userSessionRepository;

	static {
		googlePeopleAPIPropertiesList.add("names");
		googlePeopleAPIPropertiesList.add("addresses");
		googlePeopleAPIPropertiesList.add("birthdays");
		googlePeopleAPIPropertiesList.add("emailAddresses");
		googlePeopleAPIPropertiesList.add("genders");
		googlePeopleAPIPropertiesList.add("locales");
		googlePeopleAPIPropertiesList.add("phoneNumbers");
	}

	static {
		fbPeopleAPIFieldsList.add("id");
		fbPeopleAPIFieldsList.add("birthday");
		fbPeopleAPIFieldsList.add("email");
		fbPeopleAPIFieldsList.add("first_name");
		fbPeopleAPIFieldsList.add("gender");
		fbPeopleAPIFieldsList.add("location");
		fbPeopleAPIFieldsList.add("middle_name");
		fbPeopleAPIFieldsList.add("name");
		fbPeopleAPIFieldsList.add("timezone");
	}

	public OAuthService(UserRegistrationRepository userRegistrationRepository, UserSessionRepository
			userSessionRepository) {
		this.userRegistrationRepository = userRegistrationRepository;
		this.userSessionRepository = userSessionRepository;
	}

	@GET
	@Path("/test")
	public Response testLink() throws UnsupportedEncodingException {

		return Response.ok().status(Response.Status.FOUND).header("oauth-source", "GOOGLE").header("Location",
				"/indusvalley/oauth2").build();

		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}


	@GET
	@Path("/")
	public Response initiateOAuth(@Context org.wso2.msf4j.Request request) {

		//String oAuthSource = request.getHeader("oauth-source");
		String oAuthSource = "GOOGLE";

		if (oAuthSource.equals("GOOGLE")) {
			return Response.ok().status(Response.Status.FOUND).header("Location", "/indusvalley/google").build();
		} else if (oAuthSource.equals("FB")) {
			return Response.ok().status(Response.Status.FOUND).header("Location", "/indusvalley/fb").build();
		}

		return Response.ok().status(Response.Status.SERVICE_UNAVAILABLE).build();
	}

	@GET
	@Path("/fb")
	public Response get2() throws UnsupportedEncodingException {
		String fbURL = Utils.getFBAuthorizationURL();
		return Response.ok().status(Response.Status.FOUND).header("Location", fbURL).build();

		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}

	@GET
	@Path("/google")
	public Response initiateGoogleOAuth() {
		String googleAuthURL = "";
		try {
			String stateValue = URLEncoder.encode(Utils.GOOGLE_OAUTH2_STATE_SECURITY_TOKEN_CONSTANT + new BigInteger
					(130, new SecureRandom()).toString(32), "UTF-8");
			googleAuthURL = Utils.getGoogleAuthorizationURL(stateValue);
			System.out.println(googleAuthURL);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return Response.ok().status(Response.Status.FOUND).header("Location", googleAuthURL).build();

		// Create a state token to prevent request forgery.
		// Store it in the session for later validation.
	}

	/**
	 *
	 * Get Full Name, Location, DOB from Google's and FB's JWT token
	 * @param code
	 * @return
	 * @throws CertificateException
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws URISyntaxException
	 * @throws JOSEException
	 */

	@GET
	@Path("/callback/google")
	@Produces("application/json")
	public Response handleGoogleCallback(@QueryParam("code") String code) throws CertificateException, ParseException,
			NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException, URISyntaxException,
			JOSEException {
		System.out.println("code:" + code);

		String googleTokenEPBody = Utils.getGoogleTokenEPBody(code);

		Map<String, String> headerMap = new HashMap<>();

		byte[] encodedAuthorizationBytes = Base64.encodeBase64((Utils.GOOGLE_CLIENT_ID + ":" + Utils
				.GOOGLE_CLIENT_SECRET_ID)
				.getBytes());

		String authorizationHeader = Utils.AUTHORIZATION_HEADER_VALUE_PREFIX + new String(encodedAuthorizationBytes);
		headerMap.put(HttpHeaders.AUTHORIZATION, authorizationHeader);
		headerMap.put(HttpHeaders.CONTENT_TYPE, javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE.toString
				());

		String responseJWT = Utils.establishPostConnection(Utils.GOOGLE_TOKEN_EP, googleTokenEPBody, headerMap);

		System.out.println(responseJWT);
		Gson gson = new Gson();
		// Convert JSON to Java Object
		GoogleTokenResponse googleTokenResponse = gson.fromJson(responseJWT, GoogleTokenResponse
				.class);

		SignedJWT signedJWT = SignedJWT.parse(googleTokenResponse.getId_token());
		String email = (String) signedJWT.getJWTClaimsSet().getClaim("email");
		boolean emailVerified = (boolean) signedJWT.getJWTClaimsSet().getClaim("email_verified");

		if (emailVerified) {

			StringBuilder profileResponseOutputBuilder = new StringBuilder();

			UserSessionPK userSessionPK = new UserSessionPK();
			userSessionPK.setUserId(email);
			userSessionPK.setAccessTokenSource("GOOGLE");

			UserSession userSession = userSessionRepository.findUserAccount(userSessionPK);
			if (userSession == null) {

				String systemGeneratedAccessToken = UUID.randomUUID().toString();
				String systemGeneratedRefreshToken = UUID.randomUUID().toString();

				long creationTimeStamp = Utils.getGMTUnixTimeStamp();
				long expirationTimeStamp = Utils.addGMTUnixTimeStamp(creationTimeStamp);

				GoogleUserAuthResponse userAuthResponse = new GoogleUserAuthResponse();
				userAuthResponse.setCode(code);
				userAuthResponse.setEmail(email);

				headerMap.remove("authorization");

				List<String> profileResponseList = new ArrayList<>();
				for (String profileName : googlePeopleAPIPropertiesList) {
					String googlePeopleAPIEP = Utils.getGooglePeopleAPIEPURL(googleTokenResponse.getAccess_token(),
							profileName);
					System.out.println("googlePeopleAPIEP:" + googlePeopleAPIEP);
					String profileResponseInformation = Utils.establishGetConnection(googlePeopleAPIEP, headerMap);
					profileResponseList.add(profileResponseInformation);
				}

				for (String profileResponse : profileResponseList) {
					profileResponseOutputBuilder.append(profileResponse).append("\n");
				}

				System.out.println("profileResponseOutputBuilder:"+profileResponseOutputBuilder);

				userSession = new UserSession();
				userSession.setUserId(email);
				userSession.setAccessToken(googleTokenResponse.getAccess_token());
				userSession.setAccessTokenType(googleTokenResponse.getToken_type());
				userSession.setAccessTokenSource("GOOGLE");
				userSession.setSystemAccessToken(systemGeneratedAccessToken);
				userSession.setRefreshToken(systemGeneratedRefreshToken);
				userSession.setExpiresIn(String.valueOf(expirationTimeStamp));
				userSession.setDateCreated(String.valueOf(creationTimeStamp));
				userSession.setDateModified(String.valueOf(creationTimeStamp));
				userSession.setCreatedBy(email);
				userSession.setModifiedBy(email);

				userSessionRepository.createUser(userSession);
			}

			long creationTimeStamp = Utils.getGMTUnixTimeStamp();
			long expirationTimeStamp = Utils.addGMTUnixTimeStamp(creationTimeStamp);

			String finalJWTToken = Utils.createJWTToken(profileResponseOutputBuilder.toString(),
					userSession.getAccessToken(), userSession.getRefreshToken(),
					userSession.getAccessTokenType(), String.valueOf(creationTimeStamp),
					String.valueOf(expirationTimeStamp));

			return Response.ok().status(Response.Status.OK).entity(finalJWTToken)
					.build();
		}

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOperation("GOOGLE-AUTH");
		responseStatus.setStatus("FAILED");
		return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseStatus).build();
	}


	@GET
	@Path("/callback/fb")
	@Produces("application/json")
	public Response handleFBCallback(@QueryParam("code") String code) throws IOException, CertificateException,
			ParseException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, URISyntaxException,
			JOSEException {

		String fbTokenEPBody = Utils.getFBTokenEPBody(code);

		byte[] encodedAuthorizationBytes = Base64.encodeBase64((Utils.FB_CLIENT_ID + ":" + Utils
				.FB_CLIENT_SECRET_ID)
				.getBytes());

		String authorizationHeader = Utils.AUTHORIZATION_HEADER_VALUE_PREFIX + new String(encodedAuthorizationBytes);

		Map<String, String> headerMap = new HashMap<>();
		headerMap.put(HttpHeaders.CONTENT_TYPE, javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE.toString
				());
		headerMap.put(HttpHeaders.AUTHORIZATION, authorizationHeader);

		String responseJWTToken = Utils.establishPostConnection(Utils.FB_TOKEN_EP, fbTokenEPBody, headerMap);
		Gson gson = new Gson();
		// Convert JSON to Java Object
		FBTokenResponse fbTokenResponse = gson.fromJson(responseJWTToken, FBTokenResponse
				.class);
		String fbGraphBody = Utils.getFBGraphAPIBody(fbPeopleAPIFieldsList);
		headerMap.clear();
		headerMap.put(HttpHeaders.AUTHORIZATION, "Bearer " + fbTokenResponse.getAccess_token());
		String peopleResponse = Utils.establishPostConnection(Utils.FB_GRAPH_API_EP, fbGraphBody, headerMap);

		gson = new Gson();
		// Convert JSON to Java Object
		FBGraphAPIResponse fbGraphAPIResponse = gson.fromJson(peopleResponse, FBGraphAPIResponse.class);

		UserSessionPK userSessionPK = new UserSessionPK();
		userSessionPK.setUserId(fbGraphAPIResponse.getEmail());
		userSessionPK.setAccessTokenSource("FB");
		UserSession userSession = userSessionRepository.findUserAccount(userSessionPK);

		long creationTimeStamp = Utils.getGMTUnixTimeStamp();
		long expirationTimeStamp = Utils.addGMTUnixTimeStamp(creationTimeStamp);

		if (userSession == null) {

			userSession = new UserSession();
			userSession.setUserId(fbGraphAPIResponse.getEmail());
			userSession.setAccessToken(fbTokenResponse.getAccess_token());
			userSession.setAccessTokenType(fbTokenResponse.getToken_type());
			userSession.setAccessTokenSource("FB");
			userSession.setSystemAccessToken(UUID.randomUUID().toString());
			userSession.setRefreshToken(UUID.randomUUID().toString());
			userSession.setExpiresIn(String.valueOf(expirationTimeStamp));
			userSession.setDateCreated(String.valueOf(creationTimeStamp));
			userSession.setDateModified(String.valueOf(creationTimeStamp));
			userSession.setCreatedBy(fbGraphAPIResponse.getEmail());
			userSession.setModifiedBy(fbGraphAPIResponse.getEmail());

			userSessionRepository.createUser(userSession);
		} else {
			peopleResponse = "";
		}

		peopleResponse = Utils.
				createJWTToken(peopleResponse, userSession.getAccessToken(), userSession.getRefreshToken(),
						userSession.getAccessTokenType(), String.valueOf(creationTimeStamp),
						String.valueOf(expirationTimeStamp));

		return Response.ok().status(Response.Status.OK).entity(peopleResponse).build();
	}
}

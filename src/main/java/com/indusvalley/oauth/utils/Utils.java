package com.indusvalley.oauth.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class Utils {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static final String GOOGLE_CLIENT_ID = "472178500768-8ggfmo6pun0402o1enu035td3p1m6fji.apps" +
			".googleusercontent.com";
	public static final String FB_CLIENT_ID = "1830179383962166";
	public static final String FB_CLIENT_SECRET_ID = "96957e6a5306effa27c2dc9ece3198b6";

	public static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Basic ";
	public static final String GOOGLE_CLIENT_SECRET_ID = "6zHpBUzpLzC6KaRgyXcNL4u4";
	public static final String CLIENT_ID_STRING = "client_id";
	public static final String FB_CLIENT_ID_STRING = "1830179383962166";

	public static final String GOOGLE_OAUTH2_URL = "https://accounts.google.com/o/oauth2/v2/auth";
	public static final String FB_OAUTH2_URL = "https://www.facebook.com/dialog/oauth";

	public static final String OAUTH2_RESPONSE_TYPE_CONSTANT = "response_type";
	public static final String OAUTH2_SCOPE_CONSTANT = "scope";
	public static final String OAUTH2_CODE_CONSTANT = "code";
	public static final String OAUTH2_GRANT_TYPE_CONSTANT = "grant_type";
	public static final String OAUTH2_ACCESS_TOKEN_CONSTANT = "access_token";
	public static final String OAUTH2_GOOGLE_PERSON_FIELDS_CONSTANT = "personFields";
	public static final String OAUTH2_FB_PERSON_FIELDS_CONSTANT = "fields";
	public static final String OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE_VALUE = "authorization_code";
	public static final String GOOGLE_OAUTH2_STATE_CONSTANT = "state";
	public static final String GOOGLE_OAUTH2_STATE_SECURITY_TOKEN_CONSTANT = "security_token=";
	public static final String OAUTH2_REDIRECT_URI_CONSTANT = "redirect_uri";

	public static final String OAUTH2_RESPONSE_TYPE_VALUE = "code";
	public static final String GOOGLE_OAUTH2_SCOPE_VALUE = "openid%20email%20https%3A%2F%2Fwww.googleapis" +
			".com%2Fauth%2Fplus.login%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuser.addresses" +
			".read%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuser.birthday.read%20https%3A%2F%2Fwww.googleapis" +
			".com%2Fauth%2Fuser.emails.read%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuser.phonenumbers" +
			".read%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis" +
			".com%2Fauth%2Fuserinfo.profile%20";
	public static final String FB_OAUTH2_SCOPE_VALUE =
			"public_profile%20email%20user_birthday%20user_hometown%20user_location%20user_website" +
					"%20user_work_history" +
					"%20user_about_me";


	public static final String GOOGLE_CLIENT_CALLBACK_URL = "http://indusvalley.westeurope.cloudapp.azure" +
			".com:8080/indusvalley/oauth2/callback/google";
	public static final String FB_CLIENT_CALLBACK_URL = "http://indusvalley.westeurope.cloudapp.azure" +
			".com:8080/indusvalley/oauth2/callback/fb";

	public static final String GOOGLE_TOKEN_EP = "https://www.googleapis.com/oauth2/v4/token";
	public static final String GOOGLE_PEOPLE_API_EP = "https://people.googleapis.com/v1/people/me";
	public static final String FB_GRAPH_API_EP = "https://graph.facebook.com/v2.8/me";

	public static final String SHARED_KEY = "a0a2abd8-6162-41c3-83d6-1cf559b46afc";

	public static final String FB_TOKEN_EP = "https://graph.facebook.com/oauth/access_token";

	public static enum ERROR_CODES{
		LOGIN_FAILURE
	}

	public static enum OPERATION{
		LOGIN
	}
	public static String getFBAuthorizationURL() throws UnsupportedEncodingException {

		return FB_OAUTH2_URL + "?" +
				CLIENT_ID_STRING + "=" + FB_CLIENT_ID_STRING + "&" +
				OAUTH2_RESPONSE_TYPE_CONSTANT + "=" +
				OAUTH2_RESPONSE_TYPE_VALUE +
				"&" +
				OAUTH2_SCOPE_CONSTANT + "=" + FB_OAUTH2_SCOPE_VALUE + "&" +
				OAUTH2_REDIRECT_URI_CONSTANT + "=" + URLEncoder
				.encode(FB_CLIENT_CALLBACK_URL, "UTF-8");
	}

	public static String getGoogleAuthorizationURL(String stateValue) throws UnsupportedEncodingException {

		return GOOGLE_OAUTH2_URL + "?" +
				CLIENT_ID_STRING + "=" + GOOGLE_CLIENT_ID + "&" +
				OAUTH2_RESPONSE_TYPE_CONSTANT + "=" +
				OAUTH2_RESPONSE_TYPE_VALUE +
				"&" +
				OAUTH2_SCOPE_CONSTANT + "=" + GOOGLE_OAUTH2_SCOPE_VALUE + "&" +
				GOOGLE_OAUTH2_STATE_CONSTANT + "=" + stateValue + "&" +
				OAUTH2_REDIRECT_URI_CONSTANT + "=" + URLEncoder
				.encode(GOOGLE_CLIENT_CALLBACK_URL, "UTF-8");
	}

	public static String getGoogleTokenEPBody(String code) throws UnsupportedEncodingException {


		return
				OAUTH2_GRANT_TYPE_CONSTANT + "=" + OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE_VALUE + "&" +
						OAUTH2_CODE_CONSTANT + "=" + URLEncoder.encode(code, "UTF-8") + "&" +
						OAUTH2_REDIRECT_URI_CONSTANT + "=" + URLEncoder.encode(GOOGLE_CLIENT_CALLBACK_URL,
						"UTF-8");
	}

	public static String getGooglePeopleAPIEPURL(String accessToken, String profileName) throws
			UnsupportedEncodingException {

		return GOOGLE_PEOPLE_API_EP + "?" +
				OAUTH2_ACCESS_TOKEN_CONSTANT + "=" + accessToken + "&" +
				OAUTH2_GOOGLE_PERSON_FIELDS_CONSTANT + "=" + profileName;
	}

	public static String getFBTokenEPBody(String code) throws UnsupportedEncodingException {

		return
				OAUTH2_GRANT_TYPE_CONSTANT + "=" + OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE_VALUE + "&" +
						CLIENT_ID_STRING + "=" + URLEncoder.encode(Utils.FB_CLIENT_ID, "UTF-8") + "&" +
						OAUTH2_CODE_CONSTANT + "=" + URLEncoder.encode(code, "UTF-8") + "&" +
						OAUTH2_REDIRECT_URI_CONSTANT + "=" + URLEncoder.encode(FB_CLIENT_CALLBACK_URL,
						"UTF-8");
	}

	public static String getFBGraphAPIBody(List<String> fbPeopleAPIFieldsList) throws
			UnsupportedEncodingException {

		StringBuilder filedValuesBuilder = new StringBuilder();
		int filedValuesCount = fbPeopleAPIFieldsList.size();
		for (int count = 0; count < filedValuesCount; count++) {

			filedValuesBuilder.append(fbPeopleAPIFieldsList.get(count));

			if (count < filedValuesCount - 1) {
				filedValuesBuilder.append(",");
			}
		}


		return OAUTH2_FB_PERSON_FIELDS_CONSTANT + "=" + filedValuesBuilder.toString();
	}

	public static String establishGetConnection(String url, Map<String, String> headerMap) throws IOException,
			CertificateException,
			NoSuchAlgorithmException,
			KeyStoreException, KeyManagementException, ParseException, URISyntaxException {

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(new TrustSelfSignedStrategy())
				.build();

		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, new
				NoopHostnameVerifier());

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
		HttpGet httpGet = new HttpGet(url);

		for (Map.Entry<String, String> pair : headerMap.entrySet()) {
			httpGet.setHeader(pair.getKey(), pair.getValue());
		}

		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity httpEntity = response.getEntity();

		Scanner scanner = new Scanner(httpEntity.getContent()).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}

	public static String establishPostConnection(String url, String body, Map<String, String> headerMap) throws
			IOException,
			CertificateException,
			NoSuchAlgorithmException,
			KeyStoreException, KeyManagementException, ParseException, URISyntaxException {
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(
						new TrustSelfSignedStrategy())
				.build();

		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, new
				NoopHostnameVerifier());

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();

		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, String> pair : headerMap.entrySet()) {
			httpPost.setHeader(pair.getKey(), pair.getValue());
		}

		HttpEntity httpPostEntity = new StringEntity(body);
		httpPost.setEntity(httpPostEntity);


		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity httpEntity = response.getEntity();

		Scanner scanner = new Scanner(httpEntity.getContent()).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}


	public static String createJWTToken(String jwtPayload, String accessToken, String refreshToken, String tokenType
			, String creationTimeStamp, String expiresTimeStamp)
			throws
			NoSuchAlgorithmException,
			JOSEException,
			ParseException {

		JWSHeader jwsHeader = (new JWSHeader.Builder(JWSAlgorithm.HS256)).contentType("JWT").build();
		// Create HMAC signer
		JWSSigner signer = new MACSigner(Utils.SHARED_KEY.getBytes());

		// Prepare JWT with claims set

		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject("joe").expirationTime(new Date(1300819380 * 1000l)).issuer("abc").claim("output",
						jwtPayload).claim("access_token", accessToken).
						claim("refresh_token", refreshToken).
						claim("token_type", tokenType).
						claim("creation_time", creationTimeStamp).
						claim("expires_in", expiresTimeStamp).
						build();

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSet);

		// Apply the HMAC
		signedJWT.sign(signer);

		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(signedJWT));

		// Serialise JWS object to compact format
		return signedJWT.serialize();
	}

	public static boolean verifyJWTToken(String jwtToken) throws ParseException, JOSEException {

		// Parse back and check signature
		SignedJWT signedJWT = SignedJWT.parse(jwtToken);
		JWSVerifier verifier = new MACVerifier(Utils.SHARED_KEY.getBytes());

		try {
			return signedJWT.verify(verifier);
		} catch (JOSEException e) {
			System.err.println("Couldn't verify signature: " + e.getMessage());
		}

		return false;
	}

	public static void sendEmail(String receiver, String subject, String body, String sender)
			throws MessagingException, MessagingException {
		final String user = "firzhan007@gmail.com";
		final String password = "Gsoc2015!";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		};

		Session session = Session.getInstance(properties, auth);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(sender));
		msg.setRecipients(Message.RecipientType.TO, getAddressArray(receiver));
		msg.setSubject(subject);
		msg.setSentDate(Calendar.getInstance().getTime());
		msg.setContent(body, "text/html");

		Transport.send(msg);
	}

	private static InternetAddress[] getAddressArray(String fieldValue) throws AddressException {
		StringTokenizer s = new StringTokenizer(fieldValue, ",");
		InternetAddress[] addresses = new InternetAddress[s.countTokens()];
		for (int i = 0; i < addresses.length; i++) {
			addresses[i] = new InternetAddress(s.nextToken().trim());
		}
		return addresses;
	}

	public static String generateCombinedReport(String emailID, String uuID) throws UnsupportedEncodingException {

		String encodedString = URLEncoder.encode(emailID + ":" + uuID, "UTF-8");
		String combinedReport = "";
		combinedReport += "<div style='background-color:black; text-align:center ; color:white; line-height:40px; " +
				"font-size:20px; vertical-align:middle'>Account recovery Information</div>";
		String link = "http://localhost:8080/indusvalley/account/recovery/complete/" + encodedString;
		combinedReport += "<p>Email ID:" + emailID + "</p><br/>";
		combinedReport += "<p>Email Link:" + link + "</p><br/>";

		return combinedReport;
	}

	public static long getGMTUnixTimeStamp() throws ParseException {
		DateFormat gmtDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return (gmtDateFormat.parse(gmtDateFormat.format(new Date(Instant.now().getEpochSecond() * 1000))).getTime())
				/1000;
	}

	public static long addGMTUnixTimeStamp(long currentTimeStamp) throws ParseException {
		DateFormat gmtDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return (gmtDateFormat.parse(gmtDateFormat.format(new Date((currentTimeStamp + (3600 * 24 * 30 * 6)) * 1000)))
				.getTime())/1000;
	}
}

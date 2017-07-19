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

package com.indusvalley.oauth;

import com.indusvalley.oauth.dao.AccountRecoveryRepository;
import com.indusvalley.oauth.dao.UserLoginRepository;
import com.indusvalley.oauth.dao.UserRegistrationRepository;
import com.indusvalley.oauth.dao.UserSessionRepository;
import com.indusvalley.oauth.service.AccountRecoveryService;
import com.indusvalley.oauth.service.OAuthService;
import com.indusvalley.oauth.service.UserService;
import org.wso2.msf4j.MicroservicesRunner;

import javax.persistence.Persistence;

/**
 * Application entry point.
 *
 * @since 1.0
 */
public class Application {
	public static void main(String[] args) {
		new MicroservicesRunner()
				.deploy(new OAuthService(getUserInformationRepository(), getUserSessionRepository()))
				.deploy(new AccountRecoveryService(getAccountRecoveryRepository()))
				.deploy(new UserService(getUserInformationRepository(), getUserLoginRepository(),
						getUserSessionRepository()))
				.start();
	}

	public static AccountRecoveryRepository getAccountRecoveryRepository() {
		return new AccountRecoveryRepository(Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa"));
	}

	public static UserRegistrationRepository getUserInformationRepository() {
		return new UserRegistrationRepository(Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa"));
	}

	public static UserLoginRepository getUserLoginRepository() {
		return new UserLoginRepository(Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa"));
	}

	public static UserSessionRepository getUserSessionRepository() {
		return new UserSessionRepository(Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa"));
	}
}

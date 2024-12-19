package com.joboffers.domain.loginandregister;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginAndRegisterFacadeConfiguration {

    @Bean
    public LoginAndRegisterFacade loginAndRegisterFacade(LoginRepository loginRepository) {
        return new LoginAndRegisterFacade(loginRepository);
    }
}

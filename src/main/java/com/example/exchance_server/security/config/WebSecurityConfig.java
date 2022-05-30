package com.example.exchance_server.security.config;

import com.example.exchance_server.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v*/registerPerson/**",
                        "/api/v*/registerOrganization/**",
                        "/api/v*/authentification/**",
                        "/api/v*/projectconstructor/publishProject/**",
                        "/api/v*/projectconstructor/getLastProject/**",
                        "/api/v*/projectconstructor/getAllProjects/**",
                        "/api/v*/projectconstructor/addUserToProject/**",
                        "/api/v*/projectconstructor/subscribeToProject/**",
                        "/api/v*/projectconstructor/getProjectSubscriptions/**",
                        "/api/v*/projectconstructor/setLikeToProject/**",
                        "/api/v*/admin/publishManual/**",
                        "/api/v*/admin/deleteLastManual/**",
                        "/api/v*/getManual/**",
                        "/api/v*/getUserByToken/**",
                        "/api/v*/getFullUserByToken/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}

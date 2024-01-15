package com.group.libraryapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class UserConfiguration {
    //UserRepostory를 반환하는 메소드를 만듦
/*    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate){
        return new UserRepository(jdbcTemplate);
    }*/
}

package com.fkocak.springrestdemo;

import com.fkocak.springrestdemo.user.UserDetails;
import com.fkocak.springrestdemo.user.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDetailsRepository userDetailsRepository;

//    public UserDetailsCommandLineRunner(UserDetailsRepository userDetailsRepository) {
//        this.userDetailsRepository = userDetailsRepository;
//    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(Arrays.toString(args));

        userDetailsRepository.save(new UserDetails("Furkan","Developer"));

        System.out.println(userDetailsRepository.findByRole("Developer").getName());

        List<UserDetails> users = userDetailsRepository.findAll();

        users.forEach(userDetails -> logger.info(userDetails.getName()+userDetails.getRole()));
    }
}

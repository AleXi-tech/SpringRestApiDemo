package com.fkocak.springrestdemo.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {

    //Custom Method
    UserDetails findByRole(String role);
}

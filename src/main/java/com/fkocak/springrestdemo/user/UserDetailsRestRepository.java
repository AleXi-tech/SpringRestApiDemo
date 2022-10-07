package com.fkocak.springrestdemo.user;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails,Long> { //Few additional features (JpaRep)

    //Custom Method
    UserDetails findByRole(String role);
}

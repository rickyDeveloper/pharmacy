package com.pharmacy.repository;

import com.pharmacy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vikasnaiyar on 20/06/18.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}

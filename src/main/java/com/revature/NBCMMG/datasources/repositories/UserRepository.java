package com.revature.NBCMMG.datasources.repositories;

import com.revature.NBCMMG.datasources.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);
}

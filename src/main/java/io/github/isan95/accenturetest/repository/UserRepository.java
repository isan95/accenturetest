package io.github.isan95.accenturetest.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.isan95.accenturetest.entity.UserApp;

@Repository
public interface UserRepository extends CrudRepository<UserApp, Long>{
	 Optional<UserApp> findByUsername(String ndoc);
	    
	 Boolean existsByUsername(String ndoc);
	
}

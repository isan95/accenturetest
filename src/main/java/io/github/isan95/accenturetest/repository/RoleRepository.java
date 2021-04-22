package io.github.isan95.accenturetest.repository;

import java.util.Optional;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.isan95.accenturetest.entity.ERole;
import io.github.isan95.accenturetest.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

	Optional<Role> findByName(ERole name);
}

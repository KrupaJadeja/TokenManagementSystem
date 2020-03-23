package com.token.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.token.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

	Optional<User> findByUserNameOrEmail(String username, String email);

	Boolean existsByUserName(String username);

	Boolean existsByEmail(String email);
	
	@Query("Select u From User u where u.role != 'Admin'")
	List<User> getOnlyUsers();
	
	User findByEmail(String email);
	
	User findByResetToken(String token);

}

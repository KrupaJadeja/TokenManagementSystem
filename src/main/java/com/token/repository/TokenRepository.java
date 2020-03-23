package com.token.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.token.entity.Token;
import com.token.entity.User;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>, JpaSpecificationExecutor<Token> {

	@Query("Select t From Token t ORDER BY t.priority ASC")
	List<Token> getTokeniestByPriority();

	@Query("Select t From Token t WHERE department=?1 and called='No' ORDER BY priority DESC")
	List<Token> getTokenForCall(String department);
	
	@Query("Select t From Token t WHERE assignedUser=?1 and department=?2 and counter=?3")
	List<Token> getTokenForStop(@Param("user") User user,@Param("department") String department,@Param("counter") String counter);

	@Transactional	
	@Modifying
	@Query("Delete From Token WHERE assignedUser=?1 and department=?2 and counter=?3")
	void deletebyDepartment(@Param("user") User user,@Param("department") String department,@Param("counter") String counter);
}
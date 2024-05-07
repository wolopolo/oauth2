package com.wolopolo.oauth2.repository;

import com.wolopolo.oauth2.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
}

package com.wolopolo.oauth2.repository;

import com.wolopolo.oauth2.entity.Oauth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2RegisteredClientRepo extends JpaRepository<Oauth2RegisteredClient, String>, JpaSpecificationExecutor<Oauth2RegisteredClient> {
}

package com.wolopolo.oauth2.repository;

import com.wolopolo.oauth2.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepo extends CrudRepository<VerificationToken, String> {
}

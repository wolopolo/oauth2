package com.wolopolo.oauth2.repository.specification;

import com.wolopolo.oauth2.dto.account.AccountSearchReq;
import com.wolopolo.oauth2.entity.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {
    public static Specification<Account> search(AccountSearchReq searchReq) {
        return Specification.where(likeEmail(searchReq.getEmail()))
                .and(likeName(searchReq.getName()))
                .and(likeRole(searchReq.getRole()))
                .and(isVerified(searchReq.getIsVerified()))
                .and(isActive(searchReq.getIsActive()));
    }

    public static Specification<Account> likeEmail(String email) {
        if(StringUtils.isBlank(email)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%"));
    }

    public static Specification<Account> likeName(String name) {
        if(StringUtils.isBlank(name)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }

    public static Specification<Account> likeRole(String role) {
        if(StringUtils.isBlank(role)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("role"), "%" + role + "%"));
    }

    public static Specification<Account> isVerified(Boolean isVerified) {
        if(isVerified == null) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isVerified"), isVerified));
    }

    public static Specification<Account> isActive(Boolean isActive) {
        if(isActive == null) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive));
    }
}

package com.wolopolo.oauth2.repository.specification;

import com.wolopolo.oauth2.dto.registeredclient.RegisteredClientSearchReq;
import com.wolopolo.oauth2.entity.Oauth2RegisteredClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class Oauth2RegisteredClientSpecification {
    public static Specification<Oauth2RegisteredClient> search(RegisteredClientSearchReq searchReq) {
        return Specification.where(likeClientId(searchReq.getClientId()));
    }

    public static Specification<Oauth2RegisteredClient> likeClientId(String clientId) {
        if(StringUtils.isBlank(clientId)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("clientId"), "%" + clientId + "%"));
    }

    public static Specification<Oauth2RegisteredClient> equalClientId(String clientId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("clientId"), clientId));
    }
}

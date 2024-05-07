package com.wolopolo.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonSearchRequest {
    private int page = 1;
    private int size = 20;
    @JsonSetter(nulls = Nulls.SKIP)
    private String sortBy = "createdDate";
    @JsonSetter(nulls = Nulls.SKIP)
    private String sortType = "DESC";
}

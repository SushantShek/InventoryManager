package com.example.warehouse.manager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "product")
public class Products implements Serializable {
    static final long serialVersionUID = -687991400503928843L;
    private String name;
    @JsonUnwrapped
    @JsonProperty("contain_articles")
    private List<Article> article;
}

package com.example.warehouse.manager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "art_id",
        "name",
        "stock"
})
@JsonRootName(value = "inventory")
public class Inventory implements Serializable {
    static final long serialVersionUID = -687991409288405033L;
    @JsonProperty("art_id")
    private Long artId;
    private String name;
    private Long stock;
}

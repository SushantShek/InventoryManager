package com.example.warehouse.manager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "art_id",
        "amount_of",
        "quantity"
})
public class Article implements Serializable {
    static final long serialVersionUID = -687991492884005033L;

    @JsonProperty("art_id")
    private Long artId;
    @JsonProperty("amount_of")
    private Long price;
    private Long quantity;
}

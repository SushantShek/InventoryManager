package com.example.warehouse.manager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class InventoryStock implements Serializable {
    static final long serialVersionUID = -687991409288405033L;
    @JsonUnwrapped
    private List<Inventory> inventory;
}

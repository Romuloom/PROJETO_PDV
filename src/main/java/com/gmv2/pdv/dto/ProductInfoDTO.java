package com.gmv2.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfoDTO {
    private long id;
    private String description;
    private int quantity;
}

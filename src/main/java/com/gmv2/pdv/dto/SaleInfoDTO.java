package com.gmv2.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleInfoDTO {
    private String User;
    private String date;
    private List<ProductInfoDTO> products;
}
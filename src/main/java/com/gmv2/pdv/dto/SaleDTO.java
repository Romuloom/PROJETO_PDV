package com.gmv2.pdv.dto;

import com.gmv2.pdv.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private long userid;

    List<ProductDTO> items;
}

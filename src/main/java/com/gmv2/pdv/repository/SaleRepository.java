package com.gmv2.pdv.repository;

import com.gmv2.pdv.entity.Product;
import com.gmv2.pdv.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}

package com.gmv2.pdv.repository;

import com.gmv2.pdv.entity.ItemSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleRepository extends JpaRepository<ItemSale , Long> {

}
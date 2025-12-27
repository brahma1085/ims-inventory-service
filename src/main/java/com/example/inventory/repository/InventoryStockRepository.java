package com.example.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.entity.InventoryStock;

public interface InventoryStockRepository extends JpaRepository<InventoryStock, Long> {

	boolean existsBySkuCode(String skuCode);

	List<InventoryStock> findByItem_Id(Long itemId);

}

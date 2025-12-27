package com.example.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.inventory.entity.InventoryItem;

public interface InventoryItemRepository
		extends JpaRepository<InventoryItem, Long>, PagingAndSortingRepository<InventoryItem, Long> {

	boolean existsByName(String name);

	Page<InventoryItem> findByNameContainingIgnoreCase(String name, Pageable pageable);

	@Query("""
			SELECT i FROM InventoryItem i
			LEFT JOIN i.stocks s
			WHERE
			  LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))
			  OR LOWER(s.skuCode) LIKE LOWER(CONCAT('%', :search, '%'))
			""")
	Page<InventoryItem> search(@Param("search") String search, Pageable pageable);

}

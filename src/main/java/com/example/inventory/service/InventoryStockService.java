package com.example.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventory.dto.CreateInventoryStockRequestDto;
import com.example.inventory.dto.InventoryStockDto;
import com.example.inventory.entity.InventoryItem;
import com.example.inventory.entity.InventoryStock;
import com.example.inventory.exception.ResouceNotFoundExcpetion;
import com.example.inventory.mapper.InventoryStockMapper;
import com.example.inventory.repository.InventoryItemRepository;
import com.example.inventory.repository.InventoryStockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryStockService {

	private final InventoryStockRepository stockRepo;
	private final InventoryStockMapper stockMapper;
	private final InventoryItemRepository itemRepo;

	@Transactional
	public InventoryStockDto update(Long id, CreateInventoryStockRequestDto dto) {
		InventoryStock stock = stockRepo.findById(id)
				.orElseThrow(() -> new ResouceNotFoundExcpetion("Stock Not Found with ID: " + id));

		existsBySkuCode(dto);

		stock.setSkuCode(dto.getSkuCode());
		stock.setQuantity(dto.getQuantity());
		log.debug("Inventory stock to update for id: {}", id);
		return stockMapper.toDto(stockRepo.save(stock));
	}

	@Transactional
	public void delete(Long id) {
		InventoryStock stock = stockRepo.findById(id)
				.orElseThrow(() -> new ResouceNotFoundExcpetion("Stock Not Found with ID: " + id));
		stockRepo.delete(stock);
		log.debug("Inventory stock deleted: {}", id);
	}

	@Transactional
	public InventoryStockDto createStock(Long itemId, CreateInventoryStockRequestDto dto) {
		InventoryItem item = itemRepo.findById(itemId).orElseThrow(
				() -> new ResouceNotFoundExcpetion("Stock Service - createStock - InventoryItem Not Found: " + itemId));

		existsBySkuCode(dto);

		InventoryStock stock = stockMapper.toEntity(dto);
		stock.setItem(item);
		return stockMapper.toDto(stockRepo.save(stock));
	}

	private void existsBySkuCode(CreateInventoryStockRequestDto dto) {
		if (stockRepo.existsBySkuCode(dto.getSkuCode())) {
			throw new IllegalArgumentException(dto.getSkuCode() + "SKU Already Exists!");
		}
	}

}

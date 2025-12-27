package com.example.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.CreateInventoryStockRequestDto;
import com.example.inventory.dto.InventoryStockDto;
import com.example.inventory.service.InventoryStockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/inventory/stock")
@RequiredArgsConstructor
public class InventoryStockController {

	private final InventoryStockService stockService;

	@PostMapping("/{itemId}")
	@PreAuthorize("hasRole('ADMIN')")
	public InventoryStockDto createStock(@PathVariable Long itemId, @RequestBody CreateInventoryStockRequestDto dto) {
		log.debug("InventoryStockController ==> createStock: itemId: ", itemId, dto);
		return stockService.createStock(itemId, dto);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public InventoryStockDto updateStock(@PathVariable Long id, @RequestBody CreateInventoryStockRequestDto dto) {
		log.debug("InventoryStockController ==> updateStock: id: ", id, dto);
		return stockService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStock(@PathVariable Long id) {
		log.debug("InventoryStockController ==> deleteStock: id: ", id);
		stockService.delete(id);
	}
}

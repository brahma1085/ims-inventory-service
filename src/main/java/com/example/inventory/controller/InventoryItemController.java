package com.example.inventory.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.InventoryItemDto;
import com.example.inventory.entity.InventoryItem;
import com.example.inventory.service.InventoryItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryItemController {

	private final InventoryItemService service;

	@GetMapping
	public Page<InventoryItem> all(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction, @RequestParam(required = false) String search) {
		log.debug("received request InventoryItemController.all(): ");
		return service.findAll(pageNo, size, sortBy, direction, search);
	}

	@GetMapping("/{id}")
	public InventoryItemDto one(@PathVariable Long id) {
		log.debug("received request InventoryItemController.one(id): " + id);
		return service.findById(id);
	}

	@PutMapping("/{id}")
	public InventoryItemDto update(@PathVariable Long id, @RequestBody InventoryItemDto dto) {
		log.debug("received request InventoryItemController.update: " + dto.toString());
		return service.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		log.debug("received request InventoryItemController.delete(id): " + id);
		service.delete(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public InventoryItemDto create(@Valid @RequestBody InventoryItemDto dto) {
		log.debug("received request InventoryItemController.create: " + dto.toString());
		return service.createItem(dto);
	}
}

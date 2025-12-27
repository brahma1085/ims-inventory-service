package com.example.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventory.dto.InventoryItemDto;
import com.example.inventory.entity.InventoryItem;
import com.example.inventory.exception.ResouceNotFoundExcpetion;
import com.example.inventory.mapper.InventoryItemMapper;
import com.example.inventory.repository.InventoryItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryItemService {

	private final InventoryItemRepository itemRepo;
	private final InventoryItemMapper itemMapper;

	// without pagination
	public List<InventoryItemDto> findAll() {
		return itemRepo.findAll().stream().map(itemMapper::toDto).toList();
	}

	// with pagination
	public Page<InventoryItem> findAll(int pageNo, int size, String sortBy, String direction, String search) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNo, size, sort);
		if (search == null || search.isBlank()) {
			return itemRepo.findAll(pageable);
		}
		// return itemRepo.findByNameContainingIgnoreCase(search, pageable); //search by
		// name
		return itemRepo.search(search, pageable); // multi-column search name,skuCode
	}

	public InventoryItemDto findById(Long id) {
		return itemMapper.toDto(itemRepo.findById(id).orElseThrow());
	}

	@Transactional
	public InventoryItemDto createItem(InventoryItemDto reqDto) {
		if (itemRepo.existsByName(reqDto.getName())) {
			throw new IllegalStateException("Inventory item already exists with name: " + reqDto.getName());
		}
		return itemMapper.toDto(itemRepo.save(itemMapper.toEntity(reqDto)));
	}

	@Transactional
	public InventoryItemDto update(Long id, InventoryItemDto dto) {
		log.debug("InventoryItemService ==> update: id, dto", id, dto.toString());
		InventoryItem item = itemRepo.findById(id)
				.orElseThrow(() -> new ResouceNotFoundExcpetion("Inventory Item Not found: " + id));
		item.setName(dto.getName());
		item.setPrice(dto.getPrice());
		item.setStock(dto.getStock());
		return itemMapper.toDto(itemRepo.save(item));
	}

	@Transactional
	public void delete(Long id) {
		log.debug("InventoryItemService ==> delete: ", id);
		InventoryItem item = itemRepo.findById(id)
				.orElseThrow(() -> new ResouceNotFoundExcpetion("Inventory Item Not found: " + id));
		itemRepo.delete(item);
		log.debug("InventoryItemService ==> deleted: ", item);
	}
}

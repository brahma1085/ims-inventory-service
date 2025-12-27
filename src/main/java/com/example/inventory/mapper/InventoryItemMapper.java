package com.example.inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventory.dto.InventoryItemDto;
import com.example.inventory.entity.InventoryItem;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {

	InventoryItemDto toDto(InventoryItem entity);

	@Mapping(target = "stocks", ignore = true)
	InventoryItem toEntity(InventoryItemDto dto);
}

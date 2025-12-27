package com.example.inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.inventory.dto.CreateInventoryStockRequestDto;
import com.example.inventory.dto.InventoryStockDto;
import com.example.inventory.entity.InventoryStock;

@Mapper(componentModel = "spring")
public interface InventoryStockMapper {

	@Mapping(source = "item.id", target = "itemId")
	InventoryStockDto toDto(InventoryStock entity);

	@Mapping(target = "item", ignore = true)
	InventoryStock toEntity(CreateInventoryStockRequestDto dto);
}

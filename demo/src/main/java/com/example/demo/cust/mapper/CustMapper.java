package com.example.demo.cust.mapper;

import com.example.demo.cust.entity.CustEntity;
import com.example.demo.cust.dto.CustDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustMapper {

    CustDto toDto(CustEntity custEntity);
}

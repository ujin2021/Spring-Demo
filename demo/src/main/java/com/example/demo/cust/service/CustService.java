package com.example.demo.cust.service;

import com.example.demo.cust.dto.CustDto;
import com.example.demo.cust.entity.CustEntity;
import com.example.demo.cust.mapper.CustMapper;
import com.example.demo.cust.repository.CustRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustService {

    private final CustRepository custRepository;
    private final CustMapper custMapper;

    // cust 단건조회
    public CustDto findById(Long id) {
        CustEntity custEntity = custRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id가 없습니다"));
        return custMapper.toDto(custEntity);
    }

    // cust 전체조회
    public List<CustDto> findAll() {
        return custRepository.findAll().stream()
                .map(custMapper::toDto)
                .collect(Collectors.toList());
    }
}

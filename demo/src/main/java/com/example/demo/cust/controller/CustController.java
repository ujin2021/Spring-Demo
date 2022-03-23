package com.example.demo.cust.controller;

import com.example.demo.cust.dto.CustDto;
import com.example.demo.cust.service.CustService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cust/v1")
@RequiredArgsConstructor
public class CustController {

    private final CustService custService;

    @GetMapping("/{id}")
    public CustDto findById(@PathVariable Long id) {
        return custService.findById(id);
    }

    @GetMapping
    public List<CustDto> findAll() {
        return custService.findAll();
    }

    @PutMapping("/{id}")
    public CustDto updateById(@PathVariable Long id) {

    }
}

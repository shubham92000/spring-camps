package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.currency.CreateCurrencyReqDto;
import com.bootcamp.springcamp.dtos.currency.CurrencyResDto;
import com.bootcamp.springcamp.services.CurrencyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {
    private final Logger log = LoggerFactory.getLogger(CurrencyController.class);

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    ResponseEntity<List<CurrencyResDto>> getAllCurrency(){
        var res = currencyService.getAllCurrency();
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    ResponseEntity<List<CurrencyResDto>> addCurrency(@Valid @RequestBody CreateCurrencyReqDto createCurrencyReqDto){
        var res = currencyService.addCurrency(createCurrencyReqDto);
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("{id}")
    ResponseEntity<CurrencyResDto> updateCurrency(@PathVariable("id") String id){
        var res = currencyService.updateCurrency(id);
        return ResponseEntity.ok(res);
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteCurrency(@PathVariable("id") String id){
        var res = currencyService.deleteCurrency(id);
        return ResponseEntity.ok(res);
    }
}

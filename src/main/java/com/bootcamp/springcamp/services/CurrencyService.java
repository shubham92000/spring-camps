package com.bootcamp.springcamp.services;



import com.bootcamp.springcamp.dtos.currency.CreateCurrencyReqDto;
import com.bootcamp.springcamp.dtos.currency.CurrencyResDto;

import java.util.List;

public interface CurrencyService {
    List<CurrencyResDto> getAllCurrency();
    List<CurrencyResDto> addCurrency(CreateCurrencyReqDto createCurrencyReqDto);
    CurrencyResDto updateCurrency(String id);
    String deleteCurrency(String id);
}

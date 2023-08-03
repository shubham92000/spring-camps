package com.bootcamp.springcamp.repos;

import com.bootcamp.springcamp.models.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CurrencyRepo extends MongoRepository<Currency, String> {
    Optional<Currency> findByCurrencyCode(String currencyCode);
    Optional<Currency> findByCountry(String country);
}

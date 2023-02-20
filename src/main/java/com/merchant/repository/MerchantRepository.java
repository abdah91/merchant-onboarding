package com.merchant.repository;

import com.merchant.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MerchantRepository  extends MongoRepository<Merchant, String> {

    @Query("SELECT m FROM Merchant m WHERE (m.name like %:search% or m.email like %:search% or m.phone like %:search%  or m.address like %:search%)")
    Page<Merchant> findAll(@Param("search") String search, Pageable pageable);

    @Query("SELECT m FROM Merchant m ")
    Page<Merchant> findAll( Pageable pageable);

    Merchant findByEmail(@Param("email") String email);

}

package com.merchant.repository;

import com.merchant.model.Merchant;
import com.merchant.model.MerchantHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MerchantHistoryRepository extends MongoRepository<MerchantHistory, String> {

    List<MerchantHistory> findByMerchant(String merchant);

}

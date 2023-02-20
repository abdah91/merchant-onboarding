package com.merchant.service;

import com.merchant.model.MerchantHistory;
import com.merchant.model.dto.MerchantDto;
import com.merchant.utils.CommonFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MerchantService  {
	MerchantDto createMerchant(MerchantDto merchant);
	MerchantDto updateMerchant(MerchantDto merchant);
	boolean deleteMerchant(String id);
	MerchantDto getMerchant(String id);
	public Page<MerchantDto> getAllMerchants(Pageable pageable, CommonFilter filter);

    List<MerchantHistory> getMerchantChangeHistory(String merchantId);
}

package com.merchant.controller;

import com.merchant.config.Authorized;
import com.merchant.constants.AppConstant;
import com.merchant.controller.advice.RestException;
import com.merchant.model.MerchantHistory;
import com.merchant.model.dto.MerchantDto;
import com.merchant.service.MerchantService;
import com.merchant.utils.CommonFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/merchant",  headers = {"Authorization"})
public class MerchantController {
	@Autowired
	private MerchantService merchantService;


	@DeleteMapping("/{merchantId}")
	@Authorized
	public ResponseEntity delete(@PathVariable String merchantId , HttpServletRequest servletRequest) {
		String responseStatus = "";
		try {
			if (!merchantService.deleteMerchant(merchantId)) {
				log.error("Failed to delete the Merchant record.");
				throw new EmptyResultDataAccessException(0);
			}
			responseStatus = String.format(AppConstant.DELETE_SUCCESS_MESSAGE,"Merchant");
		} catch(IllegalArgumentException ex) {
			log.error("Failed to delete the Merchant record Exception -> {}",ex.getMessage());
			throw new RestException(HttpStatus.UNPROCESSABLE_ENTITY,ex.getLocalizedMessage());
		} catch(EmptyResultDataAccessException ex) {
			log.error("Failed to delete the Merchant record Exception -> {}",ex.getMessage());
			throw new RestException(HttpStatus.NOT_FOUND, String.format(AppConstant.NOT_FOUND_MESSAGE, "Merchant"));
		}
		log.info("Merchant record deleted successfully.");
		return new ResponseEntity<>( responseStatus, HttpStatus.OK);
	}


	@PutMapping
	@Authorized
	public ResponseEntity<MerchantDto> updateMerchant(@RequestBody MerchantDto merchantDto , HttpServletRequest servletRequest) throws UnknownHostException {
		ResponseEntity<MerchantDto> responseDTO;
		try {
			
			if (null==merchantService.updateMerchant(merchantDto)) {
				log.error("Failed to update the Merchant record.");
				throw new EmptyResultDataAccessException(0);
			}

			responseDTO = new ResponseEntity<>(merchantDto, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			log.error("Failed to update the Merchant record Exception -> {}",ex.getMessage());
			throw new RestException(HttpStatus.UNPROCESSABLE_ENTITY,ex.getLocalizedMessage());
		} catch(EmptyResultDataAccessException ex) {
			log.error("Failed to update the Merchant record Exception -> {}",ex.getMessage());
			throw new RestException(HttpStatus.NOT_FOUND, String.format(AppConstant.NOT_FOUND_MESSAGE, "Merchant"));
		}
		log.info("Merchant record updated successfully.");
		return responseDTO;
	}


	@PostMapping
	@Authorized
	public ResponseEntity<MerchantDto> create(@RequestBody MerchantDto merchantDto , HttpServletRequest servletRequest) throws UnknownHostException {
		ResponseEntity<MerchantDto> responseDTO;
		try {

			merchantDto = merchantService.createMerchant(merchantDto);
			responseDTO = new ResponseEntity<>(merchantDto, HttpStatus.OK);
		} catch(IllegalArgumentException ex) {
			log.error("Failed to create Merchant record Exception -> {}",ex.getLocalizedMessage());
			throw new RestException(HttpStatus.UNPROCESSABLE_ENTITY,ex.getLocalizedMessage());
		}
		log.info("Merchant record created successfully.");
		return responseDTO;
	}


	@GetMapping("/{merchantId}")
	@Authorized
	public ResponseEntity<MerchantDto> get(@PathVariable String merchantId, HttpServletRequest servletRequest) {
		MerchantDto merchantDto = merchantService.getMerchant(merchantId);
		if (null == merchantDto || null == merchantDto.getId()) {
			log.error("Unable to find merchant record with Merchant-id: {}.",merchantId);
			throw new RestException();
		}
		log.debug("Fetched Merchant record with Merchant-id: {}.",merchantId);
		return new ResponseEntity<>(merchantDto, HttpStatus.OK);
	}


	@GetMapping
	@Authorized
	public ResponseEntity<Page<MerchantDto>> getAll(@PageableDefault(size = 10, page = 0, value = 10) Pageable pageable
			, HttpServletRequest servletRequest
			, @RequestParam(value = "filter", required = false) CommonFilter filter
			) throws Exception {

		Page<MerchantDto> allMerchants = merchantService.getAllMerchants(pageable, filter);
		log.debug("Fetched {} Merchant records with filters: {}.",allMerchants.getSize(),filter);
		return new ResponseEntity<>(allMerchants, HttpStatus.OK);
	}

	@GetMapping("/changes/{merchantId}")
	@Authorized
	public ResponseEntity<List<MerchantHistory>> getHistory(@PathVariable String merchantId
			, HttpServletRequest servletRequest) {
		List<MerchantHistory> merchantHistory = merchantService.getMerchantChangeHistory(merchantId);
		if (null == merchantHistory || merchantHistory.size()<=0 ) {
			log.error("Unable to find merchant history record with Merchant-id: {}.",merchantId);
			throw new RestException(HttpStatus.OK, "Unable to find merchant history record with Merchant-id: {}."+merchantId);
		}
		log.debug("Fetched Merchant history record with Merchant-id: {}.",merchantId);
		return new ResponseEntity<>(merchantHistory, HttpStatus.OK);
	}

}

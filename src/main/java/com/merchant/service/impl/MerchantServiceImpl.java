package com.merchant.service.impl;

import com.merchant.exception.EntityNotFoundException;
import com.merchant.model.EmailDetails;
import com.merchant.model.Merchant;
import com.merchant.model.MerchantHistory;
import com.merchant.model.dto.MerchantDto;
import com.merchant.repository.MerchantHistoryRepository;
import com.merchant.repository.MerchantRepository;
import com.merchant.service.EmailService;
import com.merchant.service.MerchantService;
import com.merchant.utils.CommonFilter;
import com.merchant.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantHistoryRepository merchantHistoryRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    SlackClient slackClient;

    @Autowired
    private ModelMapper modelMapper;

    public Merchant map(MerchantDto merchantDto) {
        Merchant obj = null;
        if (null != merchantDto) {
            obj = modelMapper.map(merchantDto, Merchant.class);
        }
        return obj;
    }

    public MerchantDto map(Merchant merchant) {
        MerchantDto obj = null;
        if (null != merchant) {
            obj = modelMapper.map(merchant, MerchantDto.class);
        }
        return obj;
    }

    public Page<MerchantDto> map(Page<Merchant> merchants) {
        if (null != merchants) {
            return merchants.map(this::map);
        }
        return null;
    }


    @Override
    public MerchantDto createMerchant(MerchantDto merchantDto) {
        Merchant merchant = new Merchant();
        merchant = map(merchantDto);
        merchant.setCreatedDate(new Date());
        merchant.setStatus("Pending");
        merchant.setOtp(CommonUtils.getOTP());
        slackClient.sendMessage("Merchant is Created with name"+merchantDto.getName());
        emailService.sendSimpleMail(new EmailDetails(merchantDto.getEmail(),
                "Merchant is Created with name"+merchantDto.getName() +" and OPT("+merchant.getOtp()+")", "Merchant Created"));

        merchant = merchantRepository.save(merchant);
        MerchantHistory merchantHistory = new MerchantHistory();
        merchantHistory.setMerchant(merchant.getId());
        merchantHistory.setDate(new Date());
        merchantHistory.setActivity("new Merchant is created with email:"+ merchant.getEmail());
        merchantHistoryRepository.save(merchantHistory);
        return map(merchant);
    }

    @Override
    public MerchantDto updateMerchant(MerchantDto merchantDto) {
        Merchant merchant = merchantRepository.findById(merchantDto.getId()).orElseThrow(() -> new EntityNotFoundException("Merchant not found"));
        if(!merchant.getName().equals(merchantDto.getName())){
            MerchantHistory merchantHistory = new MerchantHistory();
            merchantHistory.setMerchant(merchant.getId());
            merchantHistory.setDate(new Date());
            merchantHistory.setActivity("Merchant name changed from "+merchant.getName()+" to"+ merchantDto.getName());
            merchantHistoryRepository.save(merchantHistory);
        }
        merchant.setName(merchantDto.getName());

        if(!merchant.getEmail().equals(merchantDto.getEmail())){
            MerchantHistory merchantHistory = new MerchantHistory();
            merchantHistory.setMerchant(merchant.getId());
            merchantHistory.setDate(new Date());
            merchantHistory.setActivity("Merchant Email changed from "+merchant.getEmail()+" to"+ merchantDto.getEmail());
            merchantHistoryRepository.save(merchantHistory);
        }
        merchant.setEmail(merchantDto.getEmail());
        if(!merchant.getPhone().equals(merchantDto.getPhone())){
            MerchantHistory merchantHistory = new MerchantHistory();
            merchantHistory.setMerchant(merchant.getId());
            merchantHistory.setDate(new Date());
            merchantHistory.setActivity("Merchant Phone changed from "+merchant.getPhone()+" to"+ merchantDto.getPhone());
            merchantHistoryRepository.save(merchantHistory);
        }
        merchant.setPhone(merchantDto.getPhone());
        if(!merchant.getLicenseNumber().equals(merchantDto.getLicenseNumber())){
            MerchantHistory merchantHistory = new MerchantHistory();
            merchantHistory.setMerchant(merchant.getId());
            merchantHistory.setDate(new Date());
            merchantHistory.setActivity("Merchant LicenseNumber changed from "+merchant.getLicenseNumber()+" to"+ merchantDto.getLicenseNumber());
            merchantHistoryRepository.save(merchantHistory);
        }

        merchant.setAddress(merchantDto.getAddress());
        if(!merchant.getIdentificationNumber().equals(merchantDto.getIdentificationNumber())){
            MerchantHistory merchantHistory = new MerchantHistory();
            merchantHistory.setMerchant(merchant.getId());
            merchantHistory.setDate(new Date());
            merchantHistory.setActivity("Merchant IdentificationNumber changed from "+merchant.getIdentificationNumber()+" to"+ merchantDto.getIdentificationNumber());
            merchantHistoryRepository.save(merchantHistory);
        }
        merchant.setIdentificationNumber(merchantDto.getIdentificationNumber());
        if(!merchant.getAddress().equals(merchantDto.getAddress())){
            MerchantHistory merchantHistory = new MerchantHistory();
            merchantHistory.setMerchant(merchant.getId());
            merchantHistory.setDate(new Date());
            merchantHistory.setActivity("Merchant Address changed from "+merchant.getAddress()+" to"+ merchantDto.getAddress());
            merchantHistoryRepository.save(merchantHistory);
        }
        merchant.setLicenseNumber(merchantDto.getLicenseNumber());

        merchant = merchantRepository.save(merchant);

        MerchantHistory merchantHistory = new MerchantHistory();
        merchantHistory.setMerchant(merchant.getId());
        merchantHistory.setDate(new Date());
        merchantHistory.setActivity("Merchant data is updated with email:"+ merchant.getEmail());
        merchantHistoryRepository.save(merchantHistory);
        return map(merchant);
    }

    @Override
    public boolean deleteMerchant(String id) {
        try{
            Merchant merchant = merchantRepository.findById(id).get();

            MerchantHistory merchantHistory = new MerchantHistory();
            merchantHistory.setMerchant(merchant.getId());
            merchantHistory.setDate(new Date());
            merchantHistory.setActivity("Merchant OTP is verified with email:"+ merchant.getEmail());

            merchantHistoryRepository.save(merchantHistory);

            merchantRepository.delete(merchant);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public MerchantDto getMerchant(String id) {
        return map(merchantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Merchant not found")));
    }

    @Override
    public Page<MerchantDto> getAllMerchants(Pageable pageable, CommonFilter filter) {

        if(filter!=null && filter.get("search")!=null){
            return map(merchantRepository.findAll((String) filter.get("search").get(), pageable));
        }
        return map(merchantRepository.findAll( pageable));
    }

    @Override
    public List<MerchantHistory> getMerchantChangeHistory(String merchantId){

        return merchantHistoryRepository.findByMerchant(merchantId);
    }

}
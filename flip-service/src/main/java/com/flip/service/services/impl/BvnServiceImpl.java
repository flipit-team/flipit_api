package com.flip.service.services.impl;

import com.flip.service.pojo.request.BvnVerificationRequest;
import com.flip.service.pojo.response.BaseResponse;
import com.flip.service.services.BvnService;
import org.springframework.stereotype.Service;

/**
 * @author Charles on 11/12/2022
 */
@Service
public class BvnServiceImpl implements BvnService {

    @Override
    public BaseResponse verifyBvn(BvnVerificationRequest bvnRequest) {
        return new BaseResponse("00", "Successful");
    }
}

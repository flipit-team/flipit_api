package com.flip.service.services;

import com.flip.service.pojo.request.BvnVerificationRequest;
import com.flip.service.pojo.response.BaseResponse;

/**
 * @author Charles on 11/12/2022
 */
public interface BvnService {

    BaseResponse verifyBvn(BvnVerificationRequest bvnRequest);
}

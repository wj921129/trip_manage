package com.jeesite.modules.finance.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RefundOrderService {

    @Value("${api.host}")
    private String apiHost;


}

package com.example.serviceanalyse.Interfaces;

import com.example.serviceanalyse.Dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/biochar/user-service/account")
public interface AccountClient {
    @GetMapping("{id}")
    public ResponseEntity<AccountDto> SelectById (@PathVariable long id);
}

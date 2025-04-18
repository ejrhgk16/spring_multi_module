package com.library;

import com.library.feign.KakaoClient;
import com.library.feign.NaverClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients(clients = {NaverClient.class, KakaoClient.class})
@SpringBootApplication//모듈을 만들어서 구동시키려면
public class LibrarySearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibrarySearchApplication.class, args);
    }
}

package com.library.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.exception.ApiException;
import com.library.exception.ErrorType;
import com.library.NaverErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NaverErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    public NaverErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);//바디를 읽어서 스트링으로 변환
            NaverErrorResponse errorResponse = objectMapper.readValue(body, NaverErrorResponse.class);
            throw new ApiException(errorResponse.getErrorMessage(), ErrorType.EXTERNAL_API_ERROR, HttpStatus.valueOf(response.status()));
        } catch (IOException e) {
            log.error("[Naver] 에러 메세지 파싱 에러 code={}, request={}, methodKey={}, errorMessage={}", response.status(), response.request(), methodKey, e.getMessage());
            throw new ApiException("네이버 메세지 파싱에러", ErrorType.EXTERNAL_API_ERROR, HttpStatus.valueOf(response.status()));
        }
    }
}

package com.library.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
//
//@Schema: Swagger/OpenAPI 문서화를 위한 어노테이션으로, API 문서에서 이 클래스의 설명을 제공합니다.
//
//        record: Java 14에서 도입된 불변(immutable) 데이터 객체를 간단히 정의하는 새로운 유형의 클래스입니다.
//
//<T>: 제네릭 타입 파라미터로, 페이징 결과의 내용물 타입을 유연하게 지정할 수 있게 합니다.


@Schema(description = "페이징 결과")
public record PageResult<T>(
        @Schema(description = "현재 페이지번호", example = "1")
        int page,
        @Schema(description = "페이지 크기", example = "10")
        int size,
        @Schema(description = "전체 요소수", example = "100")
        int totalElements,
        @Schema(description = "본문")
        List<T> contents) {
}

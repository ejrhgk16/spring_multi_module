package com.library.controller

import com.library.controller.request.SearchRequest
import com.library.controller.response.PageResult
import com.library.controller.response.SearchResponse
import com.library.service.BookQueryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerItTest extends Specification {
    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookQueryService bookQueryService = Mock()
//
//    MockMvc
//            정의: MockMvc는 웹 애플리케이션을 서버에 배포하지 않고도 스프링 MVC의 동작을 재현할 수 있게 해주는 테스트 프레임워크입니다.
//
//            목적:
//
//    HTTP 요청을 시뮬레이션하여 컨트롤러를 테스트합니다.
//
//            전체 스프링 MVC 인프라를 사용하지 않고도 컨트롤러의 동작을 검증할 수 있습니다.
//
//            특징:
//
//    실제 서버를 구동하지 않아 테스트 속도가 빠릅니다.
//
//            스프링의 웹 애플리케이션 컨텍스트를 로드하여 통합 테스트 환경을 제공합니다.
//
//            perform 메소드
//    정의: MockMvc의 메소드로, 가상의 HTTP 요청을 수행합니다.

    def "정상인자로 요청시 성공한다."() {
        given:
        def request = new SearchRequest(query: "HTTP", page: 1, size: 10)

        and:
        bookQueryService.search(*_) >> new PageResult<>(1, 10, 10, [Mock(SearchResponse)])

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("page", request.page.toString())
                .param("size", request.size.toString()))

        then:
        result.andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath('$.totalElements').value(10))
                .andExpect(jsonPath('$.page').value(1))
                .andExpect(jsonPath('$.size').value(10))
                .andExpect(jsonPath('$.contents').isArray())
    }

    def "query가 비어있을때 BadRequest 응답반환된다."() {
        given:
        def request = new SearchRequest(query: "", page: 1, size: 10)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("page", request.page.toString())
                .param("size", request.size.toString()))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errorMessage').value("입력은 비어있을 수 없습니다."))
    }

    def "page가 음수일경우에 BadRequest 응답반환된다."() {
        given:
        def request = new SearchRequest(query: "HTTP", page: -10, size: 10)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("page", request.page.toString())
                .param("size", request.size.toString()))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errorMessage').value("페이지번호는 1이상이어야 합니다."))
    }

    def "size가 50을 초과하면 BadRequest 응답반환된다."() {
        given:
        def request = new SearchRequest(query: "HTTP", page: 1, size: 51)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.query)
                .param("page", request.page.toString())
                .param("size", request.size.toString()))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errorMessage').value("페이지크기는 50이하여야 합니다."))
    }
}

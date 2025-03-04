package com.library.util

import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class DateUtilsTest extends Specification {
    def "문자열(yyyyMMdd)을 LocalDate 객체로 변환한다."() {
        given:
        def date = "20240101"

        when:
        def result = DateUtils.parseYYYYMMDD(date)

        then:
        result == LocalDate.of(2024, 1, 1)
    }

    def "ISO_OFFSET_DATE_TIME 형식의 문자열을 LocalDateTime 객체로 변환한다."() {
        given:
        def datetime = "2024-01-01T14:30:00+09:00"

        when:
        def result = DateUtils.parseOffsetDateTime(datetime)

        then:
        result == LocalDateTime.of(2024, 1, 1, 14, 30)
    }
}

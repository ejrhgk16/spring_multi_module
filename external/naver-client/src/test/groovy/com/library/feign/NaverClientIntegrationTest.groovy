import com.library.feign.NaverClient
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@SpringBootTest(classes = NaverClientIntegrationTest.TestConfig.class)
@ActiveProfiles("test")
class NaverClientIntegrationTest extends Specification {

//    @EnableAutoConfiguration
//    Spring Boot의 자동 구성 기능을 활성화합니다1.
//
//    애플리케이션 컨텍스트를 자동으로 구성하여 필요한 빈들을 추측하고 설정합니다1.
//
//    클래스패스에 있는 의존성을 기반으로 적절한 구성을 자동으로 적용합니다1.
//
//    @EnableFeignClients
//    Spring Cloud OpenFeign을 활성화하는 어노테이션입니다3.
//
//            Feign 클라이언트 인터페이스를 스캔하고 구현체를 생성합니다8.
//
//    clients 속성을 사용하여 특정 Feign 클라이언트 클래스를 명시적으로 지정할 수 있습니다3.
//
//            clients = NaverClient.class
//NaverClient 인터페이스만을 Feign 클라이언트로 등록하도록 지정합니다.
//
//이는 전체 애플리케이션을 스캔하는 대신 특정 클라이언트만 활성화하고자 할 때 유용합니다.
//
//static class TestConfig{}
//이는 테스트를 위한 내부 정적 구성 클래스입니다.
//
//테스트 환경에서 필요한 특정 설정을 격리하고 관리하기 위해 사용됩니다.
//
//이 구성은 주로 테스트 환경에서 NaverClient라는 특정 Feign 클라이언트만을 활성화하고, Spring Boot의 자동 구성 기능을 사용하여 필요한 다른 구성요소들을 자동으로 설정하는 데 사용됩니다.

    // @Autowired로 NaverClient를 주입하기위해
    @EnableAutoConfiguration
    @EnableFeignClients(clients = NaverClient.class)
    static class TestConfig{}


    @Autowired
    NaverClient naverClient

    @Test
    void callNaver(){
        String http = naverClient.search("http", 1, 10);
        System.out.println(http);

    }

    def "naver 호출"() {
        given:
        when:
        def response = naverClient.search("HTTP", 1, 10)

        then:
        response.total == 31
    }
}

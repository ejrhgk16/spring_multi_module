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

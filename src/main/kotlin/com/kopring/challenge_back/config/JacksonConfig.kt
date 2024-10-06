import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        // Java 8 Time API 모듈 등록
        objectMapper.registerModule(JavaTimeModule())

        // 기본 타임스탬프 형식을 비활성화 (ISO-8601 대신 사용)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        // 시간대 설정: Asia/Seoul
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))

        return objectMapper
    }
}

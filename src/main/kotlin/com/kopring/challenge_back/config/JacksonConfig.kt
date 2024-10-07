import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        // Java 8 Time API 모듈 등록
        objectMapper.registerModule(JavaTimeModule())

        // 기본 타임스탬프 형식을 비활성화 (ISO-8601 대신 사용)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        // Date를 문자열로 처리하도록 명시
        objectMapper.disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)

        return objectMapper
    }
}
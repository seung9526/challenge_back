import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.usertype.internal.ZonedDateTimeCompositeUserType.ZonedDateTimeEmbeddable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class ChallengeCreateRequest(
    val challengeName: String,
    val challengeDescription: String,
    val category: String,

    @Schema(
        description = "챌린지 시작 시간",
        example = "2024-10-06 10:11",
        pattern = "yyyy-MM-dd HH:mm",
        type = "string"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDate
)

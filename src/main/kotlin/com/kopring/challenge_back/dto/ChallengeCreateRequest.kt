import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ChallengeCreateRequest(
    val challengeName: String,
    val challengeDescription: String,
    val category: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val startDate: LocalDateTime,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    val endDate: LocalDateTime
)

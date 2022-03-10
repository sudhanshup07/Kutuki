package `in`.sudhanshu.kutuki.common.domain.model

data class VideoResponse (
    val code: Int,
    val response: VideoListResponse
)

data class VideoListResponse(
    val videos: Map<String, Video>
)
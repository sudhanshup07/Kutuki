package `in`.sudhanshu.kutuki.common.data.remote.dto

import `in`.sudhanshu.kutuki.common.domain.model.Video
import `in`.sudhanshu.kutuki.common.domain.model.VideoListResponse
import `in`.sudhanshu.kutuki.common.domain.model.VideoResponse

data class VideoResponseDto(
    val code: Int,
    val response: VideoListResponseDto
){
    fun toVideoResponse(): VideoResponse{
        return VideoResponse(
            code = code,
            response = response.toVideoListResponse()
        )
    }
}

data class VideoListResponseDto(
    val videos: Map<String, Video>
){
    fun toVideoListResponse(): VideoListResponse{
        return VideoListResponse(
            videos = videos
        )
    }
}

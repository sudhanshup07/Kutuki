package `in`.sudhanshu.kutuki.ui.video_playback

import `in`.sudhanshu.kutuki.common.domain.model.VideoListResponse

sealed class GetVideoListEvent{

    class  Success(val data: VideoListResponse): GetVideoListEvent()
    object Loading: GetVideoListEvent()
    class Failure(val errorText: String): GetVideoListEvent()
    object Empty: GetVideoListEvent()
}

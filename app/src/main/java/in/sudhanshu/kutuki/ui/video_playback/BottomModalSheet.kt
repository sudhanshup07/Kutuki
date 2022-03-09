package `in`.sudhanshu.kutuki.ui.video_playback

import `in`.sudhanshu.kutuki.databinding.BottomModalLayoutBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomModalSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BottomModalLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomModalLayoutBinding.inflate(inflater)
        return binding.root
    }
}
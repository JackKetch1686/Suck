package com.example.myapplicatio

import android.util.Log
import android.widget.SeekBar
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService

class MySeekBarChangeListner(var seekBar: SeekBar): SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
//        Log.d("SEEK", seekBar.progress.toString())
//        Log.d("SEEK", "duration "+(seekBar.progress*BackgroundAudioService.mediaPlayer.duration/100).toString())
//        Log.d("SEEK", "*1000 "+(seekBar.progress*1000).toString())
//
//        BackgroundAudioService.mediaPlayer.seekTo(seekBar.progress*BackgroundAudioService.mediaPlayer.duration/100)
        if (fromUser)
            BackgroundAudioService.mediaPlayer.seekTo(progress)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}
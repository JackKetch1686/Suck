package com.example.myapplicatio

import android.util.Log
import android.widget.SeekBar
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService

class MySeekBar2 (var seekBar: SeekBar): SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        var k  = Math.abs(100-seekBar.progress)
        var   log1 = (Math.log(100.toDouble()-k)/Math.log(100.toDouble())).toFloat()
        Log.d("VOLUEM",seekBar.progress.toString() )
        BackgroundAudioService.mediaPlayer.setVolume(log1,log1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}
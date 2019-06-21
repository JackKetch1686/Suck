package com.example.myapplicatio

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fragment_player.*
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService

class FragmentPlayer :Fragment(){
    fun resetView(bitmap: Bitmap?){
        if (bitmap!=null)
            imageView.setImageBitmap(bitmap)
        else imageView.setImageResource(R.drawable.music_notes)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var touchCounter =1
//        PlayPausePlayer.setOnClickListener{
//
//        }
        button5.setOnClickListener{
            BackgroundAudioService.Companion::mediaPlayer.get().seekTo(BackgroundAudioService.Companion::mediaPlayer.get().getCurrentPosition() - 10000)
        }
        button6.setOnClickListener{
            BackgroundAudioService.Companion::mediaPlayer.get().seekTo(BackgroundAudioService.Companion::mediaPlayer.get().getCurrentPosition() + 10000)
        }
        PlayPausePlayer.setOnClickListener {
            if (touchCounter % 2 == 0) {
                PlayPausePlayer.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                PlayPausePlayer.setImageResource(android.R.drawable.ic_media_play)
            }
            touchCounter++
            if ( BackgroundAudioService.Companion::mediaPlayer.get().isPlaying) {
                BackgroundAudioService.Companion::mediaPlayer.get().pause()
            } else{
                BackgroundAudioService.Companion::mediaPlayer.get().start()
            }
        }
        imageButton2.setOnClickListener{
            BackgroundAudioService.mediaPlayer.isLooping = !BackgroundAudioService.mediaPlayer.isLooping
        }

        imageButton5.setOnClickListener{
            BackgroundAudioService.mediaPlayer.stop()
            BackgroundAudioService.mediaPlayer.reset()
            var number = (Math.random()* SaveItems.recyclerItems.size).toInt()
            BackgroundAudioService.mediaPlayer.setDataSource( SaveItems.recyclerItems[number].fullName)
            BackgroundAudioService.mediaPlayer.prepare()
            BackgroundAudioService.mediaPlayer.start()
        }

        seekBar.setOnSeekBarChangeListener(MySeekBar2(seekBar))
        seekBar2.setOnSeekBarChangeListener( MySeekBarChangeListner(seekBar2))

        backToMainActivity.setOnClickListener{
            startActivity(Intent(activity, MainActivity::class.java))
        }

    }
}
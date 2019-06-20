package com.example.myapplicatio

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fragment_main_activity.*
import kotlinx.android.synthetic.main.fragment_fragment_player.*
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService
import java.io.File
import kotlin.math.log

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


        button5.setOnClickListener{
            BackgroundAudioService.Companion::mediaPlayer.get().seekTo(BackgroundAudioService.Companion::mediaPlayer.get().getCurrentPosition() - 10000)
        }
        button6.setOnClickListener{
            BackgroundAudioService.Companion::mediaPlayer.get().seekTo(BackgroundAudioService.Companion::mediaPlayer.get().getCurrentPosition() + 10000)
        }
        imageButton6.setOnClickListener {
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
            BackgroundAudioService.mediaPlayer.setDataSource( SaveItems.recyclerItems[(Math.random()* SaveItems.recyclerItems.size).toInt()].fullName
            )
        }

        seekBar.setOnSeekBarChangeListener(MySeekBar2(seekBar))
        seekBar2.setOnSeekBarChangeListener( MySeekBarChangeListner(seekBar2))

        backToMainActivity.setOnClickListener{
            startActivity(Intent(activity, MainActivity::class.java))
        }

    }
}
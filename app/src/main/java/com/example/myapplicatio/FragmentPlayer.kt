package com.example.myapplicatio

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplicatio.statics.SaveImageForPlayer
import com.example.myapplicatio.statics.SaveItems
import com.example.myapplicatio.statics.SaveShafle
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
        imageView.setImageBitmap(SaveImageForPlayer.image)
        imageButton8.setOnClickListener{
            activity!!.startService(Intent(context, BackgroundAudioService::class.java))
        }
        var kill = false

        seek_Bar_progress.max = BackgroundAudioService.mediaPlayer.duration
        val SeekbarUpdateHandler = Handler()
        val UpdateSeekbar = object : Runnable {
            override fun run() {
                if(!kill) {
                    seek_Bar_progress.progress = BackgroundAudioService.mediaPlayer.currentPosition
                    SeekbarUpdateHandler.postDelayed(this, 100)
                }
//current_time.text = (seekBar.progress / 1000).toString()
            }
        }
        button5.setOnClickListener{
            BackgroundAudioService.Companion::mediaPlayer.get().seekTo(BackgroundAudioService.Companion::mediaPlayer.get().getCurrentPosition() - 10000)
        }
        button6.setOnClickListener{
            BackgroundAudioService.Companion::mediaPlayer.get().seekTo(BackgroundAudioService.Companion::mediaPlayer.get().getCurrentPosition() + 10000)
        }

        SeekbarUpdateHandler.postDelayed(UpdateSeekbar, 0)
        PlayPausePlayer.setOnClickListener {
            if (!BackgroundAudioService.mediaPlayer.isPlaying) {
                PlayPausePlayer.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                PlayPausePlayer.setImageResource(android.R.drawable.ic_media_play)
            }
            if ( BackgroundAudioService.Companion::mediaPlayer.get().isPlaying) {
                BackgroundAudioService.Companion::mediaPlayer.get().pause()
                SeekbarUpdateHandler.removeCallbacks(UpdateSeekbar)
            } else{
                BackgroundAudioService.Companion::mediaPlayer.get().start()
                SeekbarUpdateHandler.postDelayed(UpdateSeekbar, 100)

            }
        }
        repeatButton.setOnClickListener{
            if(BackgroundAudioService.mediaPlayer.isLooping){
                Toast.makeText(context, "Off", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "On", Toast.LENGTH_SHORT).show()
            }
            BackgroundAudioService.mediaPlayer.isLooping = !BackgroundAudioService.mediaPlayer.isLooping
        }

        shuffleButton.setOnClickListener{
            if(SaveShafle.shaf){
                Toast.makeText(context, "Not shuffle", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Shuffle", Toast.LENGTH_SHORT).show()
            }

            SaveShafle.shaf=! SaveShafle.shaf
//            var number = (Math.random()* SaveItems.recyclerItems.size).toInt()
//            activity!!.startService(Intent(context, BackgroundAudioService::class.java).putExtra("path",SaveItems.recyclerItems[number].fullName))
            BackgroundAudioService.mediaPlayer.stop()
            BackgroundAudioService.mediaPlayer.reset()
            var number = (Math.random()* SaveItems.recyclerItems.size).toInt()
            BackgroundAudioService.mediaPlayer.setDataSource( SaveItems.recyclerItems[number].fullName)
            BackgroundAudioService.mediaPlayer.prepare()
            BackgroundAudioService.mediaPlayer.start()
        }

        seekBar.setOnSeekBarChangeListener(MySeekBar2(seekBar))
        seek_Bar_progress.setOnSeekBarChangeListener( MySeekBarChangeListner(seek_Bar_progress))

        backToMainActivity.setOnClickListener{
            startActivity(Intent(activity, MainActivity::class.java))
            kill = true

            //////////!!!!!!!!!!!!!!!!!!!!!!!!!!
        }

    }
}
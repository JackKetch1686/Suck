package ru.spb.designedBy239School.advancedMusicPlayer.service

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.core.net.toUri
import com.example.myapplicatio.FragmentPlayer
import com.example.myapplicatio.statics.*
import java.io.File

class BackgroundAudioService:Service(),MediaPlayer.OnCompletionListener {

    companion object {
        lateinit var mediaPlayer : MediaPlayer
    }

    override fun onBind(intent: Intent?): IBinder? {
        mediaPlayer.pause()
        Log.d("ONCLICKLISTNER","Service 1")
        return null
    }

    override fun onCompletion(p0: MediaPlayer?) {
        stopSelf()
        Log.d("ONCLICKLISTNER","Service 2")

    }

    override fun onCreate() {
        Log.d("ONCLICKLISTNER","Service 3")
        mediaPlayer = MediaPlayer()

    }

    override fun onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ONCLICKLISTNER","Service 4")
            var path = intent!!.getStringExtra("path")
        if (path!=null) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            Log.d("ONCLICKLISTNER", "path = " + path)
            mediaPlayer = MediaPlayer.create(this, File(path).toUri())
            var m = MediaMetadataRetriever()
            m.setDataSource(path)
            if (m.embeddedPicture!= null) {
                var bitmap = BitmapFactory.decodeByteArray(m.embeddedPicture, 0, m.embeddedPicture.size)
                SaveImageForPlayer.image = bitmap
            }else{
                SaveImageForPlayer.image = DefaultBitmap.image
            }

            mediaPlayer.start()
        }

        return START_STICKY
    }

 //   mediaPlayer.setOnCompletionListener {
//                Log.d("LISTNEROFPLAYER","all")
//                if (SaveShafle.shaf){
//                    Log.d("LISTNEROFPLAYER","1")
//                    BackgroundAudioService.mediaPlayer.stop()
//                    BackgroundAudioService.mediaPlayer.reset()
//                    var number = (Math.random()* SaveItems.recyclerItems.size).toInt()
//                    BackgroundAudioService.mediaPlayer.setDataSource( SaveItems.recyclerItems[number].fullName)
//                    BackgroundAudioService.mediaPlayer.prepare()
//                    BackgroundAudioService.mediaPlayer.start()
//                } else{
//                    if (IsArtists.artists){
//                        Log.d("LISTNEROFPLAYER","2")
//                        BackgroundAudioService.mediaPlayer.stop()
//                        BackgroundAudioService.mediaPlayer.reset()
//                        var number = PlayingSongNumber.number
//                        BackgroundAudioService.mediaPlayer.setDataSource( SaveItems.recyclerItems[number].fullName)
//                        BackgroundAudioService.mediaPlayer.prepare()
//                        BackgroundAudioService.mediaPlayer.start()
//                    } else{
//                        Log.d("LISTNEROFPLAYER","3")
//                        BackgroundAudioService.mediaPlayer.stop()
//                        BackgroundAudioService.mediaPlayer.reset()
//                        var number = PlayingSongNumber.number
//                        BackgroundAudioService.mediaPlayer.setDataSource( SaveSongsOfAuthor.recyclerItems[number].fullName)
//                        BackgroundAudioService.mediaPlayer.prepare()
//                        BackgroundAudioService.mediaPlayer.start()
//
//                    }
//
//                }
//            }


}
package ru.spb.designedBy239School.advancedMusicPlayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Environment
import android.os.IBinder
import android.util.Log
import androidx.core.net.toUri
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.RecyclerItem
import java.io.File

class BackgroundAudioService:Service(),MediaPlayer.OnCompletionListener {
    val recyclerItems = returnRecyclerItems()
    var mediaPlayer = MediaPlayer()
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("ONCLICKLISTNER","Service 1")
        return null
    }

    override fun onCompletion(p0: MediaPlayer?) {
        stopSelf()
        Log.d("ONCLICKLISTNER","Service 2")

    }

    override fun onCreate() {
        Log.d("ONCLICKLISTNER","Service 3")

    }

    override fun onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ONCLICKLISTNER","Service 4")
        var path = intent!!.getStringExtra("path")
        mediaPlayer.stop()
        mediaPlayer.reset()
        Log.d("ONCLICKLISTNER","path = "+path)
        mediaPlayer = MediaPlayer.create(this,File(path).toUri())
        mediaPlayer.start()

        return START_STICKY
    }

    fun returnRecyclerItems(): ArrayList<RecyclerItem> {
        if (recyclerItems==null){
            // return recyclerItems
        }
        Log.d("SONGSEARCHING","search...")
        val recyclerItems: ArrayList<RecyclerItem> = ArrayList()
        var listMusic = getPlayListStrings(Environment.getExternalStorageDirectory())
        for (i in listMusic) {
            var m = MediaMetadataRetriever()
            m.setDataSource(i["path"])
            var image = m.getEmbeddedPicture()
            var artist = "artist: " + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            var album = "" + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            var song = m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            if (song == null) song = i["name"]
            recyclerItems.add(
                RecyclerItem(
                    song,
                    artist,
                    album,
                    image,
                    i["path"].toString()
                )
            )
        }
        return recyclerItems
    }


    private fun getPlayListStrings(inputFile : File) : ArrayList<HashMap<String,String>> {
        val filelist : ArrayList<HashMap<String,String>> = ArrayList()
        try {
            val files = inputFile.listFiles() //here you will get NPE if directory doesn't contains any file,handle it like this.
            for (file in files) {
                if (file.isDirectory) {
                    if (getPlayListStrings(file) != null) {
                        //filelist.addAll(getPlayListStrings(file))
                    } else {

                        break
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    val song = HashMap<String, String>()
                    song["name"] = file.name
                    song["path"] = file.absolutePath
                    filelist.add(song)
                }
            }
            return filelist
        } catch (e: Exception) {
            return ArrayList()
        }
    }
}
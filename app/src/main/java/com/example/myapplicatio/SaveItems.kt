package com.example.myapplicatio

import android.media.MediaMetadataRetriever
import android.os.Environment
import android.util.Log
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.RecyclerItem
import java.io.File


class SaveItems {
    init {
        Log.d("Initsongs","Init")
        recyclerItems = returnRecyclerItems()
    }
    companion object itemsOfMusic {
        lateinit var recyclerItems: ArrayList<RecyclerItem>
    }
    fun returnRecyclerItems(): ArrayList<RecyclerItem> {
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
                        filelist.addAll(getPlayListStrings(file))
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


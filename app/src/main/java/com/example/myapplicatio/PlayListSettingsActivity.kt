package com.example.myapplicatio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicatio.statics.SaveItems
import com.example.myapplicatio.statics.SaveSongsOfAuthor
import kotlinx.android.synthetic.main.activity_play_list_settings.*
import kotlinx.android.synthetic.main.fragment_fragment_main_activity.recycler_view
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.MyRecyclerViewAdapter
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService


class PlayListSettingsActivity : AppCompatActivity(),  MyRecyclerViewAdapter.OnSongListner {
    override fun onNoteCLick(position: Int, artists: Boolean) {
        Log.d("CLICKPlaylist", "Playlist settings click")
        if (!artists) {
            Log.d("CLICKPlaylist","1")
            this.startService(
                Intent(this, BackgroundAudioService::class.java).putExtra(
                    "path",
                    SaveItems.recyclerItems[position].fullName
                )
            )
        } else{
            Log.d("CLICKPlaylist","2")
            Log.d("CLICKPlaylist", SaveSongsOfAuthor.recyclerItems[position].fullName)
            this.startService(
            Intent(this, BackgroundAudioService::class.java).
                putExtra("path", SaveSongsOfAuthor.recyclerItems[position].fullName)
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_list_settings)

        button_fragment_main_activity.setOnClickListener {
            startActivity(Intent(this, PlayerAndEqualizerActivity::class.java))
        }
        for (i in SaveSongsOfAuthor.recyclerItems){
            Log.d("AUTHORS2", i.SongAuthorName+" "+ i.SongName)
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        val adapterR = MyRecyclerViewAdapter( SaveSongsOfAuthor.recyclerItems, this, true)
        recycler_view.adapter = adapterR
        PlayPauseButtonMain_settings.setOnClickListener{
            if (!BackgroundAudioService.mediaPlayer.isPlaying) {
                PlayPauseButtonMain_settings.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                PlayPauseButtonMain_settings.setImageResource(android.R.drawable.ic_media_play)
            }
            if (BackgroundAudioService.mediaPlayer.isPlaying){
                BackgroundAudioService.mediaPlayer.pause()
            } else{
                BackgroundAudioService.mediaPlayer.start()
            }
        }



    }
}

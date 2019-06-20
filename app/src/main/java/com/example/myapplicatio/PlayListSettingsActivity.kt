package com.example.myapplicatio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_fragment_main_activity.*
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.MyRecyclerViewAdapter
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService


class PlayListSettingsActivity : AppCompatActivity(),  MyRecyclerViewAdapter.OnSongListner {
    override fun onNoteCLick(position: Int) {
        this.startService(Intent(this, BackgroundAudioService::class.java).putExtra("path", SaveItems.recyclerItems[position].fullName))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_list_settings)

        recycler_view.layoutManager = LinearLayoutManager(this)
        val adapterR = MyRecyclerViewAdapter( SaveSongsOfAuthor.recyclerItems, this)
        var l = SaveItems.recyclerItems
        recycler_view.adapter = adapterR


    }
}

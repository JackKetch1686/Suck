package com.example.myapplicatio


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myapplicatio.statics.IsArtists
import com.example.myapplicatio.statics.SaveAuthorsSongs
import com.example.myapplicatio.statics.SaveItems
import com.example.myapplicatio.statics.SaveSongsOfAuthor
import kotlinx.android.synthetic.main.fragment_fragment_playlist.*
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.RecyclerItem

class FragmentPlaylist :Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fragment_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var artists =  ArrayList<String>()
        for (i in SaveAuthorsSongs.arrayAuthorsOfSongs){
            artists.add(i.name)
        }

        var adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1,artists)
        list_of_artists.adapter = adapter

        list_of_artists.setOnItemClickListener { _, _, _, id ->
            var songs: ArrayList<RecyclerItem> = ArrayList<RecyclerItem>()
            for ( i in SaveAuthorsSongs.arrayAuthorsOfSongs[id.toInt()].songs) {
                Log.d("Authors1", SaveAuthorsSongs.arrayAuthorsOfSongs[id.toInt()].name+" : " +i )
                songs.add(SaveItems.recyclerItems[i])
            }
            SaveSongsOfAuthor.recyclerItems = songs
            activity!!.startActivity(Intent(context, PlayListSettingsActivity::class.java))
            IsArtists.artists=true

        }
    }
}
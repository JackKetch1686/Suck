package com.example.myapplicatio

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.MyRecyclerViewAdapter
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService

class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.OnSongListner {
    override fun onNoteCLick(position: Int) {
        startService(Intent(this, BackgroundAudioService::class.java).putExtra("path", SaveItems.recyclerItems[position].fullName))
        Log.d("ONCLICKLISTNER","MainActivity: "+position.toString())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageButton11.setOnClickListener{
            if (BackgroundAudioService.mediaPlayer.isPlaying){
                BackgroundAudioService.mediaPlayer.pause()
            } else{
                BackgroundAudioService.mediaPlayer.start()
            }
        }


        button_fragment_main_activity.setOnClickListener{
            startActivity(Intent(this, PlayerAndEqualizerActivity::class.java))
        }

        val adapterOfMainActivity = MyViewPagerAdapter(supportFragmentManager)
        adapterOfMainActivity.addFragment(FragmentPlaylist(), "←")
        adapterOfMainActivity.addFragment(FragmentMainActivity(), "Name of currrent playlist thb etghb netghn")



        SaveItems()
        startService(Intent(this, BackgroundAudioService::class.java))
        
        vpOfMainActivity.adapter=adapterOfMainActivity
        tabsOfMainActivity.setupWithViewPager(vpOfMainActivity)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }


        var authors = ArrayList<String>()
        for (item in SaveItems.recyclerItems){
            var isAsrtist = true
            var artist  = item.SongAuthorName
            for (author in authors){
                if (artist==author) false
            }
            if(isAsrtist){
                authors.add(artist)
            }
        }

        var arrayAuthorsOfSongs = ArrayList<AuthorOfSongs>()
        for (author in authors){
            var songs = ArrayList<Int> ()
            var j = 0
            for (item in SaveItems.recyclerItems){
                if (author==item.SongAuthorName){
                    songs.add(j)
                    j++
                }
            }
            arrayAuthorsOfSongs.add(AuthorOfSongs(author, songs))
        }
        SaveAuthors.arrayAuthorsOfSongs=arrayAuthorsOfSongs

    }

    class MyViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager){
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]

        }
        fun addFragment(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }


        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}

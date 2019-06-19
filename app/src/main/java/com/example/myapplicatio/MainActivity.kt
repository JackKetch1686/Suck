package com.example.myapplicatio

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaMetadataRetriever
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fragment_main_activity.*
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.MyRecyclerViewAdapter
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.RecyclerItem
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService
import java.io.File

class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.OnSongListner {
    override fun onNoteCLick(position: Int) {
        startService(Intent(this, BackgroundAudioService::class.java).putExtra("path", recyclerItems[position].fullName))
        Log.d("ONCLICKLISTNER","MainActivity: "+position.toString())

    }
    val recyclerItems = returnRecyclerItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapterOfMainActivity = MyViewPagerAdapter(supportFragmentManager)
        adapterOfMainActivity.addFragment(FragmentMainActivity(), "My Playlist ")
        adapterOfMainActivity.addFragment(FragmentPlaylist(), "Playlist ")

        vpOfMainActivity.adapter=adapterOfMainActivity
        tabsOfMainActivity.setupWithViewPager(vpOfMainActivity)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }



//        var recyclerView: RecyclerView = findViewById(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(  this)
//        val adapterR = MyRecyclerViewAdapter(recyclerItems, this)
//        recyclerView.adapter = adapterR



    }

    fun returnRecyclerItems(): ArrayList<RecyclerItem> {
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

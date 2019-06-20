package com.example.myapplicatio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fragment_main_activity.*
import ru.spb.designedBy239School.advancedMusicPlayer.adapter.MyRecyclerViewAdapter
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService

class FragmentMainActivity :Fragment(), MyRecyclerViewAdapter.OnSongListner{
    override fun onNoteCLick(position: Int) {
        Log.d("ONCLICKLISTNER","is activiti null? "+activity.toString())
        Log.d("ONCLICKLISTNER","FragmentMainActivity "+position.toString())
        activity!!.startService(Intent(activity, BackgroundAudioService::class.java))//
        // .putExtra("path", BackgroundAudioService::recyclerItems.get(BackgroundAudioService())[position].fullName))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_fragment_main_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recycler_view.layoutManager = LinearLayoutManager(activity)
        val adapterR = MyRecyclerViewAdapter( BackgroundAudioService::recyclerItems.get(BackgroundAudioService()), this)
        recycler_view.adapter = adapterR
    }
}
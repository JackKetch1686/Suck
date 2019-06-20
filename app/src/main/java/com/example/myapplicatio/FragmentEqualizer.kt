package com.example.myapplicatio

import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fragment_equalizer.*
import kotlinx.android.synthetic.main.fragment_fragment_main_activity.*
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService
import ru.spb.designedBy239School.advancedMusicPlayer.service.BackgroundAudioService.Companion.mediaPlayer

class FragmentEqualizer :Fragment() {

    lateinit var eqSettings: Equalizer.Settings
    lateinit var equalizer: Equalizer
    var pauseCounter = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fragment_equalizer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var audioSessionId = BackgroundAudioService.mediaPlayer.audioSessionId


        //val bassboost = BassBoost(0, audioSessionId)

//        eq_seek_bar_BASS.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//            }
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//            }
//            override fun onStopTrackingTouch(seekBar: SeekBar) {
//                bassboost.setStrength(seekBar.progress.toShort())
//                Log.d("EQ", "BASS is ${bassboost.properties.strength}")
//            }
//        })


        this.equalizer = Equalizer(1, audioSessionId)
        equalizer.enabled = true

        if (equalizer.enabled && equalizer.hasControl()) Log.d(
            "EQ",
            "equalizer is enable and has control"
        )

        Log.d("EQ", "Start level is ${equalizer.getBandLevel(0)}")


        eq_seek_bar_0.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                equalizer.enabled = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                equalizer.setBandLevel(0, seekBar.progress.toShort())
                Log.d("EQ", "BAnd 0 level is ${equalizer.getBandLevel(0)}")
            }
        })

        eq_seek_bar_1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                equalizer.setBandLevel(1, seekBar.progress.toShort())
                Log.d("EQ", "BAnd 1 level is ${equalizer.getBandLevel(0)}")
            }
        })

        eq_seek_bar_2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                equalizer.setBandLevel(2, seekBar.progress.toShort())
                Log.d("EQ", "BAnd 2 level is ${equalizer.getBandLevel(2)}")
            }
        })

        eq_seek_bar_3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                equalizer.setBandLevel(3, seekBar.progress.toShort())
                Log.d("EQ", "BAnd 3 level is ${equalizer.getBandLevel(3)}")
            }
        })

        eq_seek_bar_4.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                equalizer.setBandLevel(4, seekBar.progress.toShort())
                Log.d("EQ", "BAnd 4 level is ${equalizer.getBandLevel(4)}")
            }
        })

        preset_1.setOnClickListener {
            equalizer.usePreset(1)
            Log.d("EQ", "Preset 1")
        }

        preset_2.setOnClickListener {
            equalizer.usePreset(2)
            Log.d("EQ", "Preset 2")
        }


    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        requireActivity().getSharedPreferences("Sas", Context.MODE_PRIVATE).edit().putInt("key", 1).apply()
//        requireActivity().getSharedPreferences("SAs", Context.MODE_PRIVATE).getInt("key", 0)
//    }

    override fun onPause() {
        super.onPause()
        pauseCounter++
        Log.d("EQ", "pauseCounter $pauseCounter")
        requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE)
            .edit().putInt("band0", equalizer.properties.bandLevels[0].toInt()).apply()
        requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE)
            .edit().putInt("band1", equalizer.properties.bandLevels[1].toInt()).apply()
        requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE)
            .edit().putInt("band2", equalizer.properties.bandLevels[2].toInt()).apply()
        requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE)
            .edit().putInt("band3", equalizer.properties.bandLevels[3].toInt()).apply()
        requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE)
            .edit().putInt("band4", equalizer.properties.bandLevels[4].toInt()).apply()
    }


    //    !!!!!!!!!           ONRESUME CALLED

    override fun onResume() {
        super.onResume()
        Log.d("EQ", "ONRESUME called")
        eq_seek_bar_0.progress =
            requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE).getInt("band0", 0)
        eq_seek_bar_1.progress =
            requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE).getInt("band1", 0)
        eq_seek_bar_2.progress =
            requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE).getInt("band2", 0)
        eq_seek_bar_3.progress =
            requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE).getInt("band3", 0)
        eq_seek_bar_4.progress =
            requireActivity().getSharedPreferences("Eq_Settings", Context.MODE_PRIVATE).getInt("band4", 0)


    }
}

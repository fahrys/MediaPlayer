package com.example.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mediaputar: MediaPlayer
    private var waktutotal :Int = 0

    @Suppress("RedundantSamConstructor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaputar = MediaPlayer.create(this , R.raw.kemoono)
        mediaputar.isLooping = true
        mediaputar.setVolume(0.5f ,0.5f)
        waktutotal = mediaputar.duration

        suaraseekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        val jumlaVolume = progress / 100.0f
                        mediaputar.setVolume(jumlaVolume , jumlaVolume)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            }
        )

        sikbarlagu.max = waktutotal
        sikbarlagu.setOnSeekBarChangeListener(
            object  : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaputar.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            }
        )



    }

    fun playBtnClick(view: View) {}
}
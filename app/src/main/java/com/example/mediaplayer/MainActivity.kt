package com.example.mediaplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
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

        Thread(Runnable {
            while (mediaputar != null) {
                try {
                    val pesan = Message()
                    pesan.what = mediaputar.currentPosition
                    penanganan.sendMessage(pesan)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()

    }

    private var penanganan = @SuppressLint("HandlerLeak")
    object : Handler() {
        @SuppressLint("SetTextI18n")
        override fun handleMessage(pesan: Message) {
            super.handleMessage(pesan)

            val posisiSaatIni = pesan.what
            sikbarlagu.progress = posisiSaatIni

            val perkiraannWaktu = buatLabelWaktu(posisiSaatIni)
            labelperkiraanwaktu.text = perkiraannWaktu

            val pengingatWaktu = buatLabelWaktu(waktutotal - posisiSaatIni)
            labelpengingatwaktu.text = "-$pengingatWaktu"
        }
    }

    fun buatLabelWaktu(waktu : Int) : String {
        var labelwaktu = ""
        val menit = waktu / 1000 / 60
        val detik = waktu / 1000 % 60

        labelwaktu = "$menit: "
        if (detik < 10) labelwaktu += "0"
        labelwaktu += detik

        return labelwaktu
    }

    fun playBtnClick(v: View) {
        if (mediaputar.isPlaying){
            mediaputar.pause()
            btn_playpause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
        } else {
            mediaputar.start()
            btn_playpause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_filled_24)
        }
    }
}
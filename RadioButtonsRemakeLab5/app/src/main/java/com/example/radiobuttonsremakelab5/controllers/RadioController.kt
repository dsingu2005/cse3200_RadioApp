package com.example.radiobuttonsremakelab5.controllers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.radiobuttonsremakelab5.R
import com.example.radiobuttonsremakelab5.models.Radio
import com.example.radiobuttonsremakelab5.views.RadioViewModel

@Composable
fun RadioController(viewModel: RadioViewModel = viewModel()) {
    val selectedRadio = viewModel.selectedRadio.collectAsState().value
    val isPlaying = viewModel.isPlaying.collectAsState().value
    val volume = viewModel.volume.collectAsState().value

    Column {
        val radios = listOf(
            Radio("Station 1", "https://streams.radio.co/s8d06d0298/listen", R.drawable.radio1),
            Radio("Station 2", "http://s6.mediastreaming.it:7020/;", R.drawable.radio2),
            Radio("Station 3", "http://playerservices.streamtheworld.com/api/livestream-redirect/WEZNFM.mp3", R.drawable.radio3),
            Radio("Station 4", "http://rd.shalombeatsradio.com:9190/stream", R.drawable.radio4),
            Radio("Station 5", "https://goldenwest.leanstream.co/CKFIFM-MP3", R.drawable.radio5)
        )

        radios.forEach { radio ->
            Row {
                Image(painter = painterResource(id = radio.imageResId), contentDescription = null)
                Text(text = radio.name)
                Button(onClick = { viewModel.selectRadio(radio); viewModel.startStopRadio() }) {
                    Text(text = if (isPlaying && selectedRadio == radio) "Stop" else "Start")
                }
            }
        }

        Row {
            Button(onClick = { viewModel.decreaseVolume() }) {
                Text(text = "-")
            }
            Slider(
                value = volume.toFloat(),
                onValueChange = { newVolume -> viewModel.setVolume(newVolume.toInt()) },
                valueRange = 0f..100f
            )
            Button(onClick = { viewModel.increaseVolume() }) {
                Text(text = "+")
            }
        }
    }
}
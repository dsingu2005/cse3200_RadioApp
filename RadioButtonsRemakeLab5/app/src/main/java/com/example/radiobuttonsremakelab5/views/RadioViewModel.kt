package com.example.radiobuttonsremakelab5.views

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.example.radiobuttonsremakelab5.models.Radio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RadioViewModel : ViewModel() {
    private val _selectedRadio = MutableStateFlow<Radio?>(null)
    val selectedRadio: StateFlow<Radio?> = _selectedRadio

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _volume = MutableStateFlow(50) // Default volume level
    val volume: StateFlow<Int> = _volume

    private var mediaPlayer: MediaPlayer? = null

    fun setVolume(newVolume: Int) {
        _volume.value = newVolume.coerceIn(0, 100) 
        val volume = _volume.value / 100f 
        mediaPlayer?.setVolume(volume, volume)  
    }

    fun selectRadio(radio: Radio) {
        _selectedRadio.value = radio
    }

    fun startStopRadio() {
        if (_isPlaying.value) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            _isPlaying.value = false
        } else {
            val radio = _selectedRadio.value ?: return
            mediaPlayer = MediaPlayer().apply {
                setDataSource(radio.streamUrl)
                setOnPreparedListener { start() }
                prepareAsync()
            }
            _isPlaying.value = true
        }
    }

    fun increaseVolume() {
        if (_volume.value < 100) {
            _volume.value++
            setVolume(_volume.value)  // Update the volume of the MediaPlayer
        }
    }

    fun decreaseVolume() {
        if (_volume.value > 0) {
            _volume.value--
            setVolume(_volume.value)  // Update the volume of the MediaPlayer
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
    }
}
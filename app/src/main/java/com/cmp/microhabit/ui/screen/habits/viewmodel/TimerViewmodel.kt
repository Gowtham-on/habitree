
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel(initialSeconds: Int) : ViewModel() {
    var secondsLeft by mutableIntStateOf(initialSeconds)

    private var timerJob: Job? = null
    private var isPaused = false
    private val initial = initialSeconds
    private var isStarted by mutableStateOf(false)

    fun start() {
        if (timerJob == null || timerJob?.isCompleted == true) {
            isPaused = false
            isStarted = true
            timerJob = viewModelScope.launch {
                while (secondsLeft > 0) {
                    delay(1000L)
                    if (!isPaused) {
                        secondsLeft -= 1
                    }
                }
            }
        }
    }

    fun pause() {
        isStarted = false
        isPaused = true
    }

    fun resume() {
        if (isPaused) isPaused = false
    }

    fun stop() {
        isStarted = false
        timerJob?.cancel()
        timerJob = null
        secondsLeft = initial
        isPaused = false
    }

    fun isRunning(): Boolean {
        return isStarted
    }
}
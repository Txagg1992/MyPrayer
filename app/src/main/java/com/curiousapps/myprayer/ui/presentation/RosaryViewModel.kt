package com.curiousapps.myprayer.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curiousapps.myprayer.data.RosaryPrayersItem
import com.curiousapps.myprayer.repository.PrayerRepository
import com.curiousapps.myprayer.repository.SaintRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RosaryViewModel @Inject constructor(
    private val repository: PrayerRepository,
    private val saintRepository: SaintRepository,
    private val rosaryOrderBuilder: RosaryOrderBuilder
): ViewModel() {

    private val _state = MutableStateFlow(RosaryState())
    val state = _state
        .onStart { getRosaryPrayers() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            RosaryState()
        )

    private fun getRosaryPrayers() {
        viewModelScope.launch {
            val result = repository.getRosaryPrayers()
            val saints = saintRepository.getSaints().map { saint ->
                RosaryPrayersItem(
                    id = saint.id,
                    prayerName = saint.prayerName,
                    prayerText = saint.prayerText,
                    youtubeUrl = saint.youtubeUrl
                )
            }
            val todaysMysterySet = repository.getMysterySetForDay(LocalDate.now().dayOfWeek)
            if (result.isEmpty()) {
                _state.value = _state.value.copy(
                    prayers = result,
                    displayPrayers = emptyList(),
                    saintPrayers = saints,
                    todaysMysterySetName = todaysMysterySet?.name,
                    isLoading = false,
                    error = "No Prayers Found"
                )
            } else {
                _state.value = _state.value.copy(
                    prayers = result,
                    displayPrayers = rosaryOrderBuilder.build(result, todaysMysterySet),
                    saintPrayers = saints,
                    todaysMysterySetName = todaysMysterySet?.name,
                    isLoading = false,
                    error = null
                )
            }
        }
    }


    fun saveListScrollOffset(offset: Int) {
        _state.value = _state.value.copy(lastListScrollOffset = offset)
    }

    data class RosaryState(
        val prayers: List<RosaryPrayersItem> = emptyList(),
        val displayPrayers: List<RosaryPrayersItem> = emptyList(),
        val saintPrayers: List<RosaryPrayersItem> = emptyList(),
        val todaysMysterySetName: String? = null,
        val lastListScrollOffset: Int = 0,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}
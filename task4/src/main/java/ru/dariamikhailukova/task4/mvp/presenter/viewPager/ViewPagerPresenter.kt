package ru.dariamikhailukova.task4.mvp.presenter.viewPager

import androidx.lifecycle.LiveData
import ru.dariamikhailukova.task4.data.Note

interface ViewPagerPresenter {
    fun getAllData(): LiveData<List<Note>>
}

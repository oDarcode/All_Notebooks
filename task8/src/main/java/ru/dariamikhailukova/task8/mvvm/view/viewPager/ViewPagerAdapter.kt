package ru.dariamikhailukova.task8.mvvm.view.viewPager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dariamikhailukova.task8.data.Note
import ru.dariamikhailukova.task8.mvvm.view.show.ShowFragment
import ru.dariamikhailukova.task8.mvvm.view.show.ShowFragment.Companion.NOTE

/**
 * Класс адаптер для ViewPager2
 */
class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    private var notes: List<Note> = emptyList()

    /**
     * @return количество элементов списка
     */
    override fun getItemCount(): Int = notes.size

    /**
     * @return фрагмент, который отображает текущий элемент списка
     *
     * @param position позиция элемента в списке
     */
    override fun createFragment(position: Int): Fragment {
        val fragment = ShowFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(NOTE, notes[position])
        }
        return fragment
    }

    /**
     * Функция, которая заполняет список элементами и уведомляет при изменеии данных
     *
     * @param notes список заметок из БД
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setData(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }

}
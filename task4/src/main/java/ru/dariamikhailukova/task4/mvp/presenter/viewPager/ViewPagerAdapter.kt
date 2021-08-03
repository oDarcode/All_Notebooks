package ru.dariamikhailukova.task4.mvp.presenter.viewPager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dariamikhailukova.task4.data.Note
import ru.dariamikhailukova.task4.mvp.view.current.CurrentFragment
import ru.dariamikhailukova.task4.mvp.view.current.CurrentFragment.Companion.NOTE

/**
 * Класс адаптер для ViewPager2
 */
class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
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
        val fragment = CurrentFragment()
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
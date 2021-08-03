package ru.dariamikhailukova.task4.mvp.presenter.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.dariamikhailukova.task4.R
import ru.dariamikhailukova.task4.data.Note
import ru.dariamikhailukova.task4.mvp.view.list.ListFragmentDirections

/**
 * Класс адаптер для Recycler view
 */
class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var noteList = emptyList<Note>()

    /**
     * Класс View Holder для связывания переменных с компонентами макета
     */
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name: TextView? = null
        var eachItem: ConstraintLayout? = null

        init{
            name = itemView.findViewById(R.id.noteName)
            eachItem = itemView.findViewById(R.id.rowLayout)
        }
    }

    /**
     * Функция, которая задает идентификатор макета для отдельного элемента списка
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    /**
     * @return количество элементов списка
     */
    override fun getItemCount(): Int {
        return noteList.size
    }


    /**
     * Функция, которая связывает используемые текстовые метки с данными
     *
     * @param holder содержит параметры, которые необходимо задать для отображения элемента списка
     * @param position позиция элемента в списке
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.name?.text = currentItem.name

        holder.eachItem?.setOnClickListener {
            //val action = ListFragmentDirections.actionListFragmentToShowFragment(currentItem)
            //пока что отключила
            val action = ListFragmentDirections.actionListFragmentToViewPagerFragment(position)
            holder.itemView.findNavController().navigate(action)
        }

    }

    /**
     * Функция, которая заполняет список элементами и уведомляет при изменеии данных
     *
     * @param notes список заметок из БД
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setData(notes: List<Note>){
        this.noteList = notes
        notifyDataSetChanged()
    }

}
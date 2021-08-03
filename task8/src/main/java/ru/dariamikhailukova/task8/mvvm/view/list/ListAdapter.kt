package ru.dariamikhailukova.task8.mvvm.view.list

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.dariamikhailukova.task8.R
import ru.dariamikhailukova.task8.data.Note
import ru.dariamikhailukova.task8.mvvm.view.viewPager.ViewPager
import ru.dariamikhailukova.task8.mvvm.view.viewPager.ViewPager.Companion.NUMBER

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
            val intent = Intent(holder.itemView.context, ViewPager::class.java)
            intent.putExtra(NUMBER, currentItem.id)
            holder.itemView.context.startActivity(intent)
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
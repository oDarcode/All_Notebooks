package ru.dariamikhailukova.task1.presenter

import android.text.TextUtils
import ru.dariamikhailukova.task1.model.MainActivityModel
import ru.dariamikhailukova.task1.view.View

/**
 * Презентер для MainActivity
 *
 * @property view вью, к которой имеем доступ через интерфейс
 */
class MainActivityPresenter(private var view: View):Presenter {
    private var model = MainActivityModel("")

    /**
     * Инициализация view
     */
    init {
        view.initView()
    }

    /**
     * Функция, сохраняющая заполненную заметку в [MainActivityModel]
     *
     * Если заметка пуста, то выводит соотвестующее сообщение-тост
     */
    override fun readNote(name: String, text: String){
        if(isEmpty(name,text)){
            view.showToast("Пустая заметка")
        }else{
            model = MainActivityModel(name)
            view.showToast("Сохранено")
        }
        view.updateViewData()
    }

    private fun isEmpty(name: String, text: String): Boolean{
        return (TextUtils.isEmpty(name) || TextUtils.isEmpty(text))
    }
}
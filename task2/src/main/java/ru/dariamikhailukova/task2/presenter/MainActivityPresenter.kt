package ru.dariamikhailukova.task2.presenter

import android.text.TextUtils
import ru.dariamikhailukova.task2.R
import ru.dariamikhailukova.task2.model.MainActivityModel
import ru.dariamikhailukova.task2.view.View

/**
 * Презентер для MainActivity
 *
 * @property view вью, к которой имеем доступ через интерфейс
 */
class MainActivityPresenter(private var view: View):Presenter {
    private var model = MainActivityModel("","")

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
     *
     * @param name название заметки
     * @param text текст заметки
     */
    override fun readNote(name: String, text: String){
        if(isEmpty(name,text)){
            view.showToast(R.string.empty_note)
        }else{
            model = MainActivityModel(name,text)
            view.showToast(R.string.saved)
        }
        view.updateViewData()
    }

    /**
     * Функция, вызывающая неявный интент "отправить заметку во внешнее приложение",
     * если заметка не пустая
     */
    override fun sendEmail() {
        if(isEmpty(model.name, model.text)){
            view.showToast(R.string.empty_note)
        }else{
            view.sendIntent(model.name, model.text)
        }
        view.updateViewData()
    }

    private fun isEmpty(name: String, text: String): Boolean{
        return (TextUtils.isEmpty(name) || TextUtils.isEmpty(text))
    }
}
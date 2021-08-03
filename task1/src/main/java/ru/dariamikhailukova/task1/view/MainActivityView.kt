package ru.dariamikhailukova.task1.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.dariamikhailukova.task1.R
import ru.dariamikhailukova.task1.presenter.MainActivityPresenter

/**
 * View класс для работы с activity_main
 */
class MainActivityView: AppCompatActivity(), View {
    private var presenter: MainActivityPresenter? = null

    private lateinit var titleView: EditText
    private lateinit var textView: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
    }

    /**
     * Функция инициализирующая view
     * Связывает переменные с кнопками activity_main и следит за нажатием кнопки [saveButton]
     */
    override fun initView(){
        titleView = findViewById(R.id.nameNote)
        textView = findViewById(R.id.textNote)
        saveButton = findViewById(R.id.buttonSave)

        saveButton.setOnClickListener {
            presenter?.readNote(
                titleView.text.toString(),
                textView.text.toString()
            )
        }

    }

    /**
     * Функция, которая удаляет содержимое полей заметки
     */
    override fun updateViewData(){
        titleView.setText("")
        textView.setText("")
    }

    /**
     * Функция для вывода любого короткого сообения-тоста
     */
    override fun showToast(resId: String) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}
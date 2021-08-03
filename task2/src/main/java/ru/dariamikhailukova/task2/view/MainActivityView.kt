package ru.dariamikhailukova.task2.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.dariamikhailukova.task2.About
import ru.dariamikhailukova.task2.R
import ru.dariamikhailukova.task2.presenter.MainActivityPresenter

/**
 * View класс для работы с activity_main
 */
class MainActivityView: AppCompatActivity(), View {
    private var presenter: MainActivityPresenter? = null

    private lateinit var titleView: EditText
    private lateinit var textView: EditText
    private lateinit var saveButton: Button
    private lateinit var shareButton: Button
    private lateinit var aboutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
    }

    /**
    * Функция инициализирующая view
    * Связывает переменные с кнопками activity_main и следит за
     * нажатием кнопок [saveButton], [shareButton] и [aboutButton].
    */
    override fun initView(){
        titleView = findViewById(R.id.nameNote)
        textView = findViewById(R.id.textNote)
        saveButton = findViewById(R.id.buttonSave)
        shareButton = findViewById(R.id.buttonShare)
        aboutButton = findViewById(R.id.buttonAbout)

        saveButton.setOnClickListener {
            presenter?.readNote(
                titleView.text.toString(),
                textView.text.toString()
            )
        }

        aboutButton.setOnClickListener {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }

        shareButton.setOnClickListener {
            presenter?.sendEmail()
        }
    }


    /**
     * Функция вызова неявного интента "отправить заметку во внешнее приложение".
     *
     * @param name название заметки
     * @param text текст заметки
     */
    override fun sendIntent(name: String, text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, name)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, ""))
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
    override fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}
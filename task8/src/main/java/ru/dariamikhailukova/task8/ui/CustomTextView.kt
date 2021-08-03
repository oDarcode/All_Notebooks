package ru.dariamikhailukova.task8.ui

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import ru.dariamikhailukova.task8.R

/**
 * Класс, реализующий пользовательскую Text View
 *
 * @param context контекст
 * @param attrs передаваемые атрибуты
 * @param defStyleAttr стиль атрибутов
 */
class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): AppCompatTextView(context, attrs, defStyleAttr) {

    private var htmlText: String? = null
        set(value) {
            field = value
            text = value?.let { Html.fromHtml(value, 0) }
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            defStyleAttr,
            0
        ).also { typedArray ->
            htmlText = typedArray.getString(R.styleable.CustomTextView_htmlText)
        }.recycle()
    }

    override fun performClick(): Boolean {
        super.performClick()
        Log.d("CLICK", "Yes, you are clicked!")
        return true
    }

}
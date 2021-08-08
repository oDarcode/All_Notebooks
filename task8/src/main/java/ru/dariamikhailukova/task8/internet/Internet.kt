package ru.dariamikhailukova.task8.internet

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import ru.dariamikhailukova.task8.R

import ru.dariamikhailukova.task8.databinding.ActivityInternetBinding


/**
 * Класс для реализации работы WebView
 */
@SuppressLint("SetJavaScriptEnabled")
class Internet : AppCompatActivity() {
    private lateinit var binding: ActivityInternetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.myToolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        webViewSetup()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            if(binding.webView.canGoBack()){
                binding.webView.goBack()
            }else{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Функция для настройки работы webView
    private fun webViewSetup() {

        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            settings.loadsImagesAutomatically = true
        }

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("https://www.google.ru/")

    }
}
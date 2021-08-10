package ru.dariamikhailukova.allnotebooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.dariamikhailukova.allnotebooks.databinding.ActivityMainBinding
import android.content.Intent
import android.util.Log


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent()

        binding.buttonTask1.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task1",
                "ru.dariamikhailukova.task1.view.MainActivityView")
            startActivity(intent)
        }

        binding.buttonTask2.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task2",
                "ru.dariamikhailukova.task2.view.MainActivityView")
            startActivity(intent)
        }

        binding.buttonTask3.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task3",
                "ru.dariamikhailukova.task3.MainActivity")
            startActivity(intent)
        }

        binding.buttonTask4.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task4",
                "ru.dariamikhailukova.task4.MainActivity")
            startActivity(intent)
        }

        binding.buttonTask5.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task5",
                "ru.dariamikhailukova.task5.MainActivity")
            startActivity(intent)
        }

        binding.buttonTask6.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task6",
                "ru.dariamikhailukova.task6.MainActivity")
            startActivity(intent)
        }

        binding.buttonTask7.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task7",
                "ru.dariamikhailukova.task7.MainActivity")
            startActivity(intent)
        }

        binding.buttonTask8.setOnClickListener {
            intent.setClassName("ru.dariamikhailukova.task8",
                "ru.dariamikhailukova.task8.MainActivity")
            startActivity(intent)
        }


    }
}
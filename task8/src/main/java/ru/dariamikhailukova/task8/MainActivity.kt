package ru.dariamikhailukova.task8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.dariamikhailukova.task8.data.NoteDatabase
import ru.dariamikhailukova.task8.data.NoteRepository
import ru.dariamikhailukova.task8.databinding.ActivityMainBinding
import ru.dariamikhailukova.task8.workManager.MyWorker

/**
 * View класс для activity_main
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteDao = NoteDatabase.getDatabase(this).noteDao()
        repository = NoteRepository(noteDao)


        setSupportActionBar(findViewById(R.id.myToolbar))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        lateinit var repository: NoteRepository
    }

}
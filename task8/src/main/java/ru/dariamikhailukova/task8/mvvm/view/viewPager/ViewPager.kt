package ru.dariamikhailukova.task8.mvvm.view.viewPager

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import ru.dariamikhailukova.task8.R
import ru.dariamikhailukova.task8.data.Note
import ru.dariamikhailukova.task8.databinding.ActivityViewPagerBinding
import ru.dariamikhailukova.task8.factory.MyViewModelFactory
import ru.dariamikhailukova.task8.factory.ViewModelTypes
import ru.dariamikhailukova.task8.mvvm.view.current.CurrentFragment
import ru.dariamikhailukova.task8.mvvm.viewModel.viewPager.ViewPagerModel

/**
 * View класс для работы с activity_view_pager
 */
class ViewPager : AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding
    private lateinit var mViewPagerModel: ViewPagerModel
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        adapter = ViewPagerAdapter(this)

        val viewPagerModelFactory = MyViewModelFactory(application, ViewModelTypes.VIEW_PAGER)
        mViewPagerModel = ViewModelProvider(this, viewPagerModelFactory).get(ViewPagerModel::class.java)

        setSupportActionBar(findViewById(R.id.myToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var currentItem: Int
        mViewPagerModel.readAllData.observe(this, { note ->
            currentItem = findPos(note, intent.getLongExtra(NUMBER, 0))
            adapter.setData(note)
            binding.viewPager.adapter = adapter
            binding.viewPager.setCurrentItem(currentItem, false)
        })

    }

    // Функция возвращает позицию заметки с ключом id в списке note
    private fun findPos(note: List<Note>, id: Long): Int{
        return note.indexOf(note.find{ it.id == id })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.view_pager_menu, menu)
        return true
    }

    /**
     * @return фрагмент, который в данный момент открыт в [fragmentManager]
     */
    private fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentByTag("f$currentItem")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myFragment: CurrentFragment = binding.viewPager.findCurrentFragment(supportFragmentManager) as CurrentFragment

        if (item.itemId == R.id.menu_delete) {
            myFragment.deleteNote()
        }

        if (item.itemId == R.id.menu_share) {
            myFragment.sendEmail()
        }

        if (item.itemId == R.id.menu_save) {
            myFragment.updateItem()
        }

        if (item.itemId == R.id.menu_backup) {
            myFragment.backup()
        }

        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val NUMBER = "Current item"
    }
}
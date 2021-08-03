package ru.dariamikhailukova.task4.mvp.view.viewPager

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import ru.dariamikhailukova.task4.R
import ru.dariamikhailukova.task4.data.NoteViewModel
import ru.dariamikhailukova.task4.databinding.FragmentPagerBinding
import ru.dariamikhailukova.task4.mvp.presenter.viewPager.ViewPagerAdapter
import ru.dariamikhailukova.task4.mvp.presenter.viewPager.ViewPagerFragmentPresenter
import ru.dariamikhailukova.task4.mvp.view.current.CurrentFragment

/**
 * View класс для работы с fragment_pager
 */
class ViewPagerFragment : Fragment(), ViewPager {
    private val args by navArgs<ViewPagerFragmentArgs>()
    private lateinit var binding: FragmentPagerBinding
    private lateinit var viewModel: NoteViewModel

    private var presenter: ViewPagerFragmentPresenter? = null
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        presenter = ViewPagerFragmentPresenter(viewModel)
        setHasOptionsMenu(true)

        adapter = ViewPagerAdapter(this)

        val allData = presenter?.getAllData()
        allData?.observe(viewLifecycleOwner) { notes ->
            adapter.setData(notes)
            binding.viewPager.adapter = adapter
            binding.viewPager.setCurrentItem(args.pos, false)
        }

        return binding.root
    }

    /**
     * @return фрагмент, который в данный момент открыт в [fragmentManager]
     */
    override fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentByTag("f$currentItem")
    }

    /**
     * Функция для создания меню
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.current_menu, menu)
    }

    /**
     * Функция, которая следит за нажатием кнопок меню
     *
     * @param item возможные элементы меню
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            val myFragment: CurrentFragment = binding.viewPager.findCurrentFragment(childFragmentManager) as CurrentFragment
            myFragment.deleteNote()
        }

        if(item.itemId == R.id.menu_share){
            val myFragment: CurrentFragment = binding.viewPager.findCurrentFragment(childFragmentManager) as CurrentFragment
            myFragment.sendTo()
        }

        if(item.itemId == R.id.menu_save){
            val myFragment: CurrentFragment = binding.viewPager.findCurrentFragment(childFragmentManager) as CurrentFragment
            myFragment.updateItem()

        }
        return super.onOptionsItemSelected(item)
    }

}
package ru.dariamikhailukova.task4.mvp.view.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dariamikhailukova.task4.R
import ru.dariamikhailukova.task4.data.NoteViewModel
import ru.dariamikhailukova.task4.databinding.FragmentListBinding
import ru.dariamikhailukova.task4.mvp.presenter.list.ListAdapter
import ru.dariamikhailukova.task4.mvp.presenter.list.ListFragmentPresenter

/**
 * View класс для работы с fragment_list
 */
class ListFragment : Fragment(), ListView {
    private var presenter: ListFragmentPresenter? = null
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: NoteViewModel
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        presenter = ListFragmentPresenter(this, viewModel)
        setHasOptionsMenu(true)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val allData = presenter?.getAllData()
        allData?.observe(viewLifecycleOwner, { note ->
            adapter.setData(note)
        })



        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root
    }


    /**
     * Функция для создания меню
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    /**
     * Функция, которая следит за нажатием кнопок меню
     *
     * @param item возможные элементы меню
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllNotes()
        }

        if(item.itemId == R.id.menu_about){
            Toast.makeText(requireContext(), "ALL OF YOU IS GODS", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Функция, которая выводит диалоговое окно для удаления всех заметок
     */
    override fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            presenter?.deleteAll()
        }

        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure?")
        builder.create().show()
    }

    /**
     * Функция для вывода любого короткого сообения-тоста
     */
    override fun showToast(text: Int) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}
package ru.dariamikhailukova.task3.mvp.view.list

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dariamikhailukova.task3.AboutActivity
import ru.dariamikhailukova.task3.R
import ru.dariamikhailukova.task3.data.NoteViewModel
import ru.dariamikhailukova.task3.databinding.FragmentListBinding
import ru.dariamikhailukova.task3.mvp.presenter.list.ListFragmentPresenter

/**
 * View класс для работы с fragment_list
 */
class ListFragment : Fragment(), ListView {
    private var presenter: ListFragmentPresenter? = null
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        presenter = ListFragmentPresenter(this, viewModel)
        setHasOptionsMenu(true)

        val adapter = ListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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
            val intent = Intent(activity, AboutActivity::class.java)
            startActivity(intent)
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
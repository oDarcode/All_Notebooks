package ru.dariamikhailukova.task8.mvvm.view.list

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import ru.dariamikhailukova.task8.factory.MyViewModelFactory
import ru.dariamikhailukova.task8.factory.ViewModelTypes
import ru.dariamikhailukova.task8.internet.CustomHtml
import ru.dariamikhailukova.task8.internet.Internet
import ru.dariamikhailukova.task8.internet.Location
import ru.dariamikhailukova.task8.R
import ru.dariamikhailukova.task8.databinding.FragmentListBinding
import ru.dariamikhailukova.task8.mvvm.viewModel.list.ListViewModel
import ru.dariamikhailukova.task8.workManager.MyWorker
import java.util.concurrent.TimeUnit

/**
 * View класс для работы с fragment_list
 */
class ListFragment : Fragment(), ListView, SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentListBinding
    private lateinit var mListViewModel: ListViewModel
    private val mAdapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        val listViewModelFactory = MyViewModelFactory(ViewModelTypes.LIST)
        mListViewModel = ViewModelProvider(this, listViewModelFactory).get(ListViewModel::class.java)

        setHasOptionsMenu(true)

        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        mListViewModel.readAllData.observe(viewLifecycleOwner, { note ->
            mAdapter.setData(note)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        subscribeToViewModel()

        return binding.root
    }


    /**
     * Функция, которая следит за изменением [mListViewModel]
     */
    override fun subscribeToViewModel(){
        mListViewModel.onAllDeleteSuccess.observe(this){
            Toast.makeText(requireContext(), R.string.remove_all, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllNotes()
        }

        if(item.itemId == R.id.menu_google){
            val intent = Intent(activity, Internet::class.java)
            startActivity(intent)
        }

        if(item.itemId == R.id.menu_customHtml){
            val intent = Intent(activity, CustomHtml::class.java)
            startActivity(intent)
        }

        if(item.itemId == R.id.menu_location){
            val intent = Intent(activity, Location::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Функция, которая выводит диалоговое окно для удаления всех заметок
     */
    override fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.yes){_,_->
            mListViewModel.deleteAllNotes()
        }

        builder.apply {
            setNegativeButton(R.string.no){_,_->}
            setTitle(getString(R.string.delete_everything))
            setMessage(R.string.are_you_sure)
            create().show()
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    // Функция выполняет поиск заметки, в заголовке которой присутствует последовательность символов query
    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        mListViewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                mAdapter.setData(it)
            }
        })
    }
}
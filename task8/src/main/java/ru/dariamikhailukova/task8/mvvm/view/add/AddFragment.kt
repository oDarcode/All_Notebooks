package ru.dariamikhailukova.task8.mvvm.view.add

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.dariamikhailukova.task8.factory.MyViewModelFactory
import ru.dariamikhailukova.task8.factory.ViewModelTypes
import ru.dariamikhailukova.task8.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.task8.R
import ru.dariamikhailukova.task8.databinding.FragmentAddBinding
import ru.dariamikhailukova.task8.retrofit.PostRepository
import ru.dariamikhailukova.task8.retrofit.PostViewModel
import ru.dariamikhailukova.task8.retrofit.PostViewModelFactory

/**
 * View класс для работы с fragment_add
 */
class AddFragment : Fragment(){
    private lateinit var binding: FragmentAddBinding
    private lateinit var mAddViewModel:
            AddViewModel
    private lateinit var mPostViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        val addViewModelFactory = MyViewModelFactory(requireContext(), ViewModelTypes.ADD)
        mAddViewModel = ViewModelProvider(this, addViewModelFactory).get(AddViewModel::class.java)
        setHasOptionsMenu(true)

        binding.addViewModel = mAddViewModel
        binding.lifecycleOwner = this
        subscribeToViewModel()

        val repository = PostRepository()
        val viewModelFactory = PostViewModelFactory(repository)
        mPostViewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)

        return binding.root
    }

    /**
     * Функция, которая следит за изменением [mAddViewModel]
     */
    private fun subscribeToViewModel(){
        mAddViewModel.onAttemptSaveEmptyNote.observe(this){
            Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
        }

        mAddViewModel.onSaveSuccess.observe(this){
            Toast.makeText(requireContext(), R.string.successfully, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    /**
     * Функция для создания меню
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    /**
     * Функция, которая следит за нажатием кнопок меню
     *
     * @param item возможные элементы меню
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save){
            mAddViewModel.addNote()
        }

        if(item.itemId == R.id.menu_download){
            getCurrentData()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Функция, которая получает заметку из сети
     */
    private fun getCurrentData(){
        mPostViewModel.getPost()
        mPostViewModel.myResponse.observe(this, { response ->
            if(response.isSuccessful){
                Log.d(TAG, "Response id " + response.body()?.id.toString())
                mAddViewModel.name.value = response.body()?.title!!
                mAddViewModel.text.value = response.body()?.body!!
            }else{
                Log.d(TAG, "Error")
            }
        })
    }

    companion object{
        const val TAG = "AddFragment"
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
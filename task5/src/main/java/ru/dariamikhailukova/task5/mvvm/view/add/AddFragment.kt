package ru.dariamikhailukova.task5.mvvm.view.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.dariamikhailukova.task5.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.task5.R
import ru.dariamikhailukova.task5.data.NoteDatabase
import ru.dariamikhailukova.task5.data.NoteRepository
import ru.dariamikhailukova.task5.databinding.FragmentAddBinding

/**
 * View класс для работы с fragment_add
 */
class AddFragment : Fragment(){
    private lateinit var binding: FragmentAddBinding
    private lateinit var mAddViewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        mAddViewModel = AddViewModel(NoteRepository(noteDao))
        setHasOptionsMenu(true)

        binding.addViewModel = mAddViewModel
        binding.lifecycleOwner = this
        subscribeToViewModel()

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

        return super.onOptionsItemSelected(item)
    }


}
package com.pamiracar.todap.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pamiracar.todap.databinding.FragmentTodoBinding
import com.pamiracar.todap.model.Todo
import com.pamiracar.todap.roomdb.TodoDAO
import com.pamiracar.todap.roomdb.TodoDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class todoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val mDisposable = CompositeDisposable()
    private var secilenTodo : Todo? = null

    lateinit var db : TodoDatabase
    lateinit var todoDAO : TodoDAO



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext(),TodoDatabase::class.java, "Todolar").build()
        todoDAO = db.todoDAO()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.todoFragmentKaydetButton.setOnClickListener {
            val baslik = binding.todoFragmentBaslikEditText.text.toString()
            val metin = binding.todoFragmentMetinEditText.text.toString()

            val todo = Todo(baslik, metin)

            mDisposable.add(
                todoDAO.insert(todo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponseForInsert)
            )


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleResponseForInsert() {
        //Bir önceki fragmenta dön
        val action = todoFragmentDirections.actionTodoFragmentToListeFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

}
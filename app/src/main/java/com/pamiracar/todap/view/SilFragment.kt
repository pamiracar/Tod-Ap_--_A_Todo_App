package com.pamiracar.todap.view

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.room.Room
import com.pamiracar.todap.R
import com.pamiracar.todap.databinding.FragmentListeBinding
import com.pamiracar.todap.databinding.FragmentSilBinding
import com.pamiracar.todap.databinding.FragmentTodoBinding
import com.pamiracar.todap.model.Todo
import com.pamiracar.todap.roomdb.TodoDAO
import com.pamiracar.todap.roomdb.TodoDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SilFragment : Fragment() {
    private var _binding: FragmentSilBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val mDisposable = CompositeDisposable()
    private lateinit var db : TodoDatabase
    private lateinit var todoDAO: TodoDAO
    private var secilenTodo : Todo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext(),TodoDatabase::class.java,"Todolar").build()
        todoDAO = db.todoDAO()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val id = SilFragmentArgs.fromBundle(it).id
            mDisposable.add(
                todoDAO.findById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )
            binding.SilFragmentSilButton.setOnClickListener {

                mDisposable.add(
                    todoDAO.delete(todo = secilenTodo!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponseForInsert)
                )
            }
        }

        binding.SilFragmentIptalButton.setOnClickListener {
            val action = SilFragmentDirections.actionSilFragmentToListeFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleResponseForInsert(){
        //Bir önceki fragmenta dön
        val action = SilFragmentDirections.actionSilFragmentToListeFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun handleResponse(todo: Todo){
        secilenTodo = todo
    }


}
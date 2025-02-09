package com.pamiracar.todap.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.pamiracar.todap.adapter.todoAdapter
import com.pamiracar.todap.databinding.FragmentListeBinding
import com.pamiracar.todap.model.Todo
import com.pamiracar.todap.roomdb.TodoDAO
import com.pamiracar.todap.roomdb.TodoDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class listeFragment : Fragment() {

    private var _binding: FragmentListeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val mDisposable = CompositeDisposable()
    private lateinit var db : TodoDatabase
    private lateinit var todoDAO: TodoDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext(),TodoDatabase::class.java,"Todolar").build()
        todoDAO = db.todoDAO()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listeFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        verileriAl()
        binding.listeFragmentFAB.setOnClickListener{
            val action = listeFragmentDirections.actionListeFragmentToTodoFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun handleResponse(todolar : List<Todo>) {
        val adapter = todoAdapter(todolar)
        binding.listeFragmentRecyclerView.adapter = adapter
    }

    private  fun verileriAl() {
        mDisposable.add(
            todoDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )
    }

}
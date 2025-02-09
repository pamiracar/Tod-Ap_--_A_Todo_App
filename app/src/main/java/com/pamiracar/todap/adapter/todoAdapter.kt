package com.pamiracar.todap.adapter

import android.Manifest
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.pamiracar.todap.databinding.RecyclerRowBinding
import com.pamiracar.todap.model.Todo
import com.pamiracar.todap.roomdb.TodoDAO
import com.pamiracar.todap.roomdb.TodoDatabase
import com.pamiracar.todap.view.listeFragmentDirections
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class todoAdapter(val todoListesi : List<Todo>) : RecyclerView.Adapter<todoAdapter.TodoHolder>() {



    class TodoHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): todoAdapter.TodoHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return TodoHolder(recyclerRowBinding)

    }

    override fun getItemCount(): Int {
        return todoListesi.size
    }

    override fun onBindViewHolder(holder: todoAdapter.TodoHolder, position: Int) {
        holder.binding.RecyclerRowTodoTitle.text = todoListesi[position].baslik
        holder.binding.RecyclerRowTodoName.text = todoListesi[position].metin
        holder.itemView.setOnClickListener {
            val action = listeFragmentDirections.actionListeFragmentToSilFragment(id = todoListesi[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }




}
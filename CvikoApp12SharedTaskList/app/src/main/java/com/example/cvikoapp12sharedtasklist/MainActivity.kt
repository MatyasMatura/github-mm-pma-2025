package com.example.cvikoapp12sharedtasklist

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvikoapp12sharedtasklist.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore

        // Nastavení adapteru
        adapter = TaskAdapter(
            tasks = emptyList(),
            onChecked = { task -> toggleCompleted(task) },
            onDelete = { task -> deleteTask(task) },
            onEdit = { task -> showEditDialog(task) }
        )

        binding.recyclerViewTasks.adapter = adapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)

        // Přidání úkolu
        binding.buttonAdd.setOnClickListener {
            val title = binding.inputTask.text.toString()
            if (title.isNotEmpty()) {
                addTask(title)
                binding.inputTask.text.clear()
            }
        }

        // Realtime sledování Firestore
        listenForTasks()
    }

    private fun addTask(title: String) {
        println("DEBUG: addTask called with title = $title")
        val task = Task(title = title, completed = false)
        db.collection("tasks").add(task)
    }

    private fun toggleCompleted(task: Task) {
        // Update the task directly by its ID
        if (task.id.isNotEmpty()) {
            db.collection("tasks")
                .document(task.id)
                .update("completed", !task.completed)
        }
    }

    private fun deleteTask(task: Task) {
        // Delete the task directly by its ID
        if (task.id.isNotEmpty()) {
            db.collection("tasks").document(task.id).delete()
        }
    }

    private fun showEditDialog(task: Task) {
        // Vytvoření EditText pro zadání nového názvu
        val editText = EditText(this).apply {
            setText(task.title)
            hint = "Upravit název úkolu"
            setPadding(50, 40, 50, 40)
        }

        // Vytvoření a zobrazení dialogu
        AlertDialog.Builder(this)
            .setTitle("Upravit úkol")
            .setView(editText)
            .setPositiveButton("Uložit") { _, _ ->
                val newTitle = editText.text.toString()
                if (newTitle.isNotEmpty()) {
                    editTask(task, newTitle)
                }
            }
            .setNegativeButton("Zrušit", null)
            .show()
    }

    private fun editTask(task: Task, newTitle: String) {
        // Aktualizace názvu úkolu v Firestore
        if (task.id.isNotEmpty()) {
            db.collection("tasks")
                .document(task.id)
                .update("title", newTitle)
        }
    }

    private fun listenForTasks() {
        db.collection("tasks")
            // Sleduje kolekci tasks v reálném čase
            .addSnapshotListener { snapshots, _ ->
                // Převede dokumenty z Firestore na seznam objektů Task, včetně jejich ID
                val taskList = snapshots?.documents?.map { doc ->
                    doc.toObject(Task::class.java)?.copy(id = doc.id) ?: Task()
                } ?: emptyList()
                // Aktualizuje RecyclerView novým seznamem úkolů
                adapter.submitList(taskList)
            }
    }
}
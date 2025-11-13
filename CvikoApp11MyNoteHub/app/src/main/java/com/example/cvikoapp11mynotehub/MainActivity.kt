package com.example.cvikoapp11mynotehub

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvikoapp11mynotehub.data.Note
import com.example.cvikoapp11mynotehub.data.NoteDao
import com.example.cvikoapp11mynotehub.data.NoteHubDatabaseInstance
import com.example.cvikoapp11mynotehub.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteDao: NoteDao
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Po kliknutí na FAB otevře AddNoteActivity
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        /* Zobrazování poznámek */

        // 1) Vytvoření adaptéru
        val adapter = NoteAdapter(
        onEditClick = { note ->
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra("note_id", note.id)
            startActivity(intent)
        },
        onDeleteClick = { note ->
            deleteNote(note)
        }
        )

        // 2) Nastavení RecyclerView
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotes.adapter = adapter

        // 3) Získání DAO
        noteDao = NoteHubDatabaseInstance.getDatabase(applicationContext).noteDao()

        // 4) Pozorování dat pomocí Flow z Room
        // Spustí korutinu v rámci životního cyklu aktivity.
        // Když se aktivita zničí, korutina se automaticky ukončí.
        lifecycleScope.launch {
            // Sleduje Flow. Kdykoli se změní data v DB → seznam se okamžitě obnoví.
            noteDao.getAllNotes().collectLatest { notes ->
                //Pošle nová data do adapteru, aby se překreslil RecyclerView.
                adapter.submitList(notes)
            }
        }
    }
    private fun deleteNote(note: Note) {
        // Zobrazí potvrzovací dialog
        AlertDialog.Builder(this)
            .setTitle("Smazat poznámku")
            .setMessage("Opravdu chcete smazat tuto poznámku?")
            .setPositiveButton("ANO") { dialog, _ ->
                // Uživatel potvrdil, smazat
                lifecycleScope.launch(Dispatchers.IO) {
                    noteDao.delete(note)
                }
                dialog.dismiss()
            }
            .setNegativeButton("NE") { dialog, _ ->
                // Uživatel zrušil, nic nedělat
                dialog.dismiss()
            }
            .show()
    }
}
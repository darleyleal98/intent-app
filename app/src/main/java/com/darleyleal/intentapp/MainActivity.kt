package com.darleyleal.intentapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.Companion
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.darleyleal.intentapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var result: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonContinuar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            if (nome.isNullOrEmpty()) {
                Toast.makeText(this, "O campo nome é obrigatório", Toast.LENGTH_SHORT).show()
            }
            if (nome.length <= 2){
                Toast.makeText(this, "O nome deve mais de 2 caracteres!", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("nome", nome)
                startActivity(intent)
            }
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                val nome = it.data?.getStringExtra("nome").toString()
                binding.editNome.setText("$nome")
            }
        }
    }
}
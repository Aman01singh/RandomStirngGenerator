package com.iav.randomstringgenerator.ui.activity.main_

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iav.randomstringgenerator.data.remote.response.random_string_res.RandomString
import com.iav.randomstringgenerator.databinding.ActivityMainBinding
import com.iav.randomstringgenerator.network.NetworkRepository
import com.iav.randomstringgenerator.ui.adapter.RandomStringAdapter
import com.iav.randomstringgenerator.ui.viewmodel.HomeViewModel
import com.iav.randomstringgenerator.ui.viewmodel.main_.HomeViewModelFactory
import org.json.JSONObject
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RandomStringAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(NetworkRepository(contentResolver))
        )[HomeViewModel::class.java]

        initRecycler()
        setupObservers()
        buttonClicks()
    }

    private fun initRecycler() {
        adapter = RandomStringAdapter(mutableListOf()) { position ->
            viewModel.deleteItem(position)
        }
        binding.recyclerViewStrings.adapter = adapter
        binding.recyclerViewStrings.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        viewModel.strings.observe(this) {
            adapter.updateList(it)
        }

        viewModel.error.observe(this) { message ->
            binding.textViewError.visibility = if (message == null) View.GONE else View.VISIBLE
            binding.textViewError.text = message
        }
        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.buttonGenerate.isEnabled = !loading
            binding.buttonClearAll.isEnabled = !loading
        }
    }

    private fun buttonClicks() {
        binding.buttonGenerate.setOnClickListener {
            val length = binding.editTextLength.text.toString()
            if (length.isEmpty()) {
                binding.textViewError.text = "Please enter length"
                binding.textViewError.visibility = View.VISIBLE
            } else {
                viewModel.generateRandomString()
            }
        }
        binding.buttonClearAll.setOnClickListener { viewModel.clearAll() }
    }
}

package com.omarshafei.mvi_architecture_pattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var button: Button
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text)
        button = findViewById(R.id.btn)

        button.setOnClickListener {
//            send
                lifecycleScope.launchWhenStarted {
                    viewModel.intentChannel.send(MainIntent.addNumberIntent)
                }
        }

        render()

    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewSate.collect {
                textView.text = when(it) {
                    is MainViewState.Idle -> "Idel"
                    is MainViewState.Number -> it.number.toString()
                    is MainViewState.Error -> it.error
                    else -> ""
                }
            }
        }
    }
}
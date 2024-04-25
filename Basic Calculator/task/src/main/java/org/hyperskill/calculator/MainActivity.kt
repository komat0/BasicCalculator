package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var buffer = ""
    private var firstNumber: Double = 0.0
    private var secondNumber: Double = 0.0
    private var sign: Char = ' '
    private var lastSign: Char = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.displayEditText.inputType = InputType.TYPE_NULL

        binding.equalButton.setOnClickListener {
            if (sign != '=' && buffer.isNotEmpty()) {
                secondNumber = buffer.toDouble()
                calculation(sign)
            } else if (sign != '=') {
                secondNumber = firstNumber
                calculation(sign)
            } else
                calculation(lastSign)
        }
        binding.divideButton.setOnClickListener {
            sign = '/'
            tapToSign()
        }
        binding.multiplyButton.setOnClickListener {
            sign = '*'
            tapToSign()
        }
        binding.subtractButton.setOnClickListener {
            if (sign == '=') { // For a last test
                sign = '-'
            } else if (buffer.isEmpty()) {
                buffer = "-"
                binding.displayEditText.setText(buffer)
            } else if (buffer == "-") {
                buffer = ""
                binding.displayEditText.setText(buffer)
            } else {
                sign = '-'
                tapToSign()
            }
        }
        binding.addButton.setOnClickListener {
            sign = '+'
            tapToSign()
        }
        binding.dotButton.setOnClickListener {
            if (buffer == "" || buffer == "0") {
                buffer = "0."
            } else if (buffer == "-") {
                buffer += "0."
            } else if (!buffer.contains('.')) {
                buffer += "."
            }
            binding.displayEditText.setText(buffer)
        }

        binding.clearButton.setOnClickListener {
            buffer = ""
            firstNumber = 0.0
            secondNumber = 0.0
            binding.displayEditText.text.clear()
            binding.displayEditText.setHint("0")
        }
        binding.button0.setOnClickListener { digitalClick('0') }
        binding.button1.setOnClickListener { digitalClick('1') }
        binding.button2.setOnClickListener { digitalClick('2') }
        binding.button3.setOnClickListener { digitalClick('3') }
        binding.button4.setOnClickListener { digitalClick('4') }
        binding.button5.setOnClickListener { digitalClick('5') }
        binding.button6.setOnClickListener { digitalClick('6') }
        binding.button7.setOnClickListener { digitalClick('7') }
        binding.button8.setOnClickListener { digitalClick('8') }
        binding.button9.setOnClickListener { digitalClick('9') }
    }

    private fun tapToSign() {
        if (buffer.isNotEmpty()) {
            firstNumber = buffer.toDouble()
            binding.displayEditText.text.clear()
            binding.displayEditText.hint = zeroEndRemover(buffer)
            buffer = ""
        }
    }

    private fun calculation(s: Char) {
        val result: Double = when (s) {
            '+' -> firstNumber + secondNumber
            '-' -> firstNumber - secondNumber
            '*' -> firstNumber * secondNumber
            '/' -> firstNumber / secondNumber
            else -> 0.0
        }
        firstNumber = result
        binding.displayEditText.text.clear()
        binding.displayEditText.hint = zeroEndRemover(result.toString())
        buffer = ""
        lastSign = s
        sign = '='
    }

    private fun zeroBeginRemover() {
        if (buffer == "-0" || buffer == "0") {
            buffer = buffer.dropLast(1)
            binding.displayEditText.setText(buffer)
        }
    }

    private fun zeroEndRemover(res: String): String {
        var result = res
        if (result.contains('.')) {
            while (result.endsWith('0')) {
                result = result.dropLast(1)
                if (result.endsWith('.')) {
                    result = result.dropLast(1)
                    break
                }
            }
        }
        return result
    }

    private fun digitalClick(value: Char) {
        zeroBeginRemover()
        buffer += value
        binding.displayEditText.setText(buffer)
    }
}
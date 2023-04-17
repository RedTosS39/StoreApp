package com.example.storeapp.presentation.adapters

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.storeapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface ShouldBeClose {

    fun close()
}

@BindingAdapter("setTextFromEditText")
fun bindEditText(editText: TextInputEditText, text: String) {
    editText.setText(text)
}

@BindingAdapter("errorInputName")
fun bindInputNameError(inputTextInputLayout: TextInputLayout, inputNameError: Boolean) {
    val massage = if (inputNameError) {
        inputTextInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    inputTextInputLayout.error = massage
}
@BindingAdapter("errorInputCount")
fun bindInputCountError(inputTextInputLayout: TextInputLayout, inputCount: Boolean) {
    val massage = if (inputCount) {
        inputTextInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    inputTextInputLayout.error = massage
}


@BindingAdapter("setCloseButton")
fun bindCloseButton(button: Button,shouldBeClose: ShouldBeClose ) {
    shouldBeClose.close()
}

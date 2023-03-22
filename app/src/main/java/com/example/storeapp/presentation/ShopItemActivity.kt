package com.example.storeapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.R
import com.google.android.material.textfield.TextInputEditText

class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: ShopItemViewModel
    lateinit var inputEditTextName: TextInputEditText
    lateinit var inputEditTextCount: TextInputEditText
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        setupViews()

        when(intent.getStringExtra(EXTRA_SCREEN_MODE)) {
            MODE_ADD -> { viewModel.addShopItem(inputEditTextName.text.toString(), inputEditTextCount.text.toString()) }
            MODE_EDIT ->{ viewModel.editShopItem(inputEditTextName.text.toString(), inputEditTextCount.text.toString()) }
            else -> {}
        }

        viewModel.inputCountError.observe(this@ShopItemActivity) {
            if (it == false) {
                Toast.makeText(this@ShopItemActivity, "Wrong number", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.inputNameError.observe(this@ShopItemActivity) {
            if (it == false) {
                Toast.makeText(this@ShopItemActivity, "Wrong name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViews() {
        viewModel = ViewModelProvider(this@ShopItemActivity)[ShopItemViewModel::class.java]
        inputEditTextName = findViewById(R.id.et_name)
        inputEditTextCount = findViewById(R.id.et_count)
        button = findViewById(R.id.button_save)
        button.setOnClickListener {

        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"

        fun newIntentAddItem(context: Context) : Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int) : Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SCREEN_MODE, shopItemId)
            return intent
        }
    }
}
package com.oluap.introrealmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.oluap.introrealmdb.database.DatabaseFactory
import com.oluap.introrealmdb.databinding.ActivityMainBinding
import com.oluap.introrealmdb.model.ItemDB

class MainActivity : AppCompatActivity() {

    //region String
    private val TAG = "MainActivity"
    //endregion

    //region Binding
    private lateinit var binding: ActivityMainBinding
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //region Initial settings
        supportActionBar!!.hide()
        //endregion

        initListeners()

    }

    private fun initListeners(){
        try{
            binding.btnCreate.setOnClickListener {
                validateItem()
            }
        }catch (ex: Exception){
            Log.e(TAG, "Erro na função initListeners: "+ex.message, ex)
        }
    }

    private fun validateItem(){
        try{
            val summary = binding.edtSummary.text.toString()
            
            if(summary.isEmpty()){
                Toast.makeText(this, "Inform the summary!", Toast.LENGTH_SHORT).show()
                return
            }

            createItem(summary, binding.switchCompleted.isChecked)
        }catch (ex: Exception){
            Log.e(TAG, "Erro na função validateItem: "+ex.message, ex)
        }
    }

    private fun createItem(sum: String, completed: Boolean){
        try{
            DatabaseFactory.getRealm().writeBlocking {
                copyToRealm(ItemDB().apply {
                    summary     = sum
                    isComplete  = completed
                })
            }

            binding.edtSummary.text!!.clear()

            Toast.makeText(this, "Item created!", Toast.LENGTH_SHORT).show()
        }catch (ex: Exception){
            Log.e(TAG, "Erro na função createItem: "+ex.message, ex)
        }
    }

}
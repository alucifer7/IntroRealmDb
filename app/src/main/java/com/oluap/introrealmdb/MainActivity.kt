package com.oluap.introrealmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                DatabaseFactory.getRealm().writeBlocking {
                    copyToRealm(ItemDB().apply {
                        summary     = "Do the laundry"
                        isComplete  = false
                    })
                }
            }
        }catch (ex: Exception){
            Log.e(TAG, "Erro na função initListeners: "+ex.message, ex)
        }
    }

}
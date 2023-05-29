package com.oluap.introrealmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.oluap.introrealmdb.adapter.AdapterItems
import com.oluap.introrealmdb.database.DatabaseFactory
import com.oluap.introrealmdb.databinding.ActivityMainBinding
import com.oluap.introrealmdb.model.ItemDB
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class MainActivity : AppCompatActivity() {

    //region String
    private val TAG = "MainActivity"
    //endregion

    //region Binding
    private lateinit var binding: ActivityMainBinding
    //endregion

    //region Realm
    private lateinit var realm: Realm
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //region Initial settings
        supportActionBar!!.hide()
        //endregion

        realm = DatabaseFactory.getRealm()

        initListeners()
        readItems()

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
            realm.writeBlocking {
                copyToRealm(ItemDB().apply {
                    summary     = sum
                    isComplete  = completed
                })
            }

            binding.edtSummary.text!!.clear()

            Toast.makeText(this, "Item created!", Toast.LENGTH_SHORT).show()

            readItems()
        }catch (ex: Exception){
            Log.e(TAG, "Erro na função createItem: "+ex.message, ex)
        }
    }

    private fun readItems(){
        try{
            //todos os itens
            val items: RealmResults<ItemDB> = realm.query<ItemDB>().find()
            val listItems: MutableList<ItemDB> = arrayListOf()
            listItems.addAll(items)

            //itens cujo nome começa com a letra 'D'
            //val itemsThatBeginWIthD: RealmResults<ItemDB> = realm.query<ItemDB>("summary BEGINSWITH $0", "D").find()

            //itens de tarefas que ainda não foram concluídos
            //val incompleteItems: RealmResults<ItemDB> = realm.query<ItemDB>("isComplete == false").find()

            if(listItems.isEmpty()){
                Toast.makeText(this, "No items found!", Toast.LENGTH_SHORT).show()
                return
            }
            
            showItems(listItems)
        }catch (ex: Exception){
            Log.e(TAG, "Erro na função readItems: "+ex.message, ex)
        }
    }

    private fun showItems(items: List<ItemDB>){
        try{
            val adapter = AdapterItems(items)
            binding.rvItens.adapter = adapter
            binding.rvItens.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }catch (ex: Exception){
            Log.e(TAG, "Erro na função showItems: "+ex.message, ex)
        }
    }

}
package com.oluap.introrealmdb.database

import com.oluap.introrealmdb.model.ItemDB
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

/**
 * Created by Paulo Fernandes on 27,maio,2023
 */
object DatabaseFactory {

    private val realm: Realm

    init {
        val config = RealmConfiguration.create(schema = setOf(ItemDB::class))
        realm = Realm.open(config)
    }

    fun getRealm(): Realm{
        return realm
    }

}
package com.smendon.sneakersapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smendon.sneakersapp.domain.model.CartItem


@Database(
    entities = [CartItem::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val appDao: AppDao

    companion object {
        const val DATABASE_NAME = "sneakership_db"
        const val TABLE_CART = "table_cart"
    }
}
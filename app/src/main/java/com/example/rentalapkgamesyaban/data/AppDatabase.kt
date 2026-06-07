package com.example.rentalapkgamesyaban.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.rentalapkgamesyaban.data.dao.CartDao
import com.example.rentalapkgamesyaban.data.dao.ProductDao
import com.example.rentalapkgamesyaban.data.dao.RentalDao
import com.example.rentalapkgamesyaban.data.dao.UserDao
import com.example.rentalapkgamesyaban.data.entity.CartEntity
import com.example.rentalapkgamesyaban.data.entity.ProductEntity
import com.example.rentalapkgamesyaban.data.entity.RentalEntity
import com.example.rentalapkgamesyaban.data.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class, ProductEntity::class, CartEntity::class, RentalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun rentalDao(): RentalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rentalsphere_database"
                )
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val userDao = database.userDao()
                    // Seed initial data
                    userDao.insertUser(
                        UserEntity(
                            name = "Admin",
                            email = "admin@gmail.com",
                            password = "password123",
                            role = "admin"
                        )
                    )
                    userDao.insertUser(
                        UserEntity(
                            name = "Buyer",
                            email = "buyer@gmail.com",
                            password = "password123",
                            role = "buyer"
                        )
                    )
                    
                    val productDao = database.productDao()
                    productDao.insertProduct(
                        ProductEntity(
                            name = "Minecraft",
                            platform = "game",
                            description = "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles.",
                            price = 50000.0,
                            externalLink = "https://www.minecraft.net/",
                            imageUri = ""
                        )
                    )
                    productDao.insertProduct(
                        ProductEntity(
                            name = "Stardew Valley",
                            platform = "android",
                            description = "You've inherited your grandfather's old farm plot in Stardew Valley.",
                            price = 35000.0,
                            externalLink = "https://stardewvalley.net/",
                            imageUri = ""
                        )
                    )
                }
            }
        }
    }
}

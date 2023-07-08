package com.example.foodflex.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodflex.Pojo.Meal

@Database(
    entities = [Meal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MealTypeConverter::class)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun mealsDao(): MealsDao

    companion object {
        @Volatile
        private var INSTANCE: MealsDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context): MealsDatabase =
            INSTANCE ?: synchronized(LOCK) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MealsDatabase::class.java, "meals_database.db"
            ).build()
    }
}
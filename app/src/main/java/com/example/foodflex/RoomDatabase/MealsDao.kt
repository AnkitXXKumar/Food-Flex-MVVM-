package com.example.foodflex.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodflex.Pojo.Meal

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal:Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>

}
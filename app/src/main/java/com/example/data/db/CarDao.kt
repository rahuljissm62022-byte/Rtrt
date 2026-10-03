package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars ORDER BY startingPrice DESC")
    fun getAllCars(): Flow<List<Car>>

    @Query("SELECT * FROM cars WHERE id = :id")
    fun getCarById(id: Int): Flow<Car?>

    @Query("SELECT COUNT(*) FROM cars")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars(cars: List<Car>)

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)
}

package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Offer
import kotlinx.coroutines.flow.Flow

@Dao
interface OfferDao {
    @Query("SELECT * FROM offers ORDER BY id ASC")
    fun getAllOffers(): Flow<List<Offer>>

    @Query("SELECT COUNT(*) FROM offers")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffer(offer: Offer): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffers(offers: List<Offer>)

    @Update
    suspend fun updateOffer(offer: Offer)

    @Delete
    suspend fun deleteOffer(offer: Offer)

    @Query("DELETE FROM offers WHERE id = :id")
    suspend fun deleteOfferById(id: Int)
}

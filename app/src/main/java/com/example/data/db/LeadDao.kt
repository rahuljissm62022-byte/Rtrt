package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Lead
import kotlinx.coroutines.flow.Flow

@Dao
interface LeadDao {
    @Query("SELECT * FROM leads ORDER BY createdAt DESC")
    fun getAllLeads(): Flow<List<Lead>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLead(lead: Lead): Long

    @Update
    suspend fun updateLead(lead: Lead)

    @Delete
    suspend fun deleteLead(lead: Lead)

    @Query("DELETE FROM leads WHERE id = :id")
    suspend fun deleteLeadById(id: Int)
}

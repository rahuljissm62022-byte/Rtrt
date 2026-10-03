package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leads")
data class Lead(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customerName: String,
    val phone: String,
    val interestedCar: String,
    val preferredDate: String,
    val timeSlot: String,
    val city: String,
    val locationPreference: String = "Showroom Sasaram",
    val message: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val status: String = "NEW", // NEW, CONTACTED, TEST_DRIVE_SCHEDULED, COMPLETED
    val enquiryType: String = "Test Drive" // Test Drive, Best Price Quote, Finance Enquiry
)

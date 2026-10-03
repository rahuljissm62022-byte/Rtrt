package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offers")
data class Offer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val subtitle: String,
    val discountAmount: String,
    val category: String, // Festival Offer, Cash Discount, Exchange Bonus, Finance Offer, Accessories Offer, Limited Period
    val validUntil: String,
    val description: String,
    val applicableModels: String,
    val badgeText: String,
    val isFeatured: Boolean = true
)

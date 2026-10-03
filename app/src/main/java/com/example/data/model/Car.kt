package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val modelName: String,
    val tagline: String,
    val startingPrice: Double,
    val priceDisplay: String,
    val segment: String, // SUV, MPV, HATCHBACK, SEDAN
    val engine: String,
    val mileage: String,
    val transmission: String,
    val seating: String,
    val features: String, // comma-separated
    val variants: String, // comma-separated
    val colours: String, // comma-separated
    val drawableName: String,
    val customImageUri: String? = null,
    val isFeatured: Boolean = true
) {
    fun getFeaturesList(): List<String> =
        features.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    fun getVariantsList(): List<String> =
        variants.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    fun getColoursList(): List<String> =
        colours.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}

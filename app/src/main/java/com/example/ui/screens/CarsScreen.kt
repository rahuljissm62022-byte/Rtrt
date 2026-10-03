package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Car
import com.example.ui.components.CarCard
import com.example.ui.components.ShowroomCtaRow
import com.example.ui.theme.NexaCardBorder
import com.example.ui.theme.NexaCardSurface
import com.example.ui.theme.NexaChrome
import com.example.ui.theme.NexaCyanAccent
import com.example.ui.theme.NexaMidnight
import com.example.ui.theme.NexaMutedText
import com.example.ui.theme.NexaNavy
import com.example.ui.theme.NexaSilver

@Composable
fun CarsScreen(
    cars: List<Car>,
    selectedSegment: String,
    onSegmentSelected: (String) -> Unit,
    onBookTestDrive: (Car) -> Unit,
    onGetBestPrice: (Car) -> Unit,
    onViewCarDetails: (Car) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val segments = listOf(
        "ALL" to "All NEXA Models",
        "SUV" to "SUV / Crossover",
        "MPV" to "Luxury MPV",
        "HATCHBACK" to "Hatchback",
        "SEDAN" to "Sedan"
    )

    val displayedCars = cars.filter { car ->
        val matchesSegment = if (selectedSegment == "ALL") true else car.segment.equals(selectedSegment, ignoreCase = true)
        val matchesSearch = car.modelName.contains(searchQuery, ignoreCase = true) ||
                car.features.contains(searchQuery, ignoreCase = true) ||
                car.variants.contains(searchQuery, ignoreCase = true)
        matchesSegment && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NexaMidnight),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "NEXA SHOWROOM RANGE",
                    color = NexaCyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Crafted For The Driven",
                    color = NexaChrome,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Explore models available for immediate delivery at Sasaram",
                    color = NexaSilver,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Search Box
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search Grand Vitara, Fronx, Invicto, Jimny...",
                            color = NexaMutedText,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NexaNavy,
                        unfocusedContainerColor = NexaNavy,
                        focusedBorderColor = NexaCyanAccent,
                        unfocusedBorderColor = NexaCardBorder,
                        focusedTextColor = NexaChrome,
                        unfocusedTextColor = NexaChrome
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Segment Filter Tabs
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(segments) { (key, label) ->
                        val isSelected = selectedSegment.equals(key, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) NexaCyanAccent else NexaNavy)
                                .border(
                                    1.dp,
                                    if (isSelected) NexaCyanAccent else NexaCardBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onSegmentSelected(key) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = label,
                                color = if (isSelected) NexaMidnight else NexaChrome,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Showing ${displayedCars.size} Models in Sasaram",
                    color = NexaMutedText,
                    fontSize = 11.sp
                )
            }
        }

        // Cars List
        items(displayedCars) { car ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                CarCard(
                    car = car,
                    onBookTestDrive = onBookTestDrive,
                    onGetBestPrice = onGetBestPrice,
                    onViewDetails = onViewCarDetails
                )
            }
        }

        if (displayedCars.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No models match your filter",
                            color = NexaSilver,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Try clearing your search query or choosing All Models",
                            color = NexaMutedText,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Bottom CTA
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                ShowroomCtaRow(
                    customMessage = "Hello Rahul Ji! I am looking for pricing and delivery time for NEXA models at Pearl Cars Sasaram."
                )
            }
        }
    }
}

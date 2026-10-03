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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Car
import com.example.ui.components.RelationshipManagerCallCard
import com.example.ui.components.ShowroomCtaRow
import com.example.ui.theme.CallBlue
import com.example.ui.theme.NexaCardBorder
import com.example.ui.theme.NexaCardSurface
import com.example.ui.theme.NexaChrome
import com.example.ui.theme.NexaCyanAccent
import com.example.ui.theme.NexaDarkBlue
import com.example.ui.theme.NexaElectricBlue
import com.example.ui.theme.NexaGold
import com.example.ui.theme.NexaMidnight
import com.example.ui.theme.NexaMutedText
import com.example.ui.theme.NexaNavy
import com.example.ui.theme.NexaSilver
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.LeadFormState
import com.example.util.ShowroomActions

@Composable
fun TestDriveScreen(
    cars: List<Car>,
    formState: LeadFormState,
    onUpdateForm: (
        name: String?,
        phone: String?,
        interestedCar: String?,
        preferredDate: String?,
        timeSlot: String?,
        city: String?,
        locationPreference: String?,
        message: String?,
        enquiryType: String?
    ) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val carModels = if (cars.isNotEmpty()) {
        cars.map { it.modelName }
    } else {
        listOf("Grand Vitara", "Invicto", "Fronx", "Jimny", "Baleno", "XL6", "Ciaz", "Ignis")
    }

    val dateOptions = listOf("Today", "Tomorrow", "This Weekend", "Within 7 Days", "Custom Date")
    val timeOptions = listOf("10:00 AM - 01:00 PM", "01:00 PM - 04:00 PM", "04:00 PM - 07:00 PM")
    val locationOptions = listOf("Showroom Sasaram", "Doorstep / Home Visit")
    val cities = listOf("Sasaram", "Dehri-on-Sone", "Bhabua", "Bikramganj", "Mohania", "Kudra", "Chenari", "Aurangabad", "Other")

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
                    text = "EXPERIENCE THE LUXURY",
                    color = NexaCyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Book Your NEXA Test Drive",
                    color = NexaChrome,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "At Pearl Cars Sasaram showroom or complimentary at your doorstep",
                    color = NexaSilver,
                    fontSize = 12.sp
                )
            }
        }

        // Enquiry Form Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NexaNavy),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.verticalGradient(listOf(NexaCyanAccent.copy(alpha = 0.4f), NexaCardBorder))
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = "CUSTOMER ENQUIRY FORM",
                        color = NexaGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Error Banner if any
                    if (formState.errorMessage != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(NexaGold.copy(alpha = 0.2f))
                                .border(1.dp, NexaGold, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = formState.errorMessage,
                                color = NexaGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // 1. Customer Name
                    Text(
                        text = "Customer Name *",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = formState.customerName,
                        onValueChange = { onUpdateForm(it, null, null, null, null, null, null, null, null) },
                        placeholder = { Text("e.g. Ramesh Singh", color = NexaMutedText, fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = NexaCyanAccent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("lead_name_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = formFieldColors(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 2. Mobile Number
                    Text(
                        text = "Mobile Number *",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = formState.phone,
                        onValueChange = { onUpdateForm(null, it, null, null, null, null, null, null, null) },
                        placeholder = { Text("10-digit mobile number", color = NexaMutedText, fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = NexaCyanAccent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("lead_phone_input"),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = formFieldColors(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Interested Car Selector
                    Text(
                        text = "Interested NEXA Car *",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(carModels) { model ->
                            val isSelected = formState.interestedCar.equals(model, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaCyanAccent else NexaCardSurface)
                                    .border(
                                        1.dp,
                                        if (isSelected) NexaCyanAccent else NexaCardBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        onUpdateForm(null, null, model, null, null, null, null, null, null)
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = model,
                                    color = if (isSelected) NexaMidnight else NexaChrome,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4. Preferred Date
                    Text(
                        text = "Preferred Test Drive Date *",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(dateOptions) { opt ->
                            val isSelected = formState.preferredDate == opt
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaElectricBlue else NexaCardSurface)
                                    .border(
                                        1.dp,
                                        if (isSelected) NexaCyanAccent else NexaCardBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        onUpdateForm(null, null, null, opt, null, null, null, null, null)
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = opt,
                                    color = NexaChrome,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 5. Preferred Time Slot
                    Text(
                        text = "Time Slot",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(timeOptions) { slot ->
                            val isSelected = formState.timeSlot == slot
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaCyanAccent else NexaCardSurface)
                                    .border(
                                        1.dp,
                                        if (isSelected) NexaCyanAccent else NexaCardBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        onUpdateForm(null, null, null, null, slot, null, null, null, null)
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = slot,
                                    color = if (isSelected) NexaMidnight else NexaSilver,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 6. Test Drive Location (Showroom vs Doorstep)
                    Text(
                        text = "Test Drive Location Preference",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        locationOptions.forEach { loc ->
                            val isSelected = formState.locationPreference == loc
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) NexaCardSurface else NexaDarkBlue)
                                    .border(
                                        1.dp,
                                        if (isSelected) NexaCyanAccent else NexaCardBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable {
                                        onUpdateForm(null, null, null, null, null, null, loc, null, null)
                                    }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = loc,
                                    color = if (isSelected) NexaCyanAccent else NexaSilver,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 7. City / Location in Bihar
                    Text(
                        text = "Your City / Town (Bihar) *",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(cities) { cityOption ->
                            val isSelected = formState.city.equals(cityOption, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaCyanAccent else NexaCardSurface)
                                    .border(
                                        1.dp,
                                        if (isSelected) NexaCyanAccent else NexaCardBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        onUpdateForm(null, null, null, null, null, cityOption, null, null, null)
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = cityOption,
                                    color = if (isSelected) NexaMidnight else NexaSilver,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 8. Message / Specific Requirements
                    Text(
                        text = "Special Request / Message (Optional)",
                        color = NexaChrome,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = formState.message,
                        onValueChange = { onUpdateForm(null, null, null, null, null, null, null, it, null) },
                        placeholder = {
                            Text(
                                "e.g. Please bring Grand Vitara Hybrid Alpha+ for test drive",
                                color = NexaMutedText,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Message, contentDescription = null, tint = NexaSilver)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = formFieldColors(),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Submit Button
                    Button(
                        onClick = onSubmit,
                        enabled = !formState.isSubmitting,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_lead_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaCyanAccent,
                            contentColor = NexaMidnight
                        )
                    ) {
                        if (formState.isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = NexaMidnight,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Confirm & Book Test Drive",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "🔒 Your data is safe. Showroom Relationship Manager Rahul Kumar will contact you directly.",
                        color = NexaMutedText,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Direct Contact Card
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                RelationshipManagerCallCard(
                    customMessage = "Hi Rahul Ji! I am ready for a test drive and would like to coordinate timing at Pearl Cars Sasaram."
                )
            }
        }
    }
}

@Composable
private fun formFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = NexaCardSurface,
    unfocusedContainerColor = NexaCardSurface,
    focusedBorderColor = NexaCyanAccent,
    unfocusedBorderColor = NexaCardBorder,
    focusedTextColor = NexaChrome,
    unfocusedTextColor = NexaChrome
)

package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Car
import com.example.data.model.Lead
import com.example.data.model.Offer
import com.example.data.model.ShowroomConfig
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
import com.example.ui.viewmodel.Screen
import com.example.util.ShowroomActions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminDashboardScreen(
    isAuthenticated: Boolean,
    cars: List<Car>,
    offers: List<Offer>,
    leads: List<Lead>,
    config: ShowroomConfig,
    onAuthenticate: (String) -> Boolean,
    onLogout: () -> Unit,
    onUpdateLeadStatus: (Lead, String) -> Unit,
    onDeleteLead: (Lead) -> Unit,
    onUpdateCarPrice: (Car, String, Double) -> Unit,
    onAddOffer: (Offer) -> Unit,
    onDeleteOffer: (Int) -> Unit,
    onUpdateConfig: (ShowroomConfig) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var passcode by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    if (!isAuthenticated) {
        // Admin Login Dialog / Screen
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(NexaMidnight)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NexaNavy),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(listOf(NexaCyanAccent, NexaCardBorder))
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(NexaCyanAccent.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Admin Passcode",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Showroom Staff Portal",
                        color = NexaChrome,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Pearl Cars NEXA • Sasaram, Bihar",
                        color = NexaSilver,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (loginError) {
                        Text(
                            text = "Incorrect passcode. Hint: Use 9031",
                            color = NexaGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    OutlinedTextField(
                        value = passcode,
                        onValueChange = {
                            passcode = it
                            loginError = false
                        },
                        placeholder = { Text("Enter Passcode (e.g. 9031)", color = NexaMutedText, fontSize = 13.sp) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_passcode_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NexaCardSurface,
                            unfocusedContainerColor = NexaCardSurface,
                            focusedBorderColor = NexaCyanAccent,
                            unfocusedBorderColor = NexaCardBorder,
                            focusedTextColor = NexaChrome,
                            unfocusedTextColor = NexaChrome
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            if (!onAuthenticate(passcode)) {
                                loginError = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("admin_login_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaCyanAccent,
                            contentColor = NexaMidnight
                        )
                    ) {
                        Text("Unlock Admin Portal", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Staff default passcode: 9031",
                        color = NexaMutedText,
                        fontSize = 11.sp
                    )
                }
            }
        }
        return
    }

    // Authenticated Admin Dashboard with Tabs
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Leads (${leads.size})", "Cars (${cars.size})", "Offers (${offers.size})", "Showroom Info")

    // State for price edit dialog
    var carToEdit by remember { mutableStateOf<Car?>(null) }
    var editPriceDisplay by remember { mutableStateOf("") }

    // State for new offer dialog
    var showAddOfferDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NexaMidnight),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Admin Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MANAGEMENT CONSOLE",
                            color = NexaCyanAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Pearl Cars Sasaram Admin",
                            color = NexaChrome,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    OutlinedButton(
                        onClick = onLogout,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = NexaGold),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(NexaGold, NexaGold)))
                    ) {
                        Text("Lock Panel", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tab Row
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = NexaNavy,
                    contentColor = NexaCyanAccent,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = NexaCyanAccent
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selectedTab == index) NexaCyanAccent else NexaMutedText
                                )
                            }
                        )
                    }
                }
            }
        }

        // TAB 0: Leads & Test Drive Enquiries
        if (selectedTab == 0) {
            if (leads.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("No customer enquiries yet", color = NexaSilver, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text("Bookings from the Test Drive tab will appear here live.", color = NexaMutedText, fontSize = 12.sp)
                        }
                    }
                }
            } else {
                items(leads) { lead ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        LeadAdminCard(
                            lead = lead,
                            onUpdateStatus = { newStatus -> onUpdateLeadStatus(lead, newStatus) },
                            onDelete = { onDeleteLead(lead) }
                        )
                    }
                }
            }
        }

        // TAB 1: Manage Cars
        if (selectedTab == 1) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "Tap any car to update its starting price or specs",
                        color = NexaMutedText,
                        fontSize = 12.sp
                    )
                }
            }

            items(cars) { car ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = NexaNavy),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCardSurface)))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = car.modelName,
                                    color = NexaChrome,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = car.tagline,
                                    color = NexaSilver,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "Price: ${car.priceDisplay}",
                                    color = NexaGold,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Button(
                                onClick = {
                                    carToEdit = car
                                    editPriceDisplay = car.priceDisplay
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = NexaCardSurface,
                                    contentColor = NexaCyanAccent
                                )
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Price", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Edit Price", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // TAB 2: Manage Offers
        if (selectedTab == 2) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Button(
                        onClick = { showAddOfferDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaGold,
                            contentColor = NexaMidnight
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add New Showroom Offer", fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }
                }
            }

            items(offers) { offer ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = NexaNavy),
                        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCardSurface)))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = offer.title, color = NexaChrome, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${offer.category} • ${offer.discountAmount}", color = NexaGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Valid: ${offer.validUntil}", color = NexaMutedText, fontSize = 11.sp)
                            }

                            IconButton(onClick = { onDeleteOffer(offer.id) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Offer", tint = NexaSilver)
                            }
                        }
                    }
                }
            }
        }

        // TAB 3: Showroom Settings
        if (selectedTab == 3) {
            item {
                Box(modifier = Modifier.padding(16.dp)) {
                    ShowroomSettingsEditor(config = config, onSave = onUpdateConfig)
                }
            }
        }
    }

    // Edit Car Price Dialog
    if (carToEdit != null) {
        val car = carToEdit!!
        AlertDialog(
            onDismissRequest = { carToEdit = null },
            containerColor = NexaDarkBlue,
            title = { Text("Update Price for ${car.modelName}", color = NexaChrome) },
            text = {
                Column {
                    Text("Enter new price display text:", color = NexaSilver, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editPriceDisplay,
                        onValueChange = { editPriceDisplay = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NexaCardSurface,
                            unfocusedContainerColor = NexaCardSurface,
                            focusedTextColor = NexaChrome,
                            unfocusedTextColor = NexaChrome
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateCarPrice(car, editPriceDisplay, car.startingPrice)
                        carToEdit = null
                        Toast.makeText(context, "Price updated successfully!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NexaCyanAccent, contentColor = NexaMidnight)
                ) {
                    Text("Save Price")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { carToEdit = null }) {
                    Text("Cancel", color = NexaSilver)
                }
            }
        )
    }

    // Add Offer Dialog
    if (showAddOfferDialog) {
        AddOfferDialog(
            onDismiss = { showAddOfferDialog = false },
            onConfirm = { newOffer ->
                onAddOffer(newOffer)
                showAddOfferDialog = false
                Toast.makeText(context, "New offer added!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
private fun LeadAdminCard(
    lead: Lead,
    onUpdateStatus: (String) -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val dateStr = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(lead.createdAt))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NexaNavy),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCardSurface)))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = lead.customerName,
                        color = NexaChrome,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Phone: ${lead.phone} • ${lead.city}",
                        color = NexaSilver,
                        fontSize = 12.sp
                    )
                }

                // Status Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when (lead.status) {
                                "CONTACTED" -> NexaElectricBlue
                                "COMPLETED" -> NexaCyanAccent
                                else -> NexaGold
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = lead.status,
                        color = NexaMidnight,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Car: ${lead.interestedCar} | Preferred Date: ${lead.preferredDate} (${lead.timeSlot})",
                color = NexaChrome,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Location: ${lead.locationPreference} • Type: ${lead.enquiryType}",
                color = NexaMutedText,
                fontSize = 11.sp
            )

            if (lead.message.isNotBlank()) {
                Text(
                    text = "Message: \"${lead.message}\"",
                    color = NexaSilver,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Row: Direct Call, WhatsApp, Status buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { ShowroomActions.dialPhone(context, lead.phone) },
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CallBlue, contentColor = NexaChrome)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Call", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        ShowroomActions.openWhatsApp(
                            context,
                            lead.phone,
                            "Namaste ${lead.customerName}! This is Rahul Kumar from Pearl Cars NEXA Sasaram regarding your ${lead.interestedCar} enquiry."
                        )
                    },
                    modifier = Modifier
                        .weight(1.2f)
                        .height(38.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = NexaMidnight)
                ) {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Black)
                }

                // Status Toggle Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(NexaCardSurface)
                        .clickable {
                            val next = when (lead.status) {
                                "NEW" -> "CONTACTED"
                                "CONTACTED" -> "COMPLETED"
                                else -> "NEW"
                            }
                            onUpdateStatus(next)
                        }
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    Text(text = "Status ↻", color = NexaCyanAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = NexaSilver, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun ShowroomSettingsEditor(
    config: ShowroomConfig,
    onSave: (ShowroomConfig) -> Unit
) {
    val context = LocalContext.current
    var rmName by remember { mutableStateOf(config.rmName) }
    var rmPhone by remember { mutableStateOf(config.rmPhone) }
    var address by remember { mutableStateOf(config.address) }
    var email by remember { mutableStateOf(config.email) }
    var hours by remember { mutableStateOf(config.workingHours) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NexaNavy),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCardSurface)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "SHOWROOM CONTACT CONFIGURATION", color = NexaCyanAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(14.dp))

            AdminField(label = "Relationship Manager Name", value = rmName, onValueChange = { rmName = it })
            Spacer(modifier = Modifier.height(10.dp))
            AdminField(label = "Phone / WhatsApp Number", value = rmPhone, onValueChange = { rmPhone = it })
            Spacer(modifier = Modifier.height(10.dp))
            AdminField(label = "Showroom Address", value = address, onValueChange = { address = it })
            Spacer(modifier = Modifier.height(10.dp))
            AdminField(label = "Email Address", value = email, onValueChange = { email = it })
            Spacer(modifier = Modifier.height(10.dp))
            AdminField(label = "Working Hours", value = hours, onValueChange = { hours = it })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updated = config.copy(
                        rmName = rmName.trim(),
                        rmPhone = rmPhone.trim(),
                        whatsappNumber = rmPhone.trim(),
                        address = address.trim(),
                        email = email.trim(),
                        workingHours = hours.trim()
                    )
                    onSave(updated)
                    Toast.makeText(context, "Showroom details saved!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NexaCyanAccent, contentColor = NexaMidnight)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Changes", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AdminField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, color = NexaSilver, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = NexaCardSurface,
                unfocusedContainerColor = NexaCardSurface,
                focusedBorderColor = NexaCyanAccent,
                unfocusedBorderColor = NexaCardBorder,
                focusedTextColor = NexaChrome,
                unfocusedTextColor = NexaChrome
            ),
            singleLine = true
        )
    }
}

@Composable
private fun AddOfferDialog(
    onDismiss: () -> Unit,
    onConfirm: (Offer) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Festival Offer") }
    var validUntil by remember { mutableStateOf("30th November 2026") }
    var applicableModels by remember { mutableStateOf("All NEXA Models") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = NexaDarkBlue,
        title = { Text("Add Showroom Offer", color = NexaChrome) },
        text = {
            Column {
                AdminField("Offer Title", title) { title = it }
                Spacer(modifier = Modifier.height(6.dp))
                AdminField("Discount Amount (e.g. ₹50,000 Off)", discount) { discount = it }
                Spacer(modifier = Modifier.height(6.dp))
                AdminField("Category (Festival/Cash/Exchange)", category) { category = it }
                Spacer(modifier = Modifier.height(6.dp))
                AdminField("Applicable Models", applicableModels) { applicableModels = it }
                Spacer(modifier = Modifier.height(6.dp))
                AdminField("Validity Date", validUntil) { validUntil = it }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && discount.isNotBlank()) {
                        val newOffer = Offer(
                            title = title.trim(),
                            subtitle = subtitle.ifBlank { "Special Showroom Offer in Sasaram" },
                            discountAmount = discount.trim(),
                            category = category.trim(),
                            validUntil = validUntil.trim(),
                            description = description.ifBlank { "Special discount package on purchase at Pearl Cars NEXA Sasaram." },
                            applicableModels = applicableModels.trim(),
                            badgeText = "SPECIAL OFFER",
                            isFeatured = true
                        )
                        onConfirm(newOffer)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NexaGold, contentColor = NexaMidnight)
            ) {
                Text("Add Offer")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel", color = NexaSilver)
            }
        }
    )
}

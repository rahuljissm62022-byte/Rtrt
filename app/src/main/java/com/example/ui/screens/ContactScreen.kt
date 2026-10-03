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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ShowroomConfig
import com.example.ui.components.RelationshipManagerCallCard
import com.example.ui.theme.CallBlue
import com.example.ui.theme.NexaCardBorder
import com.example.ui.theme.NexaCardSurface
import com.example.ui.theme.NexaChrome
import com.example.ui.theme.NexaCyanAccent
import com.example.ui.theme.NexaElectricBlue
import com.example.ui.theme.NexaGold
import com.example.ui.theme.NexaMidnight
import com.example.ui.theme.NexaMutedText
import com.example.ui.theme.NexaNavy
import com.example.ui.theme.NexaSilver
import com.example.ui.theme.WhatsAppGreen
import com.example.util.ShowroomActions

@Composable
fun ContactScreen(
    config: ShowroomConfig,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

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
                    text = "OFFICIAL SHOWROOM CONTACT",
                    color = NexaCyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Pearl Cars NEXA",
                    color = NexaChrome,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Sasaram, Bihar • Authorised NEXA Dealership",
                    color = NexaSilver,
                    fontSize = 13.sp
                )
            }
        }

        // Relationship Manager Rahul Kumar Spotlight Card
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                RelationshipManagerCallCard(
                    customMessage = "Hello Rahul Ji! I am looking for NEXA car information from Pearl Cars Sasaram."
                )
            }
        }

        // Contact Info Details
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NexaNavy),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCardSurface)))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SHOWROOM DETAILS",
                        color = NexaCyanAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    ContactRow(
                        icon = Icons.Default.LocationOn,
                        title = "Showroom Address",
                        value = config.address,
                        actionLabel = "Map",
                        onAction = { ShowroomActions.openShowroomMap(context, config.mapsQuery) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ContactRow(
                        icon = Icons.Default.Phone,
                        title = "Relationship Manager Phone",
                        value = "+91 ${config.rmPhone} (Rahul Kumar)",
                        actionLabel = "Call",
                        onAction = { ShowroomActions.dialPhone(context, config.rmPhone) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ContactRow(
                        icon = Icons.Default.Chat,
                        title = "WhatsApp Marketing & Support",
                        value = "+91 ${config.rmPhone}",
                        actionLabel = "Chat",
                        onAction = {
                            ShowroomActions.openWhatsApp(
                                context,
                                config.rmPhone,
                                "Hello Rahul Ji! Inquiring from Pearl Cars NEXA Sasaram App."
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ContactRow(
                        icon = Icons.Default.Email,
                        title = "Official Email",
                        value = config.email,
                        actionLabel = null,
                        onAction = null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ContactRow(
                        icon = Icons.Default.AccessTime,
                        title = "Showroom Working Hours",
                        value = config.workingHours,
                        actionLabel = null,
                        onAction = null
                    )
                }
            }
        }

        // Directions on Google Maps CTA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NexaNavy),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(listOf(NexaCyanAccent.copy(alpha = 0.5f), NexaCardBorder))
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "VISIT US IN SASARAM",
                        color = NexaGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Conveniently located on GT Road / Sasaram Bypass with spacious test-drive arena & customer lounge.",
                        color = NexaSilver,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { ShowroomActions.openShowroomMap(context, config.mapsQuery) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaCyanAccent,
                            contentColor = NexaMidnight
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Navigation,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Open Directions in Google Maps",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Customer Rating & Service Assurance
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NexaCardSurface)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = NexaGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = config.rating,
                            color = NexaChrome,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Top-rated NEXA dealership experience in South Bihar",
                            color = NexaSilver,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    actionLabel: String?,
    onAction: (() -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(NexaCardSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = NexaCyanAccent,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = NexaMutedText,
                fontSize = 11.sp
            )
            Text(
                text = value,
                color = NexaChrome,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (actionLabel != null && onAction != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(NexaCyanAccent.copy(alpha = 0.2f))
                    .clickable { onAction() }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = actionLabel,
                    color = NexaCyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

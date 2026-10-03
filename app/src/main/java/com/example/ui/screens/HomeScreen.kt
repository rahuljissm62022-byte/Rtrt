package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Car
import com.example.data.model.Offer
import com.example.ui.components.CarCard
import com.example.ui.components.OfferCard
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
import com.example.ui.viewmodel.Screen
import com.example.util.ShowroomActions

@Composable
fun HomeScreen(
    cars: List<Car>,
    offers: List<Offer>,
    onNavigate: (Screen) -> Unit,
    onBookTestDrive: (Car) -> Unit,
    onGetBestPrice: (Car) -> Unit,
    onViewCarDetails: (Car) -> Unit,
    onClaimOffer: (Offer) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NexaMidnight),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // 1. Large Car Promotional Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_nexa_hero),
                    contentDescription = "Pearl Cars NEXA Showroom Sasaram",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dark Navy & Black Gradient Overlay for high-end automotive look
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    NexaMidnight.copy(alpha = 0.5f),
                                    NexaMidnight
                                ),
                                startY = 0f,
                                endY = 700f
                            )
                        )
                )

                // Banner Content Overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NexaGold)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "CHHATH & FESTIVAL SPECIAL OFFERS",
                            color = NexaMidnight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "PEARL CARS NEXA",
                        color = NexaChrome,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Sasaram, Bihar • Drive Luxury Today",
                        color = NexaSilver,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Save Up to ₹ 85,000* on Grand Vitara & Fronx",
                        color = NexaCyanAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 2. Direct Call & WhatsApp Buttons Row
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                ShowroomCtaRow(
                    customMessage = "Hello Rahul Ji! I am exploring NEXA cars at Pearl Cars Sasaram and would like to know the best price and offers."
                )
            }
        }

        // 3. Quick Action Buttons Grid (The exact 4 buttons requested by user + ad creator)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(
                    text = "QUICK ACTIONS",
                    color = NexaCyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // “Book Test Drive” button
                    QuickActionButton(
                        title = "Book Test Drive",
                        subtitle = "At Showroom or Home",
                        icon = Icons.Default.Speed,
                        accentColor = NexaCyanAccent,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.TEST_DRIVE) }
                    )

                    // “Get Best Price” button
                    QuickActionButton(
                        title = "Get Best Price",
                        subtitle = "Instant Quotation",
                        icon = Icons.Default.PriceCheck,
                        accentColor = NexaGold,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val featuredCar = cars.firstOrNull()
                            if (featuredCar != null) onGetBestPrice(featuredCar)
                            else onNavigate(Screen.CARS)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // “View Cars” button
                    QuickActionButton(
                        title = "View Cars",
                        subtitle = "Explore Full Lineup",
                        icon = Icons.Default.DirectionsCar,
                        accentColor = NexaElectricBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.CARS) }
                    )

                    // “Contact Rahul Kumar” button
                    QuickActionButton(
                        title = "Contact Rahul Kumar",
                        subtitle = "Relationship Manager",
                        icon = Icons.Default.Phone,
                        accentColor = WhatsAppGreen,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.CONTACT) }
                    )
                }
            }
        }

        // 4. Relationship Manager Card (Rahul Kumar, 9031849243)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                RelationshipManagerCallCard()
            }
        }

        // 5. “Latest Offers” Section with WhatsApp Share
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LATEST OFFERS",
                            color = NexaGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Festive & Cash Discounts",
                            color = NexaChrome,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "View All →",
                        color = NexaCyanAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigate(Screen.OFFERS) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(offers.take(4)) { offer ->
                        Box(modifier = Modifier.width(320.dp)) {
                            OfferCard(
                                offer = offer,
                                onClaimOffer = onClaimOffer
                            )
                        }
                    }
                }
            }
        }

        // 6. Showroom Ad Poster Creator Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .clickable { onNavigate(Screen.POSTER_MAKER) },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NexaNavy),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        listOf(NexaCyanAccent.copy(alpha = 0.6f), NexaGold.copy(alpha = 0.5f))
                    )
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NexaCyanAccent.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Ad Poster Maker",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Advertisement Creator",
                                color = NexaChrome,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(NexaGold)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "STAFF TOOL",
                                    color = NexaMidnight,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                        Text(
                            text = "Design NEXA ad posters & share to WhatsApp & Facebook",
                            color = NexaSilver,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // 7. Featured NEXA Cars Showcase
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "POPULAR IN SASARAM",
                            color = NexaCyanAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Featured NEXA Cars",
                            color = NexaChrome,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "All Cars →",
                        color = NexaCyanAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigate(Screen.CARS) }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        items(cars.take(3)) { car ->
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

        // 8. Pearl Cars Showroom Advantages
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "WHY PEARL CARS SASARAM",
                    color = NexaCyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "The NEXA Experience in Rohtas",
                    color = NexaChrome,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = NexaNavy),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCardSurface)))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ShowroomAdvantageRow(
                            icon = Icons.Default.Speed,
                            title = "Doorstep Test Drive in Sasaram & Nearby",
                            desc = "We bring your desired NEXA car to your home or office."
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        ShowroomAdvantageRow(
                            icon = Icons.Default.PriceCheck,
                            title = "Instant Loan Clearance & Zero Down Payment",
                            desc = "Showroom tie-up with SBI, HDFC, ICICI, BoB & Gramin banks."
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        ShowroomAdvantageRow(
                            icon = Icons.Default.EmojiEvents,
                            title = "Highest Old Car Valuation + Exchange Bonus",
                            desc = "Instant transparent evaluation for any old vehicle."
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        ShowroomAdvantageRow(
                            icon = Icons.Default.Security,
                            title = "100% Genuine NEXA Accessories & Warranty",
                            desc = "Authorized service station and warranty support."
                        )
                    }
                }
            }
        }

        // 9. Showroom Visit & Map Directions Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = NexaNavy),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCyanAccent.copy(alpha = 0.3f))))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Showroom Location",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Visit Pearl Cars NEXA Showroom",
                            color = NexaChrome,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "GT Road, Sasaram Bypass, Sasaram, Bihar - 821115",
                        color = NexaSilver,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Open Today: 9:30 AM - 7:30 PM (Mon - Sun)",
                        color = NexaMutedText,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            ShowroomActions.openShowroomMap(context, "Pearl Cars Nexa Sasaram Bihar")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaCardSurface,
                            contentColor = NexaChrome
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = Brush.horizontalGradient(listOf(NexaCyanAccent, NexaElectricBlue))
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Navigation,
                            contentDescription = "Get Directions",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Get Directions on Google Maps",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = NexaNavy),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.horizontalGradient(listOf(NexaCardBorder, accentColor.copy(alpha = 0.3f)))
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = accentColor,
                modifier = Modifier.size(24.dp)
            )

            Column {
                Text(
                    text = title,
                    color = NexaChrome,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    color = NexaSilver,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun ShowroomAdvantageRow(icon: ImageVector, title: String, desc: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(NexaCardSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NexaCyanAccent,
                modifier = Modifier.size(17.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                color = NexaChrome,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = desc,
                color = NexaSilver,
                fontSize = 11.sp
            )
        }
    }
}

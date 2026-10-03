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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.LocalOffer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Offer
import com.example.ui.components.OfferCard
import com.example.ui.components.ShowroomCtaRow
import com.example.ui.theme.NexaCardBorder
import com.example.ui.theme.NexaCardSurface
import com.example.ui.theme.NexaChrome
import com.example.ui.theme.NexaCyanAccent
import com.example.ui.theme.NexaGold
import com.example.ui.theme.NexaMidnight
import com.example.ui.theme.NexaMutedText
import com.example.ui.theme.NexaNavy
import com.example.ui.theme.NexaSilver
import com.example.ui.viewmodel.Screen

@Composable
fun OffersScreen(
    offers: List<Offer>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onClaimOffer: (Offer) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        "ALL" to "All Offers",
        "Festival" to "Festival Offers",
        "Cash" to "Cash Discounts",
        "Exchange" to "Exchange Bonuses",
        "Finance" to "Finance Offers",
        "Accessories" to "Accessories Offers",
        "Limited" to "Limited-Period"
    )

    val displayedOffers = if (selectedCategory == "ALL") {
        offers
    } else {
        offers.filter { it.category.contains(selectedCategory, ignoreCase = true) }
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
                    text = "EXCLUSIVES & SAVINGS",
                    color = NexaGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Pearl Cars Showroom Offers",
                    color = NexaChrome,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Special promotions verified by Relationship Manager Rahul Kumar",
                    color = NexaSilver,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Category Tabs
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { (key, label) ->
                        val isSelected = selectedCategory.equals(key, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) NexaGold else NexaNavy)
                                .border(
                                    1.dp,
                                    if (isSelected) NexaGold else NexaCardBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onCategorySelected(key) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = label,
                                color = if (isSelected) NexaMidnight else NexaChrome,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Offers list
        items(displayedOffers) { offer ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OfferCard(
                    offer = offer,
                    onClaimOffer = onClaimOffer
                )
            }
        }

        // Showroom Staff Poster Maker Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .clickable { onNavigate(Screen.POSTER_MAKER) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NexaCardSurface),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        listOf(NexaCyanAccent.copy(alpha = 0.5f), NexaGold.copy(alpha = 0.5f))
                    )
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = "Poster Maker",
                        tint = NexaGold,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Create Advertising Poster",
                            color = NexaChrome,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Generate professional WhatsApp & Social Media promo graphics",
                            color = NexaSilver,
                            fontSize = 11.sp
                        )
                    }
                    Button(
                        onClick = { onNavigate(Screen.POSTER_MAKER) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaCyanAccent,
                            contentColor = NexaMidnight
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Create", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }

        // Contact Section
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                ShowroomCtaRow(
                    customMessage = "Hello Rahul Ji! I would like to inquire about the active festival and exchange offers at Pearl Cars NEXA Sasaram."
                )
            }
        }
    }
}

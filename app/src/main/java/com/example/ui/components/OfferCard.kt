package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Offer
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
import com.example.util.ShowroomActions

@Composable
fun OfferCard(
    offer: Offer,
    onClaimOffer: (Offer) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val shareText = """
🔥 *EXCLUSIVE NEXA OFFER - PEARL CARS SASARAM* 🔥

🚗 *${offer.title.uppercase()}*
💰 *Benefits: ${offer.discountAmount}*
📅 Valid Till: ${offer.validUntil}
🚘 Applicable Cars: ${offer.applicableModels}

📝 *Details:*
${offer.description}

📍 *Visit Showroom:*
Pearl Cars NEXA, GT Road, Sasaram, Bihar - 821115

👤 *Relationship Manager:*
Rahul Kumar
📞 Call / WhatsApp: +91 9031849243

*Book your test drive today for best festive savings!*
    """.trimIndent()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = NexaNavy),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                listOf(NexaGold.copy(alpha = 0.5f), NexaCardBorder, NexaCyanAccent.copy(alpha = 0.3f))
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Category & Badge Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(NexaElectricBlue.copy(alpha = 0.3f))
                        .border(1.dp, NexaCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = offer.category.uppercase(),
                        color = NexaCyanAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(NexaGold)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = offer.badgeText,
                        color = NexaMidnight,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title & Subtitle
            Text(
                text = offer.title,
                color = NexaChrome,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = offer.subtitle,
                color = NexaSilver,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Discount Amount Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(NexaCardSurface, NexaDarkBlue)
                        )
                    )
                    .border(1.dp, NexaCardBorder, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TOTAL SAVINGS",
                            color = NexaMutedText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = offer.discountAmount,
                            color = NexaGold,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.LocalOffer,
                        contentDescription = "Offer Tag",
                        tint = NexaGold,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Description
            Text(
                text = offer.description,
                color = NexaSilver,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Applicable Models
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "Applicable Models",
                    tint = NexaCyanAccent,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Cars: ${offer.applicableModels}",
                    color = NexaMutedText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Validity Date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Validity",
                    tint = NexaSilver,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Valid Until: ${offer.validUntil}",
                    color = NexaMutedText,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Dual Buttons: "WhatsApp Share" + "Claim Offer"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // WhatsApp Share Button
                Button(
                    onClick = {
                        ShowroomActions.shareToWhatsApp(context, shareText)
                    },
                    modifier = Modifier
                        .weight(1.1f)
                        .height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = NexaMidnight
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "WhatsApp Share",
                        tint = NexaMidnight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WhatsApp Share",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Claim Offer Button
                Button(
                    onClick = { onClaimOffer(offer) },
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NexaCyanAccent,
                        contentColor = NexaMidnight
                    )
                ) {
                    Text(
                        text = "Claim Offer",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

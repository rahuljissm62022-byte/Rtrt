package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Speed
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Car
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CarCard(
    car: Car,
    onBookTestDrive: (Car) -> Unit,
    onGetBestPrice: (Car) -> Unit,
    onViewDetails: (Car) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageResId = when (car.drawableName) {
        "img_car_invicto" -> R.drawable.img_car_invicto
        "img_car_fronx" -> R.drawable.img_car_fronx
        "img_car_jimny" -> R.drawable.img_car_jimny
        "img_nexa_hero" -> R.drawable.img_nexa_hero
        else -> R.drawable.img_nexa_hero
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onViewDetails(car) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = NexaNavy),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.verticalGradient(
                listOf(NexaCardBorder, NexaCardSurface)
            )
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Car Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(NexaMidnight)
            ) {
                if (car.customImageUri != null) {
                    AsyncImage(
                        model = car.customImageUri,
                        contentDescription = car.modelName,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = car.modelName,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Top Badge: Segment & Featured
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NexaMidnight.copy(alpha = 0.85f))
                            .border(1.dp, NexaCardBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = car.segment.uppercase(),
                            color = NexaCyanAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NexaGold.copy(alpha = 0.9f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "OFFICIAL SHOWROOM",
                            color = NexaMidnight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Price Tag Overlay at bottom
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(NexaDarkBlue.copy(alpha = 0.92f))
                        .border(1.dp, NexaCyanAccent.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Column {
                        Text(
                            text = "STARTING FROM",
                            color = NexaMutedText,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = car.priceDisplay,
                            color = NexaChrome,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            // Info Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Model Name & Tagline
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = car.modelName,
                            color = NexaChrome,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = car.tagline,
                            color = NexaSilver,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Key Specs Row (Mileage & Engine)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(NexaCardSurface)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalGasStation,
                            contentDescription = "Mileage",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = car.mileage.split("/").firstOrNull()?.trim() ?: car.mileage,
                            color = NexaChrome,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = "Transmission",
                            tint = NexaGold,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = car.seating,
                            color = NexaSilver,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Variants summary
                Text(
                    text = "Variants: " + car.getVariantsList().take(4).joinToString(" • "),
                    color = NexaMutedText,
                    fontSize = 11.sp,
                    maxLines = 1
                )

                // Colours preview
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Colours: ",
                        color = NexaMutedText,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = car.getColoursList().take(3).joinToString(", ") + " +more",
                        color = NexaSilver,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action Buttons: "Get Best Price" & "Book Test Drive"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onGetBestPrice(car) },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = Brush.horizontalGradient(listOf(NexaCyanAccent, NexaElectricBlue))
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = NexaCyanAccent
                        )
                    ) {
                        Text(
                            text = "Get Best Price",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { onBookTestDrive(car) },
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
                            text = "Book Test Drive",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}

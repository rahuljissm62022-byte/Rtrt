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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.ui.theme.WhatsAppGreen
import com.example.util.ShowroomActions
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CarDetailSheet(
    car: Car,
    onDismiss: () -> Unit,
    onBookTestDrive: (Car) -> Unit,
    onGetBestPrice: (Car) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    val imageResId = when (car.drawableName) {
        "img_car_invicto" -> R.drawable.img_car_invicto
        "img_car_fronx" -> R.drawable.img_car_fronx
        "img_car_jimny" -> R.drawable.img_car_jimny
        "img_nexa_hero" -> R.drawable.img_nexa_hero
        else -> R.drawable.img_nexa_hero
    }

    // EMI Calculator state
    var loanTenureYears by remember { mutableFloatStateOf(5f) }
    val startingPriceLakh = car.startingPrice
    val defaultDownPayment = startingPriceLakh * 0.20 // 20% down payment
    val loanAmount = (startingPriceLakh - defaultDownPayment) * 100000 // In INR
    val annualRate = 0.085 // 8.5%
    val monthlyRate = annualRate / 12
    val months = (loanTenureYears * 12).toInt()
    val emi = if (months > 0) {
        (loanAmount * monthlyRate * (1 + monthlyRate).pow(months)) / ((1 + monthlyRate).pow(months) - 1)
    } else 0.0

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = NexaDarkBlue,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            // Header Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(NexaMidnight)
            ) {
                if (car.customImageUri != null) {
                    AsyncImage(
                        model = car.customImageUri,
                        contentDescription = car.modelName,
                        modifier = Modifier.fillMaxWidth().height(230.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = car.modelName,
                        modifier = Modifier.fillMaxWidth().height(230.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Close Button
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(NexaMidnight.copy(alpha = 0.8f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = NexaChrome
                    )
                }

                // Showroom Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(NexaMidnight.copy(alpha = 0.85f))
                        .border(1.dp, NexaCardBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "PEARL CARS NEXA SASARAM",
                        color = NexaCyanAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Name & Price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = car.modelName,
                            color = NexaChrome,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = car.tagline,
                            color = NexaSilver,
                            fontSize = 13.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Ex-Showroom",
                            color = NexaMutedText,
                            fontSize = 10.sp
                        )
                        Text(
                            text = car.priceDisplay,
                            color = NexaGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Technical Specifications Grid
                Text(
                    text = "SPECIFICATIONS",
                    color = NexaCyanAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = NexaNavy),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCardSurface)))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        SpecRow(title = "Engine", value = car.engine)
                        SpecRow(title = "Fuel Efficiency", value = car.mileage)
                        SpecRow(title = "Transmission", value = car.transmission)
                        SpecRow(title = "Seating Capacity", value = car.seating)
                        SpecRow(title = "Segment", value = car.segment)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Key Features Checklist
                Text(
                    text = "KEY FEATURES & HIGHLIGHTS",
                    color = NexaCyanAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NexaNavy)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    car.getFeaturesList().forEach { feature ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Feature",
                                tint = NexaCyanAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = feature,
                                color = NexaChrome,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Variants
                Text(
                    text = "AVAILABLE VARIANTS",
                    color = NexaCyanAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    car.getVariantsList().forEach { variant ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(NexaCardSurface)
                                .border(1.dp, NexaCardBorder, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = variant,
                                color = NexaChrome,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // EMI Calculator Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = NexaCardSurface),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCyanAccent.copy(alpha = 0.5f), NexaCardBorder)))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Calculate,
                                    contentDescription = "EMI Calculator",
                                    tint = NexaCyanAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Estimated EMI Calculator",
                                    color = NexaChrome,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "ROI @ 8.5%*",
                                color = NexaGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Tenure: ${loanTenureYears.toInt()} Years (${(loanTenureYears * 12).toInt()} Months)",
                                color = NexaSilver,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "₹ ${emi.toInt()} / month",
                                color = NexaCyanAccent,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Slider(
                            value = loanTenureYears,
                            onValueChange = { loanTenureYears = it },
                            valueRange = 1f..7f,
                            steps = 5,
                            colors = SliderDefaults.colors(
                                thumbColor = NexaCyanAccent,
                                activeTrackColor = NexaCyanAccent,
                                inactiveTrackColor = NexaCardBorder
                            )
                        )

                        Text(
                            text = "*Assuming 20% down payment. Final EMI subject to bank terms at Sasaram showroom.",
                            color = NexaMutedText,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary CTAs
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                            onBookTestDrive(car)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaCyanAccent,
                            contentColor = NexaMidnight
                        )
                    ) {
                        Text(
                            text = "Book Test Drive for ${car.modelName}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                onDismiss()
                                onGetBestPrice(car)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = NexaChrome
                            )
                        ) {
                            Text(
                                text = "Get Best Price",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                ShowroomActions.openWhatsApp(
                                    context,
                                    "9031849243",
                                    "Hello Rahul Ji! I am interested in knowing the best showroom price and festive offers for ${car.modelName} at Pearl Cars NEXA Sasaram."
                                )
                            },
                            modifier = Modifier
                                .weight(1.1f)
                                .height(46.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WhatsAppGreen,
                                contentColor = NexaMidnight
                            )
                        ) {
                            Text(
                                text = "WhatsApp RM",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = NexaMutedText,
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            color = NexaChrome,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.6f)
        )
    }
}

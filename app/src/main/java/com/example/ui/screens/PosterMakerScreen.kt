package com.example.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.Car
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
import com.example.util.ShowroomActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PosterMakerScreen(
    cars: List<Car>,
    config: ShowroomConfig,
    posterCarModel: String,
    posterFestivalName: String,
    posterOfferHeadline: String,
    posterStartingPrice: String,
    posterBackground: String,
    posterCustomImageUri: String?,
    onUpdateCarModel: (String) -> Unit,
    onUpdateFestival: (String) -> Unit,
    onUpdateOfferHeadline: (String) -> Unit,
    onUpdateStartingPrice: (String) -> Unit,
    onUpdateBackground: (String) -> Unit,
    onUpdateCustomImageUri: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isGeneratingImage by remember { mutableStateOf(false) }

    // Zero-permission Android Photo Picker for showroom staff to pick custom vehicle photo
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onUpdateCustomImageUri(uri.toString())
        }
    }

    val carModels = if (cars.isNotEmpty()) cars.map { it.modelName } else listOf("Grand Vitara", "Invicto", "Fronx", "Jimny", "Baleno", "XL6")
    val festivalPresets = listOf(
        "Chhath Puja Mahabachat",
        "Diwali Dhamaka Offer",
        "Navratri Mahotsav",
        "Pearl Cars Mega Carnival",
        "Year-End Clearance Bonanza"
    )
    val offerPresets = listOf(
        "SAVE UP TO ₹85,000*",
        "UP TO ₹50,000 CASH DISCOUNT",
        "GUARANTEED ₹35,000 EXCHANGE BONUS",
        "100% ON-ROAD FUNDING @ 7.99%*",
        "FREE ACCESSORIES WORTH ₹25,000*"
    )
    val bgStyles = listOf("Nexa Royal Navy", "Midnight Carbon", "Gold Festival Glow", "Cyber Slate")

    val selectedCar = cars.find { it.modelName.equals(posterCarModel, ignoreCase = true) }

    // Function to generate the poster bitmap and perform sharing
    fun generateAndSharePoster(targetPackage: String? = null) {
        isGeneratingImage = true
        coroutineScope.launch(Dispatchers.IO) {
            try {
                // Load base bitmap for car
                val carBitmap = if (posterCustomImageUri != null) {
                    try {
                        val input = context.contentResolver.openInputStream(Uri.parse(posterCustomImageUri))
                        BitmapFactory.decodeStream(input)
                    } catch (e: Exception) {
                        null
                    }
                } else {
                    val resId = when (selectedCar?.drawableName) {
                        "img_car_invicto" -> R.drawable.img_car_invicto
                        "img_car_fronx" -> R.drawable.img_car_fronx
                        "img_car_jimny" -> R.drawable.img_car_jimny
                        else -> R.drawable.img_nexa_hero
                    }
                    BitmapFactory.decodeResource(context.resources, resId)
                }

                val posterBitmap = ShowroomActions.generatePosterBitmap(
                    context = context,
                    festivalName = posterFestivalName,
                    carModel = posterCarModel,
                    offerHeadline = posterOfferHeadline,
                    startingPrice = posterStartingPrice,
                    tagline = selectedCar?.tagline ?: "Premium Automotive Experience",
                    rmName = config.rmName,
                    rmPhone = config.rmPhone,
                    showroomCity = "Sasaram, Bihar",
                    carBitmap = carBitmap
                )

                val uri = ShowroomActions.saveBitmapToCache(context, posterBitmap, "pearl_cars_${posterCarModel.lowercase()}_poster.png")

                withContext(Dispatchers.Main) {
                    isGeneratingImage = false
                    if (uri != null) {
                        val caption = """
🚗 *PEARL CARS NEXA, SASARAM* 🚗
🎉 *${posterFestivalName.uppercase()}*
⭐ *${posterCarModel.uppercase()} - ${posterOfferHeadline}*
💰 Starting at ${posterStartingPrice}

📍 Pearl Cars NEXA Showroom, GT Road, Sasaram, Bihar
📞 Call & WhatsApp Rahul Kumar: +91 ${config.rmPhone}
*Book your test drive today!*
                        """.trimIndent()

                        ShowroomActions.shareImageUri(context, uri, caption, targetPackage)
                    } else {
                        Toast.makeText(context, "Could not save poster bitmap", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isGeneratingImage = false
                    Toast.makeText(context, "Error creating poster: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = "Ad Creator",
                        tint = NexaGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ADVERTISEMENT CREATOR",
                        color = NexaGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = "NEXA Showroom Poster Maker",
                    color = NexaChrome,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Generate professional advertising posters for WhatsApp & Social Media marketing",
                    color = NexaSilver,
                    fontSize = 12.sp
                )
            }
        }

        // Live Poster Preview Canvas Card
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "LIVE POSTER PREVIEW",
                    color = NexaCyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                // The Visual NEXA Poster
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 5f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = NexaDarkBlue),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(
                            listOf(NexaCyanAccent, NexaGold, NexaElectricBlue)
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = when (posterBackground) {
                                        "Midnight Carbon" -> listOf(Color(0xFF0F172A), Color(0xFF020617))
                                        "Gold Festival Glow" -> listOf(Color(0xFF1E1B4B), Color(0xFF451A03), Color(0xFF0A192F))
                                        "Cyber Slate" -> listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                                        else -> listOf(Color(0xFF0A192F), Color(0xFF0D254C), Color(0xFF060D17))
                                    }
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Top Header: Branding
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "PEARL CARS NEXA",
                                        color = NexaChrome,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    )
                                }
                                Text(
                                    text = "SASARAM, BIHAR • AUTHORISED SHOWROOM",
                                    color = NexaCyanAccent,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                // Festival Badge
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(NexaGold)
                                        .padding(horizontal = 10.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = posterFestivalName.uppercase(),
                                        color = NexaMidnight,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }

                            // Model Title
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = posterCarModel.uppercase(),
                                    color = NexaChrome,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.5.sp
                                )
                                Text(
                                    text = selectedCar?.tagline ?: "Premium Automotive Experience",
                                    color = NexaSilver,
                                    fontSize = 11.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Car Graphic Center Area
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(NexaMidnight.copy(alpha = 0.5f))
                            ) {
                                if (posterCustomImageUri != null) {
                                    AsyncImage(
                                        model = posterCustomImageUri,
                                        contentDescription = posterCarModel,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    val resId = when (selectedCar?.drawableName) {
                                        "img_car_invicto" -> R.drawable.img_car_invicto
                                        "img_car_fronx" -> R.drawable.img_car_fronx
                                        "img_car_jimny" -> R.drawable.img_car_jimny
                                        else -> R.drawable.img_nexa_hero
                                    }
                                    Image(
                                        painter = painterResource(id = resId),
                                        contentDescription = posterCarModel,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            // Offer Amount Banner
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(NexaElectricBlue)
                                    .border(1.dp, NexaCyanAccent, RoundedCornerShape(10.dp))
                                    .padding(vertical = 8.dp, horizontal = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = posterOfferHeadline,
                                    color = NexaGold,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Text(
                                text = "Starting from ${posterStartingPrice} • Priority Delivery",
                                color = NexaChrome,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )

                            // Contact Footer
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(NexaCardSurface)
                                    .padding(vertical = 6.dp, horizontal = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "RELATIONSHIP MANAGER",
                                            color = NexaMutedText,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = config.rmName,
                                            color = NexaChrome,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = "📞 +91 ${config.rmPhone}",
                                        color = WhatsAppGreen,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Action Buttons: WhatsApp, Social, Download
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // WhatsApp Share
                    Button(
                        onClick = { generateAndSharePoster("com.whatsapp") },
                        enabled = !isGeneratingImage,
                        modifier = Modifier
                            .weight(1.2f)
                            .height(48.dp)
                            .testTag("poster_share_whatsapp_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = NexaMidnight
                        )
                    ) {
                        if (isGeneratingImage) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = NexaMidnight)
                        } else {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Share WhatsApp", fontWeight = FontWeight.Black, fontSize = 12.sp)
                        }
                    }

                    // General Share (Facebook / Instagram)
                    Button(
                        onClick = { generateAndSharePoster(null) },
                        enabled = !isGeneratingImage,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexaCyanAccent,
                            contentColor = NexaMidnight
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share Social", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Download / Save Button
                OutlinedButton(
                    onClick = {
                        generateAndSharePoster(null)
                        Toast.makeText(context, "Poster generated and ready to save/share!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = NexaChrome),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaSilver.copy(alpha = 0.5f))))
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = NexaCyanAccent, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download & Save Poster", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
            }
        }

        // Customization Controls for Staff
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
                        text = "POSTER CUSTOMIZER",
                        color = NexaGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 1. Select Car Model
                    Text("1. Select Car Model", color = NexaChrome, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(carModels) { model ->
                            val isSelected = posterCarModel.equals(model, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaCyanAccent else NexaCardSurface)
                                    .border(1.dp, if (isSelected) NexaCyanAccent else NexaCardBorder, RoundedCornerShape(8.dp))
                                    .clickable { onUpdateCarModel(model) }
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

                    // 2. Select or enter Festival Name
                    Text("2. Festival / Campaign Name", color = NexaChrome, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(festivalPresets) { fest ->
                            val isSelected = posterFestivalName == fest
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaGold else NexaCardSurface)
                                    .border(1.dp, if (isSelected) NexaGold else NexaCardBorder, RoundedCornerShape(8.dp))
                                    .clickable { onUpdateFestival(fest) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = fest,
                                    color = if (isSelected) NexaMidnight else NexaSilver,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = posterFestivalName,
                        onValueChange = { onUpdateFestival(it) },
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Select Offer Headline
                    Text("3. Offer Amount / Headline", color = NexaChrome, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(offerPresets) { offer ->
                            val isSelected = posterOfferHeadline == offer
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaElectricBlue else NexaCardSurface)
                                    .border(1.dp, if (isSelected) NexaCyanAccent else NexaCardBorder, RoundedCornerShape(8.dp))
                                    .clickable { onUpdateOfferHeadline(offer) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = offer,
                                    color = NexaChrome,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = posterOfferHeadline,
                        onValueChange = { onUpdateOfferHeadline(it) },
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4. Background Style
                    Text("4. Background Style", color = NexaChrome, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        bgStyles.forEach { style ->
                            val isSelected = posterBackground == style
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) NexaCyanAccent else NexaCardSurface)
                                    .border(1.dp, if (isSelected) NexaCyanAccent else NexaCardBorder, RoundedCornerShape(8.dp))
                                    .clickable { onUpdateBackground(style) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = style.split(" ").first(),
                                    color = if (isSelected) NexaMidnight else NexaSilver,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 5. Custom Car Photo Upload (Photo Picker)
                    Text("5. Car Photo Selection", color = NexaChrome, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { onUpdateCustomImageUri(null) },
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (posterCustomImageUri == null) NexaCyanAccent else NexaCardSurface,
                                contentColor = if (posterCustomImageUri == null) NexaMidnight else NexaChrome
                            )
                        ) {
                            Text("Use Studio Render", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (posterCustomImageUri != null) NexaGold else NexaCardSurface,
                                contentColor = if (posterCustomImageUri != null) NexaMidnight else NexaChrome
                            )
                        ) {
                            Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (posterCustomImageUri != null) "Photo Selected" else "Pick Device Photo", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 6. Starting Price Override
                    Text("6. Starting Price Display", color = NexaChrome, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = posterStartingPrice,
                        onValueChange = { onUpdateStartingPrice(it) },
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
        }
    }
}

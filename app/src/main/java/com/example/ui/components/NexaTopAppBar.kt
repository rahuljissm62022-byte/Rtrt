package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.R
import com.example.ui.theme.CallBlue
import com.example.ui.theme.NexaCardBorder
import com.example.ui.theme.NexaCardSurface
import com.example.ui.theme.NexaChrome
import com.example.ui.theme.NexaCyanAccent
import com.example.ui.theme.NexaDarkBlue
import com.example.ui.theme.NexaGold
import com.example.ui.theme.NexaMidnight
import com.example.ui.theme.NexaMutedText
import com.example.ui.theme.NexaSilver
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.Screen
import com.example.util.ShowroomActions

@Composable
fun NexaTopAppBar(
    currentScreen: Screen,
    isAdminAuthenticated: Boolean,
    onNavigate: (Screen) -> Unit,
    onAdminToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        NexaMidnight,
                        NexaDarkBlue
                    )
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Showroom Logo Emblem
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, NexaCardBorder, RoundedCornerShape(10.dp))
                    .clickable { onNavigate(Screen.HOME) },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_app_icon),
                    contentDescription = "Pearl Cars Nexa Logo",
                    modifier = Modifier.size(42.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Brand Titles
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigate(Screen.HOME) }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "PEARL CARS",
                        color = NexaChrome,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(NexaCyanAccent.copy(alpha = 0.2f))
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "NEXA",
                            color = NexaCyanAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
                Text(
                    text = "SASARAM, BIHAR",
                    color = NexaSilver,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
            }

            // Quick Call RM Button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(CallBlue.copy(alpha = 0.2f))
                    .border(1.dp, CallBlue.copy(alpha = 0.5f), CircleShape)
                    .clickable {
                        ShowroomActions.dialPhone(context, "9031849243")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Call Rahul Kumar",
                    tint = NexaChrome,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Quick WhatsApp Button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(WhatsAppGreen.copy(alpha = 0.2f))
                    .border(1.dp, WhatsAppGreen.copy(alpha = 0.5f), CircleShape)
                    .clickable {
                        ShowroomActions.openWhatsApp(
                            context,
                            "9031849243",
                            "Hello Rahul Ji! I am contacting you from the Pearl Cars NEXA Sasaram App regarding showroom enquiries."
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "WA",
                    color = WhatsAppGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Ad Creator (Poster Maker) shortcut
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (currentScreen == Screen.POSTER_MAKER) NexaGold.copy(alpha = 0.3f)
                        else NexaCardSurface
                    )
                    .border(1.dp, NexaCardBorder, CircleShape)
                    .clickable {
                        onNavigate(Screen.POSTER_MAKER)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = "Ad Poster Maker",
                    tint = if (currentScreen == Screen.POSTER_MAKER) NexaGold else NexaSilver,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Admin Panel Toggle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isAdminAuthenticated) NexaCyanAccent.copy(alpha = 0.2f)
                        else NexaCardSurface
                    )
                    .border(1.dp, NexaCardBorder, CircleShape)
                    .clickable { onAdminToggle() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isAdminAuthenticated) Icons.Default.LockOpen else Icons.Default.Lock,
                    contentDescription = "Admin Panel",
                    tint = if (isAdminAuthenticated) NexaCyanAccent else NexaMutedText,
                    modifier = Modifier.size(17.dp)
                )
            }
        }
    }
}

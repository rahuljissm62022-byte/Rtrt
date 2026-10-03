package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
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
import com.example.ui.theme.CallBlue
import com.example.ui.theme.NexaCardBorder
import com.example.ui.theme.NexaCardSurface
import com.example.ui.theme.NexaChrome
import com.example.ui.theme.NexaCyanAccent
import com.example.ui.theme.NexaGold
import com.example.ui.theme.NexaMidnight
import com.example.ui.theme.NexaMutedText
import com.example.ui.theme.NexaNavy
import com.example.ui.theme.NexaSilver
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.theme.WhatsAppGreenDark
import com.example.util.ShowroomActions

@Composable
fun RelationshipManagerCallCard(
    modifier: Modifier = Modifier,
    customMessage: String = "Hello Rahul Ji! I am contacting you from the Pearl Cars NEXA Sasaram App regarding showroom enquiries."
) {
    val context = LocalContext.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = NexaNavy),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaCyanAccent.copy(alpha = 0.4f), NexaCardBorder)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar Badge
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(NexaCyanAccent, NexaCardSurface)
                            )
                        )
                        .border(2.dp, NexaCyanAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "RK",
                        color = NexaMidnight,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Rahul Kumar",
                            color = NexaChrome,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified RM",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "Senior Relationship Manager",
                        color = NexaSilver,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Showroom",
                            tint = NexaCyanAccent,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Pearl Cars NEXA • Sasaram, Bihar",
                            color = NexaMutedText,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Dual CTA Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Call Button
                Button(
                    onClick = { ShowroomActions.dialPhone(context, "9031849243") },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CallBlue,
                        contentColor = NexaChrome
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Call RM",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // WhatsApp Button
                Button(
                    onClick = {
                        ShowroomActions.openWhatsApp(context, "9031849243", customMessage)
                    },
                    modifier = Modifier
                        .weight(1.2f)
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = NexaMidnight
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp",
                        tint = NexaMidnight,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WhatsApp Chat",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
fun ShowroomCtaRow(
    modifier: Modifier = Modifier,
    customMessage: String = "Hello Rahul Ji! I am contacting you from the Pearl Cars NEXA Sasaram App regarding showroom enquiries."
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = { ShowroomActions.dialPhone(context, "9031849243") },
            modifier = Modifier
                .weight(1f)
                .height(46.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = NexaCardSurface,
                contentColor = NexaChrome
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(NexaCardBorder, NexaSilver.copy(alpha = 0.5f))))
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Call",
                tint = NexaCyanAccent,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Call (9031849243)",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                ShowroomActions.openWhatsApp(context, "9031849243", customMessage)
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
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = "WhatsApp",
                tint = NexaMidnight,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "WhatsApp Rahul Ji",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NexaCardBorder
import com.example.ui.theme.NexaCyanAccent
import com.example.ui.theme.NexaMidnight
import com.example.ui.theme.NexaMutedText
import com.example.ui.theme.NexaNavy
import com.example.ui.theme.NexaSilver
import com.example.ui.viewmodel.Screen

data class NavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun NexaBottomNavigation(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(Screen.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home),
        NavItem(Screen.CARS, "Cars", Icons.Filled.DirectionsCar, Icons.Outlined.DirectionsCar),
        NavItem(Screen.OFFERS, "Offers", Icons.Filled.LocalOffer, Icons.Outlined.LocalOffer),
        NavItem(Screen.TEST_DRIVE, "Test Drive", Icons.Filled.Speed, Icons.Outlined.Speed),
        NavItem(Screen.CONTACT, "Contact", Icons.Filled.Person, Icons.Outlined.Person)
    )

    NavigationBar(
        modifier = modifier,
        containerColor = NexaMidnight,
        contentColor = NexaSilver,
        tonalElevation = 8.dp,
        windowInsets = WindowInsets.navigationBars
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.screen) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NexaMidnight,
                    selectedTextColor = NexaCyanAccent,
                    indicatorColor = NexaCyanAccent,
                    unselectedIconColor = NexaMutedText,
                    unselectedTextColor = NexaMutedText
                )
            )
        }
    }
}

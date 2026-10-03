package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.CarDetailSheet
import com.example.ui.components.LeadSuccessDialog
import com.example.ui.components.NexaBottomNavigation
import com.example.ui.components.NexaTopAppBar
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.CarsScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OffersScreen
import com.example.ui.screens.PosterMakerScreen
import com.example.ui.screens.TestDriveScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NexaMidnight
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.ShowroomViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PearlCarsMainApp()
            }
        }
    }
}

@Composable
fun PearlCarsMainApp(
    viewModel: ShowroomViewModel = viewModel(
        factory = ShowroomViewModel.provideFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext as PearlCarsApp
        )
    )
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val cars by viewModel.allCars.collectAsStateWithLifecycle()
    val filteredCars by viewModel.filteredCars.collectAsStateWithLifecycle()
    val offers by viewModel.allOffers.collectAsStateWithLifecycle()
    val filteredOffers by viewModel.filteredOffers.collectAsStateWithLifecycle()
    val leads by viewModel.allLeads.collectAsStateWithLifecycle()
    val config by viewModel.showroomConfig.collectAsStateWithLifecycle()
    val selectedSegment by viewModel.selectedCarSegment.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedOfferCategory.collectAsStateWithLifecycle()
    val selectedCarForDetails by viewModel.selectedCarForDetails.collectAsStateWithLifecycle()
    val leadFormState by viewModel.leadFormState.collectAsStateWithLifecycle()

    val posterCarModel by viewModel.posterCarModel.collectAsStateWithLifecycle()
    val posterFestivalName by viewModel.posterFestivalName.collectAsStateWithLifecycle()
    val posterOfferHeadline by viewModel.posterOfferHeadline.collectAsStateWithLifecycle()
    val posterStartingPrice by viewModel.posterStartingPrice.collectAsStateWithLifecycle()
    val posterBackground by viewModel.posterBackground.collectAsStateWithLifecycle()
    val posterCustomImageUri by viewModel.posterCustomImageUri.collectAsStateWithLifecycle()

    val isAdminAuthenticated by viewModel.isAdminAuthenticated.collectAsStateWithLifecycle()

    // Handle Android system back gesture cleanly
    BackHandler(enabled = currentScreen != Screen.HOME || selectedCarForDetails != null) {
        if (selectedCarForDetails != null) {
            viewModel.selectCarForDetails(null)
        } else {
            viewModel.navigateBack()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(NexaMidnight),
        topBar = {
            NexaTopAppBar(
                currentScreen = currentScreen,
                isAdminAuthenticated = isAdminAuthenticated,
                onNavigate = { viewModel.navigateTo(it) },
                onAdminToggle = {
                    if (currentScreen == Screen.ADMIN) {
                        viewModel.navigateTo(Screen.HOME)
                    } else {
                        viewModel.navigateTo(Screen.ADMIN)
                    }
                }
            )
        },
        bottomBar = {
            NexaBottomNavigation(
                currentScreen = currentScreen,
                onNavigate = { viewModel.navigateTo(it) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(NexaMidnight)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    Screen.HOME -> HomeScreen(
                        cars = cars,
                        offers = offers,
                        onNavigate = { viewModel.navigateTo(it) },
                        onBookTestDrive = { car ->
                            viewModel.prepareLeadForCar(car.modelName, "Test Drive")
                            viewModel.navigateTo(Screen.TEST_DRIVE)
                        },
                        onGetBestPrice = { car ->
                            viewModel.prepareLeadForCar(car.modelName, "Best Price Quote")
                            viewModel.navigateTo(Screen.TEST_DRIVE)
                        },
                        onViewCarDetails = { car ->
                            viewModel.selectCarForDetails(car)
                        },
                        onClaimOffer = { offer ->
                            viewModel.prepareLeadForCar(
                                offer.applicableModels.split(",").firstOrNull()?.trim() ?: "Grand Vitara",
                                "Offer: ${offer.title}"
                            )
                            viewModel.navigateTo(Screen.TEST_DRIVE)
                        }
                    )

                    Screen.CARS -> CarsScreen(
                        cars = filteredCars,
                        selectedSegment = selectedSegment,
                        onSegmentSelected = { viewModel.selectCarSegment(it) },
                        onBookTestDrive = { car ->
                            viewModel.prepareLeadForCar(car.modelName, "Test Drive")
                            viewModel.navigateTo(Screen.TEST_DRIVE)
                        },
                        onGetBestPrice = { car ->
                            viewModel.prepareLeadForCar(car.modelName, "Best Price Quote")
                            viewModel.navigateTo(Screen.TEST_DRIVE)
                        },
                        onViewCarDetails = { car ->
                            viewModel.selectCarForDetails(car)
                        }
                    )

                    Screen.OFFERS -> OffersScreen(
                        offers = filteredOffers,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.selectOfferCategory(it) },
                        onClaimOffer = { offer ->
                            viewModel.prepareLeadForCar(
                                offer.applicableModels.split(",").firstOrNull()?.trim() ?: "Grand Vitara",
                                "Offer: ${offer.title}"
                            )
                            viewModel.navigateTo(Screen.TEST_DRIVE)
                        },
                        onNavigate = { viewModel.navigateTo(it) }
                    )

                    Screen.TEST_DRIVE -> TestDriveScreen(
                        cars = cars,
                        formState = leadFormState,
                        onUpdateForm = { name, phone, car, date, time, city, loc, msg, type ->
                            viewModel.updateLeadForm(name, phone, car, date, time, city, loc, msg, type)
                        },
                        onSubmit = { viewModel.submitLeadForm() }
                    )

                    Screen.CONTACT -> ContactScreen(
                        config = config
                    )

                    Screen.POSTER_MAKER -> PosterMakerScreen(
                        cars = cars,
                        config = config,
                        posterCarModel = posterCarModel,
                        posterFestivalName = posterFestivalName,
                        posterOfferHeadline = posterOfferHeadline,
                        posterStartingPrice = posterStartingPrice,
                        posterBackground = posterBackground,
                        posterCustomImageUri = posterCustomImageUri,
                        onUpdateCarModel = { viewModel.updatePosterCarModel(it) },
                        onUpdateFestival = { viewModel.updatePosterFestival(it) },
                        onUpdateOfferHeadline = { viewModel.updatePosterOfferHeadline(it) },
                        onUpdateStartingPrice = { viewModel.updatePosterStartingPrice(it) },
                        onUpdateBackground = { viewModel.updatePosterBackground(it) },
                        onUpdateCustomImageUri = { viewModel.updatePosterCustomImageUri(it) }
                    )

                    Screen.ADMIN -> AdminDashboardScreen(
                        isAuthenticated = isAdminAuthenticated,
                        cars = cars,
                        offers = offers,
                        leads = leads,
                        config = config,
                        onAuthenticate = { viewModel.authenticateAdmin(it) },
                        onLogout = { viewModel.logoutAdmin() },
                        onUpdateLeadStatus = { lead, newStatus -> viewModel.updateLeadStatus(lead, newStatus) },
                        onDeleteLead = { lead -> viewModel.deleteLead(lead) },
                        onUpdateCarPrice = { car, newDisplay, newStarting ->
                            viewModel.updateCarPrice(car, newDisplay, newStarting)
                        },
                        onAddOffer = { newOffer -> viewModel.addOffer(newOffer) },
                        onDeleteOffer = { id -> viewModel.deleteOffer(id) },
                        onUpdateConfig = { updated -> viewModel.updateShowroomConfig(updated) },
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }
            }

            // Detailed Car Modal Sheet
            if (selectedCarForDetails != null) {
                CarDetailSheet(
                    car = selectedCarForDetails!!,
                    onDismiss = { viewModel.selectCarForDetails(null) },
                    onBookTestDrive = { car ->
                        viewModel.selectCarForDetails(null)
                        viewModel.prepareLeadForCar(car.modelName, "Test Drive")
                        viewModel.navigateTo(Screen.TEST_DRIVE)
                    },
                    onGetBestPrice = { car ->
                        viewModel.selectCarForDetails(null)
                        viewModel.prepareLeadForCar(car.modelName, "Best Price Quote")
                        viewModel.navigateTo(Screen.TEST_DRIVE)
                    }
                )
            }

            // Customer Lead Submission Success Dialog
            if (leadFormState.showSuccessDialog) {
                LeadSuccessDialog(
                    customerName = leadFormState.customerName,
                    interestedCar = leadFormState.interestedCar,
                    onDismiss = { viewModel.dismissSuccessDialog() }
                )
            }
        }
    }
}

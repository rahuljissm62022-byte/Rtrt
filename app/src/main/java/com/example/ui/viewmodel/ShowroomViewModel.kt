package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.PearlCarsApp
import com.example.data.model.Car
import com.example.data.model.Lead
import com.example.data.model.Offer
import com.example.data.model.ShowroomConfig
import com.example.data.repository.ShowroomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen(val title: String) {
    HOME("Home"),
    CARS("Cars"),
    OFFERS("Offers"),
    TEST_DRIVE("Test Drive"),
    CONTACT("Contact"),
    POSTER_MAKER("Ad Creator"),
    ADMIN("Admin Panel")
}

data class LeadFormState(
    val customerName: String = "",
    val phone: String = "",
    val interestedCar: String = "Grand Vitara",
    val preferredDate: String = "Tomorrow",
    val timeSlot: String = "10:00 AM - 01:00 PM",
    val city: String = "Sasaram",
    val locationPreference: String = "Showroom Sasaram",
    val message: String = "",
    val enquiryType: String = "Test Drive",
    val isSubmitting: Boolean = false,
    val showSuccessDialog: Boolean = false,
    val errorMessage: String? = null
)

class ShowroomViewModel(
    application: Application,
    private val repository: ShowroomRepository
) : AndroidViewModel(application) {

    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _screenHistory = mutableListOf<Screen>()

    val allCars: StateFlow<List<Car>> = repository.allCars
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allOffers: StateFlow<List<Offer>> = repository.allOffers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allLeads: StateFlow<List<Lead>> = repository.allLeads
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val showroomConfig: StateFlow<ShowroomConfig> = repository.showroomConfig

    // Filtering
    private val _selectedCarSegment = MutableStateFlow("ALL")
    val selectedCarSegment: StateFlow<String> = _selectedCarSegment.asStateFlow()

    val filteredCars: StateFlow<List<Car>> = combine(allCars, _selectedCarSegment) { cars, segment ->
        if (segment == "ALL") cars else cars.filter { it.segment.equals(segment, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedOfferCategory = MutableStateFlow("ALL")
    val selectedOfferCategory: StateFlow<String> = _selectedOfferCategory.asStateFlow()

    val filteredOffers: StateFlow<List<Offer>> = combine(allOffers, _selectedOfferCategory) { offers, category ->
        if (category == "ALL") offers else offers.filter { it.category.contains(category, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected items for modal views
    private val _selectedCarForDetails = MutableStateFlow<Car?>(null)
    val selectedCarForDetails: StateFlow<Car?> = _selectedCarForDetails.asStateFlow()

    // Lead Form State
    private val _leadFormState = MutableStateFlow(LeadFormState())
    val leadFormState: StateFlow<LeadFormState> = _leadFormState.asStateFlow()

    // Poster Maker State
    private val _posterCarModel = MutableStateFlow("Grand Vitara")
    val posterCarModel: StateFlow<String> = _posterCarModel.asStateFlow()

    private val _posterFestivalName = MutableStateFlow("Chhath Puja Mahabachat")
    val posterFestivalName: StateFlow<String> = _posterFestivalName.asStateFlow()

    private val _posterOfferHeadline = MutableStateFlow("SAVE UP TO ₹85,000*")
    val posterOfferHeadline: StateFlow<String> = _posterOfferHeadline.asStateFlow()

    private val _posterStartingPrice = MutableStateFlow("₹ 10.99 Lakh*")
    val posterStartingPrice: StateFlow<String> = _posterStartingPrice.asStateFlow()

    private val _posterBackground = MutableStateFlow("Nexa Royal Navy")
    val posterBackground: StateFlow<String> = _posterBackground.asStateFlow()

    private val _posterCustomImageUri = MutableStateFlow<String?>(null)
    val posterCustomImageUri: StateFlow<String?> = _posterCustomImageUri.asStateFlow()

    // Admin State
    private val _isAdminAuthenticated = MutableStateFlow(false)
    val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            _screenHistory.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (_screenHistory.isNotEmpty()) {
            _currentScreen.value = _screenHistory.removeAt(_screenHistory.size - 1)
            return true
        }
        if (_currentScreen.value != Screen.HOME) {
            _currentScreen.value = Screen.HOME
            return true
        }
        return false
    }

    fun selectCarSegment(segment: String) {
        _selectedCarSegment.value = segment
    }

    fun selectOfferCategory(category: String) {
        _selectedOfferCategory.value = category
    }

    fun selectCarForDetails(car: Car?) {
        _selectedCarForDetails.value = car
    }

    fun updateLeadForm(
        name: String? = null,
        phone: String? = null,
        interestedCar: String? = null,
        preferredDate: String? = null,
        timeSlot: String? = null,
        city: String? = null,
        locationPreference: String? = null,
        message: String? = null,
        enquiryType: String? = null
    ) {
        _leadFormState.value = _leadFormState.value.copy(
            customerName = name ?: _leadFormState.value.customerName,
            phone = phone ?: _leadFormState.value.phone,
            interestedCar = interestedCar ?: _leadFormState.value.interestedCar,
            preferredDate = preferredDate ?: _leadFormState.value.preferredDate,
            timeSlot = timeSlot ?: _leadFormState.value.timeSlot,
            city = city ?: _leadFormState.value.city,
            locationPreference = locationPreference ?: _leadFormState.value.locationPreference,
            message = message ?: _leadFormState.value.message,
            enquiryType = enquiryType ?: _leadFormState.value.enquiryType,
            errorMessage = null
        )
    }

    fun prepareLeadForCar(carName: String, enquiryType: String = "Test Drive") {
        _leadFormState.value = _leadFormState.value.copy(
            interestedCar = carName,
            enquiryType = enquiryType
        )
    }

    fun submitLeadForm() {
        val form = _leadFormState.value
        if (form.customerName.isBlank()) {
            _leadFormState.value = form.copy(errorMessage = "Please enter your name")
            return
        }
        if (form.phone.isBlank() || form.phone.length < 10) {
            _leadFormState.value = form.copy(errorMessage = "Please enter a valid 10-digit mobile number")
            return
        }

        viewModelScope.launch {
            _leadFormState.value = _leadFormState.value.copy(isSubmitting = true, errorMessage = null)
            val lead = Lead(
                customerName = form.customerName.trim(),
                phone = form.phone.trim(),
                interestedCar = form.interestedCar,
                preferredDate = form.preferredDate,
                timeSlot = form.timeSlot,
                city = form.city.trim(),
                locationPreference = form.locationPreference,
                message = form.message.trim(),
                enquiryType = form.enquiryType,
                status = "NEW"
            )
            repository.insertLead(lead)
            _leadFormState.value = _leadFormState.value.copy(
                isSubmitting = false,
                showSuccessDialog = true
            )
        }
    }

    fun dismissSuccessDialog() {
        _leadFormState.value = _leadFormState.value.copy(
            showSuccessDialog = false,
            customerName = "",
            phone = "",
            message = ""
        )
    }

    // Poster Maker updates
    fun updatePosterCarModel(model: String) {
        _posterCarModel.value = model
        // Auto update default price
        val car = allCars.value.find { it.modelName.equals(model, ignoreCase = true) }
        if (car != null) {
            _posterStartingPrice.value = car.priceDisplay
        }
    }

    fun updatePosterFestival(festival: String) {
        _posterFestivalName.value = festival
    }

    fun updatePosterOfferHeadline(headline: String) {
        _posterOfferHeadline.value = headline
    }

    fun updatePosterStartingPrice(price: String) {
        _posterStartingPrice.value = price
    }

    fun updatePosterBackground(bg: String) {
        _posterBackground.value = bg
    }

    fun updatePosterCustomImageUri(uri: String?) {
        _posterCustomImageUri.value = uri
    }

    // Admin controls
    fun authenticateAdmin(passcode: String): Boolean {
        if (passcode == "9031" || passcode == "admin" || passcode == "9031849243") {
            _isAdminAuthenticated.value = true
            return true
        }
        return false
    }

    fun logoutAdmin() {
        _isAdminAuthenticated.value = false
    }

    fun updateLeadStatus(lead: Lead, newStatus: String) {
        viewModelScope.launch {
            repository.updateLead(lead.copy(status = newStatus))
        }
    }

    fun deleteLead(lead: Lead) {
        viewModelScope.launch {
            repository.deleteLeadById(lead.id)
        }
    }

    fun addOffer(offer: Offer) {
        viewModelScope.launch {
            repository.insertOffer(offer)
        }
    }

    fun deleteOffer(offerId: Int) {
        viewModelScope.launch {
            repository.deleteOfferById(offerId)
        }
    }

    fun updateCarPrice(car: Car, newPriceDisplay: String, newStartingPrice: Double) {
        viewModelScope.launch {
            repository.updateCar(car.copy(priceDisplay = newPriceDisplay, startingPrice = newStartingPrice))
        }
    }

    fun updateShowroomConfig(config: ShowroomConfig) {
        repository.updateShowroomConfig(config)
    }

    companion object {
        fun provideFactory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val app = application as PearlCarsApp
                    return ShowroomViewModel(application, app.repository) as T
                }
            }
    }
}

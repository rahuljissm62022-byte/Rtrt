package com.example.data.repository

import com.example.data.db.CarDao
import com.example.data.db.LeadDao
import com.example.data.db.OfferDao
import com.example.data.model.Car
import com.example.data.model.Lead
import com.example.data.model.Offer
import com.example.data.model.ShowroomConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShowroomRepository(
    private val carDao: CarDao,
    private val offerDao: OfferDao,
    private val leadDao: LeadDao
) {
    val allCars: Flow<List<Car>> = carDao.getAllCars()
    val allOffers: Flow<List<Offer>> = offerDao.getAllOffers()
    val allLeads: Flow<List<Lead>> = leadDao.getAllLeads()

    private val _showroomConfig = MutableStateFlow(ShowroomConfig())
    val showroomConfig: StateFlow<ShowroomConfig> = _showroomConfig.asStateFlow()

    fun updateShowroomConfig(config: ShowroomConfig) {
        _showroomConfig.value = config
    }

    suspend fun insertLead(lead: Lead): Long = leadDao.insertLead(lead)
    suspend fun updateLead(lead: Lead) = leadDao.updateLead(lead)
    suspend fun deleteLeadById(id: Int) = leadDao.deleteLeadById(id)

    suspend fun insertCar(car: Car): Long = carDao.insertCar(car)
    suspend fun updateCar(car: Car) = carDao.updateCar(car)
    suspend fun deleteCar(car: Car) = carDao.deleteCar(car)

    suspend fun insertOffer(offer: Offer): Long = offerDao.insertOffer(offer)
    suspend fun updateOffer(offer: Offer) = offerDao.updateOffer(offer)
    suspend fun deleteOfferById(id: Int) = offerDao.deleteOfferById(id)

    suspend fun ensureDataPopulated() {
        if (carDao.getCount() == 0) {
            carDao.insertCars(com.example.data.db.AppDatabase.INITIAL_CARS)
        }
        if (offerDao.getCount() == 0) {
            offerDao.insertOffers(com.example.data.db.AppDatabase.INITIAL_OFFERS)
        }
    }
}

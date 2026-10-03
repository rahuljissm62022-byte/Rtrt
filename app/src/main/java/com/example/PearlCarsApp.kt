package com.example

import android.app.Application
import com.example.data.db.AppDatabase
import com.example.data.repository.ShowroomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PearlCarsApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {
        ShowroomRepository(
            carDao = database.carDao(),
            offerDao = database.offerDao(),
            leadDao = database.leadDao()
        )
    }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            repository.ensureDataPopulated()
        }
    }
}

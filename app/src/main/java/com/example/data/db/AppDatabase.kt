package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.Car
import com.example.data.model.Lead
import com.example.data.model.Offer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Car::class, Offer::class, Lead::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun offerDao(): OfferDao
    abstract fun leadDao(): LeadDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pearl_cars_nexa_db"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.carDao(), database.offerDao(), database.leadDao())
                    }
                }
            }
        }

        suspend fun populateInitialData(carDao: CarDao, offerDao: OfferDao, leadDao: LeadDao) {
            if (carDao.getCount() == 0) {
                carDao.insertCars(INITIAL_CARS)
            }
            if (offerDao.getCount() == 0) {
                offerDao.insertOffers(INITIAL_OFFERS)
            }
        }

        val INITIAL_CARS = listOf(
            Car(
                modelName = "Grand Vitara",
                tagline = "The Intelligent Electric Hybrid SUV",
                startingPrice = 10.99,
                priceDisplay = "₹ 10.99 - 20.09 Lakh*",
                segment = "SUV",
                engine = "1.5L Intelligent Electric Hybrid & 1.5L K-Series Dual Jet",
                mileage = "27.97 km/l (Hybrid) / 21.11 km/l (Petrol)",
                transmission = "e-CVT / 5-Speed MT / 6-Speed AT / ALLGRIP 4WD",
                seating = "5 Seater",
                features = "Intelligent Electric Hybrid EV Mode, Panoramic Sunroof, Head-Up Display, 360 View Camera, Wireless Charging, Ventilated Front Seats, 6 Airbags standard, ALLGRIP 4x4",
                variants = "Sigma, Delta, Zeta, Zeta+ (Hybrid), Alpha, Alpha+ (Hybrid)",
                colours = "Nexa Blue, Arctic White, Splendid Silver, Grandeur Grey, Opulent Red, Chestnut Brown, Dual-Tone Black Roof",
                drawableName = "img_nexa_hero",
                isFeatured = true
            ),
            Car(
                modelName = "Invicto",
                tagline = "The Opulent Luxury MPV",
                startingPrice = 25.21,
                priceDisplay = "₹ 25.21 - 28.92 Lakh*",
                segment = "MPV",
                engine = "2.0L Intelligent Electric Hybrid",
                mileage = "21.19 km/l",
                transmission = "e-CVT Automatic",
                seating = "7 / 8 Seater Luxury Captain Seats",
                features = "Panoramic Sunroof with Ambient Lighting, Powered Tailgate with Walk-Away Close, Ventilated Front Seats, 10.1-inch SmartPlay Magna Screen, 6 Airbags standard, 360 View Camera",
                variants = "Zeta+ (7/8 Str), Alpha+ (7 Str)",
                colours = "Mystic White, Stellar Bronze, Celestial Blue, Majestic Silver",
                drawableName = "img_car_invicto",
                isFeatured = true
            ),
            Car(
                modelName = "Fronx",
                tagline = "The Shape of New Crossover",
                startingPrice = 7.51,
                priceDisplay = "₹ 7.51 - 13.04 Lakh*",
                segment = "SUV",
                engine = "1.0L Turbo Boosterjet / 1.2L Dual Jet Petrol & CNG",
                mileage = "22.89 km/l (Petrol) / 28.51 km/kg (CNG)",
                transmission = "6-Speed AT with Paddle Shifters / 5-MT / Auto Gear Shift",
                seating = "5 Seater",
                features = "Geometric Precision Cut Alloy Wheels, Head-Up Display, 9-inch SmartPlay Pro+ Audio, Wireless Charger, 360 View Camera, Fast USB Type-C Ports, Dual-tone Interiors",
                variants = "Sigma, Delta, Delta+, Zeta, Alpha",
                colours = "Nexa Blue, Arctic White, Grandeur Grey, Earthen Brown, Opulent Red, Splendid Silver, Bluish Black",
                drawableName = "img_car_fronx",
                isFeatured = true
            ),
            Car(
                modelName = "Jimny",
                tagline = "The Pure 4x4 Off-roader",
                startingPrice = 12.74,
                priceDisplay = "₹ 12.74 - 14.95 Lakh*",
                segment = "SUV",
                engine = "1.5L K15B Petrol with Idle Start-Stop",
                mileage = "16.94 km/l",
                transmission = "5-Speed MT / 4-Speed AT with AllGrip Pro 4WD",
                seating = "4 Seater Off-Road",
                features = "AllGrip Pro 4WD with Low Range Transfer Gear (4L mode), Rigid Ladder Frame Chassis, 3-link Rigid Axle Suspension, Hill Hold & Hill Descent Control, Scratch-resistant Cladding",
                variants = "Zeta, Alpha, Alpha Dual Tone",
                colours = "Kinetic Yellow + Black Roof, Sizzling Red, Bluish Black, Granite Grey, Pearl Arctic White, Nexa Blue",
                drawableName = "img_car_jimny",
                isFeatured = true
            ),
            Car(
                modelName = "Baleno",
                tagline = "The Sensual Tech Hatchback",
                startingPrice = 6.66,
                priceDisplay = "₹ 6.66 - 9.88 Lakh*",
                segment = "HATCHBACK",
                engine = "1.2L Dual Jet Dual VVT Petrol & Factory-fitted S-CNG",
                mileage = "22.35 km/l (Petrol) / 30.61 km/kg (CNG)",
                transmission = "5-Speed Manual / Auto Gear Shift (AGS)",
                seating = "5 Seater",
                features = "Head-Up Display, 360 View Camera, Surround Sense Audio by ARKAMYS, 9-inch HD SmartPlay Pro+, Suzuki Connect with 40+ Features, Auto Climate Control, 6 Airbags",
                variants = "Sigma, Delta, Zeta, Alpha",
                colours = "Nexa Blue, Arctic White, Splendid Silver, Grandeur Grey, Opulent Red, Luxe Beige",
                drawableName = "img_nexa_hero",
                isFeatured = false
            ),
            Car(
                modelName = "XL6",
                tagline = "The Premium 6-Seater MPV",
                startingPrice = 11.61,
                priceDisplay = "₹ 11.61 - 14.77 Lakh*",
                segment = "MPV",
                engine = "1.5L K15C Smart Hybrid Petrol & S-CNG",
                mileage = "20.97 km/l (Petrol) / 26.32 km/kg (CNG)",
                transmission = "6-Speed AT with Paddle Shifters / 5-MT",
                seating = "6 Seater Captain Seats with Individual Armrests",
                features = "Captain Seats with Individual Armrests, Ventilated Front Seats, 360 View Camera, Tyre Pressure Monitoring System (TPMS), Smoke Grey LED Tail Lamps, Cruise Control",
                variants = "Zeta, Alpha, Alpha+",
                colours = "Nexa Blue, Brave Khaki, Opulent Red, Grandeur Grey, Splendid Silver, Arctic White",
                drawableName = "img_car_invicto",
                isFeatured = false
            ),
            Car(
                modelName = "Ciaz",
                tagline = "The Elegant Luxury Sedan",
                startingPrice = 9.40,
                priceDisplay = "₹ 9.40 - 12.29 Lakh*",
                segment = "SEDAN",
                engine = "1.5L K15 Smart Hybrid Petrol",
                mileage = "20.65 km/l",
                transmission = "5-Speed MT / 4-Speed Automatic",
                seating = "5 Seater Executive Sedan",
                features = "Smart Hybrid Technology with Dual Battery Setup, Premium Birch Blonde Wood Accents, Cruise Control, Electronic Stability Program with Hill Hold, Auto Headlamps",
                variants = "Sigma, Delta, Zeta, Alpha",
                colours = "Nexa Blue, Pearl Sangria Red, Pearl Metallic Dignity Brown, Metallic Premium Silver, Pearl Snow White",
                drawableName = "img_nexa_hero",
                isFeatured = false
            ),
            Car(
                modelName = "Ignis",
                tagline = "The Compact Urban SUV",
                startingPrice = 5.84,
                priceDisplay = "₹ 5.84 - 8.16 Lakh*",
                segment = "HATCHBACK",
                engine = "1.2L VVT Petrol",
                mileage = "20.89 km/l",
                transmission = "5-Speed Manual / Auto Gear Shift",
                seating = "5 Seater Compact",
                features = "180 mm High Ground Clearance, Bold Chrome Grille, LED Projector Headlamps with DRLs, SmartPlay Studio Infotainment, Robust Roof Rails, Dual-tone customization",
                variants = "Sigma, Delta, Zeta, Alpha",
                colours = "Lucent Orange, Silky Silver, Glistening Grey, Turquoise Blue, Nexa Blue, Pearl Arctic White",
                drawableName = "img_car_fronx",
                isFeatured = false
            )
        )

        val INITIAL_OFFERS = listOf(
            Offer(
                title = "Chhath Puja & Festive Mahabachat",
                subtitle = "Exclusive Festive Benefits in Sasaram & Rohtas",
                discountAmount = "Up to ₹ 85,000*",
                category = "Festival Offer",
                validUntil = "30th November 2026",
                description = "Celebrate the festival with Pearl Cars NEXA Sasaram! Get instant cash discount of ₹55,000 + exchange bonus of ₹30,000 on Grand Vitara, Fronx and Baleno.",
                applicableModels = "Grand Vitara, Fronx, Baleno, Jimny",
                badgeText = "FESTIVAL SPECIAL",
                isFeatured = true
            ),
            Offer(
                title = "Mega Cash Bonanza",
                subtitle = "Direct Showroom Invoice Cash Discount",
                discountAmount = "Up to ₹ 50,000 Off",
                category = "Cash Discount",
                validUntil = "Limited Stock Offer",
                description = "Direct cash rebate on select NEXA vehicles. No waiting, instant on-road price reduction on booking at Pearl Cars Sasaram.",
                applicableModels = "Jimny 4x4, Baleno, Ignis, Ciaz",
                badgeText = "DIRECT CASH DISCOUNT",
                isFeatured = true
            ),
            Offer(
                title = "Sasaram Mega Exchange Carnival",
                subtitle = "Highest Valuation For Your Old Car + Guaranteed Bonus",
                discountAmount = "₹ 35,000 Extra Bonus",
                category = "Exchange Bonus",
                validUntil = "End of This Month",
                description = "Bring any old car (any make, model or year) to Pearl Cars Sasaram and get top market valuation plus an extra guaranteed ₹35,000 exchange bonus.",
                applicableModels = "All NEXA Models",
                badgeText = "EXCHANGE CARNIVAL",
                isFeatured = true
            ),
            Offer(
                title = "100% On-Road Funding & Low ROI Scheme",
                subtitle = "Zero Down Payment with Instant Showroom Approval",
                discountAmount = "ROI Starting @ 7.99%*",
                category = "Finance Offer",
                validUntil = "Ongoing 2026",
                description = "Special showroom finance tie-up with SBI, HDFC, ICICI & Bank of Baroda. Fast loan clearance in 30 minutes with minimal documents for Sasaram & Bihar customers.",
                applicableModels = "All NEXA Cars",
                badgeText = "ZERO DOWN PAYMENT",
                isFeatured = false
            ),
            Offer(
                title = "Luxury Genuine Styling Kit Package",
                subtitle = "Complimentary NEXA Genuine Accessories",
                discountAmount = "Accessories Worth ₹ 25,000 Free",
                category = "Accessories Offer",
                validUntil = "First 25 Bookings",
                description = "Complimentary chrome styling exterior kit, premium luxury floor mats, waterproof car body cover, and ambient interior styling on new deliveries.",
                applicableModels = "Grand Vitara, Invicto, XL6, Fronx",
                badgeText = "FREE ACCESSORIES",
                isFeatured = false
            ),
            Offer(
                title = "Corporate & Govt Employee Advantage",
                subtitle = "Special Institutional Discount For Bihar Residents",
                discountAmount = "Up to ₹ 15,000 Extra",
                category = "Limited Period",
                validUntil = "Limited Period",
                description = "Exclusive institutional benefits for Teachers, Doctors, Defence, Railway & Govt Employees of Rohtas & Kaimur districts with priority vehicle allocation.",
                applicableModels = "Invicto, Grand Vitara, Fronx, Baleno, XL6",
                badgeText = "CORPORATE BENEFIT",
                isFeatured = false
            )
        )
    }
}

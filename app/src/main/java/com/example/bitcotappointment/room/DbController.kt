package com.example.bitcotappointment.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bitcotappointment.model.DBModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DbController @Inject constructor(private val context: Context) {

    private lateinit var db : AppDatabase

    fun initDatabase(){

        if (!this::db.isInitialized || !db.isOpen){

            db =  Room.databaseBuilder(
                context,
                AppDatabase::class.java, "BitcotDatabase"
            )
                .addCallback(object : RoomDatabase.Callback(){

                override fun onCreate(db: SupportSQLiteDatabase) {

                    super.onCreate(db)

                    CoroutineScope(Dispatchers.IO).launch{

                        insertInitialUsers()

                    }

                }

            }).build()

        }

    }

    suspend fun login(email: String, password: String): DBModels.User? {

        initDatabase()

        return db.userDao().login(email, password)

    }

    suspend fun getUsersByType(userType: DBModels.UserType): List<DBModels.User> {

        initDatabase()

        return db.userDao().getUsersByType(userType)

    }

    suspend fun getAppointmentsForDoctor(doctorId: Int): List<DBModels.AppointmentWithPatientDetails> {

        initDatabase()

        return db.appointmentDao().getAppointmentsForDoctor(doctorId)

    }

    suspend fun insertAppointment(appointment: DBModels.Appointment) {

        initDatabase()

        return db.appointmentDao().insertAppointment(appointment)

    }

    suspend fun updateAppointment(appointment: DBModels.Appointment): Int {

        initDatabase()

        return db.appointmentDao().updateAppointment(appointment)

    }

    suspend fun insertInitialUsers() {

        initDatabase()

        val initialUsers = listOf(
            DBModels.User(
                1,
                "",
                "Doctor 1",
                35,
                "Male",
                DBModels.UserType.DOCTOR,
                "doctor1@example.com",
                "password"
            ),
            DBModels.User(
                2,
                "",
                "Doctor 2",
                40,
                "Female",
                DBModels.UserType.DOCTOR,
                "doctor2@example.com",
                "password"
            ),
            DBModels.User(
                3,
                "",
                "Doctor 3",
                42,
                "Female",
                DBModels.UserType.DOCTOR,
                "doctor3@example.com",
                "password"
            ),
            DBModels.User(
                4,
                "",
                "Doctor 4",
                30,
                "Male",
                DBModels.UserType.DOCTOR,
                "doctor4@example.com",
                "password"
            ),
            DBModels.User(
                5,
                "",
                "Doctor 5",
                40,
                "Male",
                DBModels.UserType.DOCTOR,
                "doctor5@example.com",
                "password"
            ),
            DBModels.User(
                6,
                "",
                "Doctor 6",
                40,
                "Female",
                DBModels.UserType.DOCTOR,
                "doctor6@example.com",
                "password"
            ),
            DBModels.User(
                7,
                "",
                "Doctor 7",
                40,
                "Female",
                DBModels.UserType.DOCTOR,
                "doctor7@example.com",
                "password"
            ),
            DBModels.User(
                8,
                "",
                "Doctor 8",
                40,
                "Male",
                DBModels.UserType.DOCTOR,
                "doctor8@example.com",
                "password"
            ),
            DBModels.User(
                9,
                "",
                "Doctor 9",
                40,
                "Female",
                DBModels.UserType.DOCTOR,
                "doctor9@example.com",
                "password"
            ),
            DBModels.User(
                10,
                "",
                "Doctor 10",
                40,
                "Male",
                DBModels.UserType.DOCTOR,
                "doctor6@example.com",
                "password"
            ),
            DBModels.User(
                11,
                "",
                "Patient 1",
                20,
                "Male",
                DBModels.UserType.PATIENT,
                "patient1@example.com",
                "password"
            ),
            DBModels.User(
                12,
                "",
                "Patient 2",
                31,
                "Male",
                DBModels.UserType.PATIENT,
                "patient2@example.com",
                "password"
            ),
            DBModels.User(
                13,
                "",
                "Patient 3",
                25,
                "Male",
                DBModels.UserType.PATIENT,
                "patient3@example.com",
                "password"
            ),
            DBModels.User(
                14,
                "",
                "Patient 4",
                33,
                "Male",
                DBModels.UserType.PATIENT,
                "patient4@example.com",
                "password"
            ), DBModels.User(
                15,
                "",
                "Patient 5",
                27,
                "Female",
                DBModels.UserType.PATIENT,
                "patient5@example.com",
                "password"
            )
        )

        initialUsers.forEach { db.userDao().insertUser(it) }

    }

}
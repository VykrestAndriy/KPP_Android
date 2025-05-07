package com.example.lb_android

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lb_android.adapters.CarAdapter
import com.example.lb_android.models.Car

class CarActivity : AppCompatActivity() {

    companion object {
        lateinit var carList: MutableList<Car>
            private set
        var filteredCarList: List<Car>? = null
            internal set
    }

    private lateinit var carsRecyclerView: RecyclerView
    private lateinit var carsListAdapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val carToolbar: Toolbar = findViewById(R.id.toolbarSearch)
        setSupportActionBar(carToolbar)

        carList = mutableListOf(
            Car("Ford", "Focus", 2018, "Компактний та маневрений хетчбек", 15500.0, "ford_focus_2018"),
            Car("Nissan", "Qashqai", 2022, "Сучасний міський кросовер", 27000.0, "nissan_qashqai_2022"),
            Car("Hyundai", "Tucson", 2021, "Просторий та комфортний SUV", 24000.0, "hyundai_tucson_2021"),
            Car("Skoda", "Octavia", 2019, "Практичний та елегантний ліфтбек", 19800.0, "skoda_octavia_2019"),
            Car("Volkswagen", "Golf", 2020, "Легендарний німецький хетчбек", 22500.0, "volkswagen_golf_2020"),
            Car("Peugeot", "3008", 2023, "Стильний та технологічний кросовер", 31000.0, "peugeot_3008_2023"),
            Car("Kia", "Sportage", 2022, "Динамічний та безпечний кросовер", 26500.0, "kia_sportage_2022"),
            Car("Renault", "Megane", 2020, "Комфортабельний та економічний хетчбек", 18000.0, "renault_megane_2020"),
            Car("Opel", "Astra", 2021, "Надійний та практичний автомобіль", 17200.0, "opel_astra_2021"),
            Car("Mazda", "CX-5", 2023, "Елегантний та драйвовий кросовер", 33000.0, "mazda_cx5_2023")
        )

        carsRecyclerView = findViewById(R.id.recyclerViewCars)
        carsRecyclerView.layoutManager = LinearLayoutManager(this)
        carsListAdapter = CarAdapter(carList)
        carsRecyclerView.adapter = carsListAdapter
    }

    override fun onResume() {
        super.onResume()
        carsListAdapter.updateData(filteredCarList ?: carList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_car, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
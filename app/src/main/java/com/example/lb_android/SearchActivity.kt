package com.example.lb_android

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lb_android.adapters.CarAdapter
import com.example.lb_android.models.Car
import java.util.Locale

class SearchActivity : AppCompatActivity() {

    private lateinit var brandInput: AutoCompleteTextView
    private lateinit var modelInput: AutoCompleteTextView
    private lateinit var yearFromInput: Spinner
    private lateinit var yearToInput: Spinner
    private lateinit var costFromInput: EditText
    private lateinit var costToInput: EditText
    private lateinit var showMatchesButton: Button
    private lateinit var searchToolbar: Toolbar
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var searchResultsAdapter: CarAdapter

    private var filteredCarList: MutableList<Car> = CarActivity.carList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchToolbar = findViewById(R.id.toolbarSearch)
        setSupportActionBar(searchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        brandInput = findViewById(R.id.autoCompleteTextViewBrand)
        modelInput = findViewById(R.id.autoCompleteTextViewModel)
        yearFromInput = findViewById(R.id.spinnerYearFrom)
        yearToInput = findViewById(R.id.spinnerYearTo)
        costFromInput = findViewById(R.id.editTextCostFrom)
        costToInput = findViewById(R.id.editTextCostTo)
        showMatchesButton = findViewById(R.id.buttonMatches)
        showMatchesButton.isEnabled = false

        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView)
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        searchResultsAdapter = CarAdapter(filteredCarList)
        searchResultsRecyclerView.adapter = searchResultsAdapter
        searchResultsRecyclerView.visibility = View.GONE

        val carBrands = CarActivity.carList.map { it.brand }.toSortedSet().toList()
        Log.d("SearchActivity", "Список марок у onCreate: $carBrands")
        val brandListAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, carBrands)
        brandInput.setAdapter(brandListAdapter)

        val carModels = CarActivity.carList.map { it.model }.toSortedSet().toList()
        val modelListAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, carModels)
        modelInput.setAdapter(modelListAdapter)

        val carYears = CarActivity.carList.map { it.year }.toSortedSet().toList()
        val yearsWithAnyOption = mutableListOf("Будь-який рік") + carYears
        val yearListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yearsWithAnyOption)
        yearFromInput.adapter = yearListAdapter
        yearToInput.adapter = yearListAdapter

        val costPresets = listOf("Будь-яка", "1000", "10000", "100000")
        val costListAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, costPresets)
        costFromInput.hint = "Від (${costPresets[1]})"
        costToInput.hint = "До (${costPresets.last()})"

        val filterTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        brandInput.addTextChangedListener(filterTextWatcher)
        modelInput.addTextChangedListener(filterTextWatcher)
        costFromInput.addTextChangedListener(filterTextWatcher)
        costToInput.addTextChangedListener(filterTextWatcher)

        yearFromInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        yearToInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                applyFilters()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        showMatchesButton.setOnClickListener {
            applyFilters()
            searchResultsRecyclerView.visibility = View.VISIBLE
            searchResultsAdapter.updateData(filteredCarList)
        }

        applyFilters()
    }

    private fun applyFilters() {
        Log.d("SearchActivity", "applyFilters() викликано")
        Log.d("SearchActivity", "brandQuery: ${brandInput.text}, modelQuery: ${modelInput.text}, yearFrom: ${yearFromInput.selectedItem}, yearTo: ${yearToInput.selectedItem}, costFromText: ${costFromInput.text}, costToText: ${costToInput.text}")

        val brandQuery = brandInput.text.toString().lowercase(Locale.getDefault())
        val modelQuery = modelInput.text.toString().lowercase(Locale.getDefault())

        val selectedYearFrom = yearFromInput.selectedItem?.toString()
        val yearFrom = if (selectedYearFrom == "Будь-який рік") null else selectedYearFrom?.toIntOrNull()

        val selectedYearTo = yearToInput.selectedItem?.toString()
        val yearTo = if (selectedYearTo == "Будь-який рік") null else selectedYearTo?.toIntOrNull()

        val costFromText = costFromInput.text.toString()
        val costToText = costToInput.text.toString()

        val costFrom = when (costFromText) {
            "Будь-яка", "" -> 0.0
            else -> costFromText.toDoubleOrNull() ?: 0.0
        }

        val costTo = when (costToText) {
            "Будь-яка", "" -> Double.MAX_VALUE
            else -> costToText.toDoubleOrNull() ?: Double.MAX_VALUE
        }

        filteredCarList = CarActivity.carList.filter { car ->
            val isBrandMatch = brandQuery.isEmpty() || car.brand.lowercase(Locale.getDefault()).contains(brandQuery)
            val isModelMatch = modelQuery.isEmpty() || car.model.lowercase(Locale.getDefault()).contains(modelQuery)
            val isYearFromMatch = yearFrom == null || car.year >= yearFrom
            val isYearToMatch = yearTo == null || car.year <= yearTo
            val isCostFromMatch = car.cost >= costFrom
            val isCostToMatch = car.cost <= costTo

            isBrandMatch && isModelMatch && isYearFromMatch && isYearToMatch && isCostFromMatch && isCostToMatch
        }.toMutableList()

        searchResultsAdapter.updateData(filteredCarList)
        searchResultsRecyclerView.visibility = if (filteredCarList.isNotEmpty()) View.VISIBLE else View.GONE
        showMatchesButton.isEnabled = brandQuery.isNotEmpty() ||
                modelQuery.isNotEmpty() ||
                yearFrom != null ||
                yearTo != null ||
                costFromText.isNotEmpty() ||
                costToText.isNotEmpty()
    }
}
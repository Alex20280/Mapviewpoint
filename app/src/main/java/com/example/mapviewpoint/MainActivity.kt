package com.example.mapviewpoint

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.databinding.ActivityMainBinding
import com.example.mapviewpoint.databinding.FragmentSignUpBinding
import com.example.mapviewpoint.di.ViewModelFactory
import com.example.mapviewpoint.ui.map.MapViewModel
import com.example.mapviewpoint.ui.map.ToolbarIconClickListener
import java.util.Calendar
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ToolbarIconClickListener  {

   private lateinit var binding: ActivityMainBinding
    //private val binding by viewBinding(ActivityMainBinding::bind)

    @Inject
    lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeNavigation()
        viewModelInstantiation()
    }

    private fun viewModelInstantiation() {
        (applicationContext.applicationContext as App).appComponent.inject(this)
    }

    private fun initializeNavigation() {
        val navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.signInFragment
            )
        )

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateToolbar(destination.id, appBarConfiguration)
        }
    }

    private fun updateToolbar(destinationId: Int, appBarConfiguration: AppBarConfiguration) {
        if (appBarConfiguration.topLevelDestinations.none { it == destinationId }) {
            binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

            if (destinationId == R.id.mapFragment) {
                // If the current destination is the MapFragment, set a calendar icon and its behavior
                binding.toolbar.setNavigationIcon(R.drawable.calendar)
                binding.toolbar.setNavigationOnClickListener {
                    onToolbarIconClicked()
                    //Toast.makeText(this, "sdsdcc", Toast.LENGTH_LONG).show()
                    // Add your custom behavior for the calendar icon here
                    // For example, open a calendar view or perform any other desired action
                }
            }
        }

    }

    override fun onToolbarIconClicked() {
        // Implement your custom behavior for the toolbar icon click here
        // For example, to open the date picker dialog
        showDatePickerDialog()
    }

    private fun findNavController(): NavController {
        val host = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        return host.navController
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)

                val timestamp = selectedCalendar.timeInMillis
                sharedViewModel.setSelectedDate(timestamp)

            }, year, month, day)

        datePickerDialog.show()
    }

}
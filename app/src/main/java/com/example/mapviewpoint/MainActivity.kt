package com.example.mapviewpoint

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mapviewpoint.app.App
import com.example.mapviewpoint.databinding.ActivityMainBinding
import com.example.mapviewpoint.di.ViewModelFactory
import com.example.mapviewpoint.ui.map.ToolbarIconClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ToolbarIconClickListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
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
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        sharedViewModel = viewModelProvider.get(SharedViewModel::class.java)
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
            binding.toolbar.menu.removeItem(R.id.exit_menu_item)
            binding.toolbar.setOnMenuItemClickListener(null)
            binding.toolbar.setNavigationOnClickListener(null)
        }

        when (destinationId) {
            R.id.mapFragment -> {
                binding.toolbar.setNavigationIcon(R.drawable.calendar)
                binding.toolbar.inflateMenu(R.menu.toolbar_menu)

                binding.toolbar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.exit_menu_item -> {
                            onExitMenuClicked()
                            true // Return true to indicate that the click event has been consumed
                        }
                        else -> false // Return false for other menu items to allow normal processing
                    }
                }

                binding.toolbar.setNavigationOnClickListener {
                    onToolbarIconClicked()
                }
            }

            R.id.signInFragment -> {
                binding.toolbar.menu.removeItem(R.id.exit_menu_item)
                binding.toolbar.setNavigationIcon(null) // Remove any specific navigation icon
                binding.toolbar.setOnMenuItemClickListener(null)
            }

            R.id.forgetPasswordFragment -> {
                binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
                binding.toolbar.setOnMenuItemClickListener(null)
                binding.toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
            R.id.signUpFragment -> {
                binding.toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }

            else -> {
                // Handle other fragments or do nothing if no specific action is required
            }
        }
    }

    private fun onExitMenuClicked() {
        sharedViewModel.userLogOut()
    }

    override fun onToolbarIconClicked() {
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

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance() //TimeZone.getDefault()

                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                selectedCalendar.set(Calendar.HOUR_OF_DAY, 0) // Set hour to 0
                selectedCalendar.set(Calendar.MINUTE, 0) // Set minute to 0
                selectedCalendar.set(Calendar.SECOND, 0) // Set second to 0
                selectedCalendar.set(Calendar.MILLISECOND, 0) // Set millisecond to 0

                val timestamp = selectedCalendar.timeInMillis
                sharedViewModel.setSelectedDate(timestamp)

            }, year, month, day
        )

        datePickerDialog.show()
    }

}
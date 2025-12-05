package com.weatherforecast.presentation.weather

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.weatherforecast.domain.model.WeatherInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val state = viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            val granted = perms[android.Manifest.permission.ACCESS_FINE_LOCATION] == true
            if (granted) viewModel.getWeatherByLocation()
        }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Weather Forecast") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    permissionLauncher.launch(
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "My Location")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            SearchBar(
                query = state.query,
                onQueryChanged = viewModel::onQueryChange,
                onSearch = viewModel::search
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }

                state.weather != null -> WeatherCard(state.weather!!)

                else -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Search a city or use location button")
                }
            }
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit, onSearch: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Enter city name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() })
        )

        Spacer(Modifier.width(8.dp))

        Button(onClick = onSearch) {
            Text("Search")
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherInfo) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(weather.city, style = MaterialTheme.typography.titleLarge)
                    Text(
                        weather.description.replaceFirstChar { it.uppercaseChar() },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                weather.icon?.let { icon ->
                    val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"
                    AsyncImage(
                        model = iconUrl,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Text(
                    "${weather.temperature}°C",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.width(24.dp))

                Column {
                    Text("Feels like: ${weather.feelsLike ?: "-"}°C")
                    Text("Humidity: ${weather.humidity ?: "-"}%")
                    Text("Wind: ${weather.windSpeed ?: "-"} m/s")
                }
            }
        }
    }
}

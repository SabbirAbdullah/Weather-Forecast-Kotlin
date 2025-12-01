import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.weatherforecast.domain.model.WeatherInfo
import com.weatherforecast.presentation.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {

    val state = viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Forecast") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                state.weather?.let { weather ->
                    WeatherCard(weather)
                } ?: Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        "Search a city to see weather",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String)->Unit, onSearch: ()->Unit) {

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

import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = state.error) {
        state.error?.let { scaffoldState.snackbarHostState.showSnackbar(it); viewModel.clearError() }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Weather") }, backgroundColor = MaterialTheme.colors.primary)
    }, scaffoldState = scaffoldState) { padding ->
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                state.weather?.let { weather ->
                    WeatherCard(weather = weather)
                } ?: run {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                        Text("Search a city to see weather", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String)->Unit, onSearch: ()->Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = weather.city, style = MaterialTheme.typography.h6)
                    Text(text = weather.description.replaceFirstChar { it.uppercaseChar() }, style = MaterialTheme.typography.body2)
                }

                // Weather icon from OpenWeather: https://openweathermap.org/img/wn/{icon}@2x.png
                weather.icon?.let { icon ->
                    val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"
                    Image(
                        painter = rememberAsyncImagePainter(iconUrl),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(text = "${weather.temperature}°C", style = MaterialTheme.typography.h4)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("Feels like: ${weather.feelsLike ?: "-"}°C")
                    Text("Humidity: ${weather.humidity ?: "-"}%")
                    Text("Wind: ${weather.windSpeed ?: "-"} m/s")
                }
            }
        }
    }
}

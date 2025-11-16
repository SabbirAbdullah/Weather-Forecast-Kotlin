@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun onQueryChange(q: String) { state = state.copy(query = q) }

    fun search() {
        val city = state.query.trim()
        if (city.isEmpty()) {
            state = state.copy(error = "Please enter a city")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                val info = getWeatherUseCase(city)
                state = state.copy(isLoading = false, weather = info)
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.localizedMessage ?: "An error occurred")
            }
        }
    }

    fun clearError() { state = state.copy(error = null) }
}

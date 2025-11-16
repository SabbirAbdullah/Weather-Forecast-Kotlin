@Composable
fun WeatherTheme(content: @Composable ()->Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Purple500,
            primaryVariant = Purple200,
            secondary = Teal200
        ),
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

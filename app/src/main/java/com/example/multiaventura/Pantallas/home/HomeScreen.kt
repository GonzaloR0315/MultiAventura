// Definición del paquete donde se encuentra la pantalla de inicio de la aplicación
package com.example.multiaventura.Pantallas.home

// Importaciones necesarias
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.multiaventura.R
import com.example.multiaventura.model.Actividad
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

// Composable para la pantalla de inicio
@Composable
fun Home(
    navController: NavController
){
    // ViewModel para la pantalla de inicio
    val viewModel: HomeViewModel = viewModel()

    // Recolectar el estado de la interfaz de usuario desde el ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Scaffold para la pantalla
    Scaffold (
        // Barra superior de la pantalla
        topBar = {
            ActividadesAppBar(
                isShowingListPage = uiState.isShowingListPage,
                onBackPressed = { viewModel.navigateToListPage() }
            )
        }
    ){innerPadding ->
        // Contenido principal de la pantalla
        if (uiState.isShowingListPage){
            // Mostrar la lista de actividades si la página de lista está activa
            ActividadesList(
                actividades = uiState.actividadesList,
                onClick = {
                    viewModel.updateCurrentActividad(it)
                    viewModel.navigateToDetailPage()
                },
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                contentPadding = innerPadding,
            )
        }else{
            // Mostrar los detalles de la actividad seleccionada si la página de detalle está activa
            ActividadDetail(
                selectedActividad = uiState.currentActividad,
                contentPadding = innerPadding,
                onBackPressed = {
                    viewModel.navigateToListPage()
                }
            )
        }

    }
}

// Composable para la barra de la aplicación
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActividadesAppBar(
    onBackPressed: () -> Unit,
    isShowingListPage: Boolean,
    modifier: Modifier = Modifier
){
    val isShowingDetailPage =  !isShowingListPage

    // Barra superior con título dinámico y botón de navegación de retroceso
    TopAppBar(
        title = {
            Text(
                text =
                if (isShowingDetailPage){
                    stringResource(R.string.info_actividades)
                }else{
                    stringResource(R.string.actividades)
                },
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = if (isShowingDetailPage){
            {
                IconButton(onClick = onBackPressed){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.volver)
                    )
                }
            }
        } else {
            { Box {} }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
    )
}


// Composable para un elemento de la lista de actividades
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActividadesListItem(
    actividad: Actividad,
    onItemClick: (Actividad) -> Unit,
    modifier: Modifier = Modifier
) {
    // Tarjeta que muestra la actividad
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = { onItemClick(actividad) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(R.dimen.card_image_height))
        ) {
            // Imagen de la actividad
            ActividadesListImageItem(
                actividad = actividad,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            // Detalles de la actividad
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                // Título de la actividad
                Text(
                    text = stringResource(actividad.titleResourceId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                // Número de personas
                Row {
                    Text(
                        text = pluralStringResource(
                            R.plurals.personas,
                            actividad.playerCount,
                            actividad.playerCount
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

// Composable para mostrar la imagen de una actividad en la lista
@Composable
private fun ActividadesListImageItem(actividad: Actividad, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        // Imagen de la actividad
        Image(
            painter = painterResource(actividad.imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
    }
}

// Composable para la lista de actividades
@Composable
private fun ActividadesList(
    actividades: List<Actividad>,
    onClick: (Actividad) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // Lista de actividades
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
    ) {
        items(actividades, key = { it.id }) { actividad ->
            // Elemento de la lista que muestra una actividad
            ActividadesListItem(
                actividad = actividad,
                onItemClick = onClick
            )
        }
    }
}

// Composable para mostrar los detalles de una actividad
@Composable
private fun ActividadDetail(
    selectedActividad: Actividad,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    // Manejar el evento de retroceso para volver a la lista de actividades
    BackHandler {
        onBackPressed()
    }
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            // Imagen de la actividad
            Box {
                Box {
                    Image(
                        painter = painterResource(selectedActividad.actividadImageBanner),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                // Texto sobre la imagen
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(selectedActividad.titleResourceId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    // Número de personas
                    Row(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(
                            text = pluralStringResource(
                                R.plurals.personas,
                                selectedActividad.playerCount,
                                selectedActividad.playerCount),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                    }
                }
            }
            // Detalles de la actividad
            Text(
                text = stringResource(selectedActividad.actividadDetails),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
            // Botón para reservar la actividad
            ReservaButton(selectedActividad, viewModel())
            // Mapa que muestra la ubicación de la actividad
            Box(modifier = Modifier.height(600.dp)){
                val marker = LatLng(selectedActividad.lat, selectedActividad.lng)
                val cameraPosition = rememberCameraPositionState{ position = CameraPosition.fromLatLngZoom(marker, 15f)}
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    properties = MapProperties(mapType = MapType.HYBRID),
                    cameraPositionState = cameraPosition,
                    uiSettings = MapUiSettings(zoomControlsEnabled = false)
                ){
                    Marker(position = marker, title = stringResource(selectedActividad.titleResourceId))
                }
            }
        }
    }
}

// Composable para el botón de reserva de la actividad
@Composable
fun ReservaButton(
    selectedActividad: Actividad,
    viewModel: HomeViewModel,
) {
    // Variables para los datos de reserva
    var nomActividad = ""
    nomActividad = stringResource(selectedActividad.titleResourceId)
    var numPersonas by remember { mutableStateOf(1) }
    var telefono by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableStateOf<String?>(null) }

    // Variable para controlar la visibilidad del AlertDialog de confirmación
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    // Variable para el número máximo de personas permitido
    val maxPersonas = selectedActividad.playerCount

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de entrada para el número de personas
        OutlinedTextField(
            value = numPersonas.toString(),
            onValueChange = { newValue ->
                val newNum = newValue.toIntOrNull() ?: 0
                numPersonas = when {
                    newNum < 0 -> 1
                    newNum > maxPersonas -> maxPersonas
                    else -> newNum
                }
            },
            label = { Text("Número de personas (máximo: $maxPersonas)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(8.dp)
        )

        // Campo de entrada para el teléfono
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.padding(8.dp)
        )

        // Campo de entrada para seleccionar la fecha
        OutlinedTextField(
            value = fechaSeleccionada ?: "",
            onValueChange = { fechaSeleccionada = it },
            label = { Text("Fecha (dd-MM-yyyy)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(8.dp)
        )

        // Botón para confirmar la reserva
        Button(
            onClick = {
                // Mostrar el AlertDialog de confirmación
                mostrarConfirmacion = true
            },
            content = { Text(stringResource(R.string.confirmarRes)) },
            modifier = Modifier.padding(8.dp)
        )
    }

    // Mostrar el AlertDialog de confirmación si la variable mostrarConfirmacion es true
    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = {
                // Ocultar el AlertDialog si se presiona fuera de él
                mostrarConfirmacion = false
            },
            title = { Text(stringResource(R.string.confirmarRes)) },
            text = { Text(stringResource(R.string.preguntaConfirmarRes)) },
            confirmButton = {
                Button(
                    onClick = {
                        // Realizar la reserva si se confirma
                        if (fechaSeleccionada != null) {
                            viewModel.crearReservaFirestore(
                                nomActividad,
                                fechaSeleccionada!!,
                                numPersonas,
                                telefono
                            )
                        }
                        mostrarConfirmacion = false // Ocultar el AlertDialog
                    }
                ) {
                    Text(stringResource(R.string.si))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Cancelar la reserva si se cancela la confirmación
                        mostrarConfirmacion = false // Ocultar el AlertDialog
                    }
                ) {
                    Text(stringResource(R.string.cancelar))
                }
            }
        )
    }
}

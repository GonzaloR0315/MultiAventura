package com.example.multiaventura.Pantallas.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.multiaventura.R
import com.example.multiaventura.model.Actividad
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun Home(
    navController: NavController
){
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            ActividadesAppBar(
                isShowingListPage = uiState.isShowingListPage,
                onBackPressed = { viewModel.navigateToListPage() }
            )
        }
    ){innerPadding ->
        if (uiState.isShowingListPage){
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActividadesAppBar(
    onBackPressed: () -> Unit,
    isShowingListPage: Boolean,
    modifier: Modifier = Modifier
){
    val isShowingDetailPage =  !isShowingListPage
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActividadesListItem(
    actividad: Actividad,
    onItemClick: (Actividad) -> Unit,
    modifier: Modifier = Modifier
) {
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
            ActividadesListImageItem(
                actividad = actividad,
                modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(actividad.titleResourceId),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
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

@Composable
private fun ActividadesListImageItem(actividad: Actividad, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(actividad.imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
    }
}
@Composable
private fun ActividadesList(
    actividades: List<Actividad>,
    onClick: (Actividad) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium)),
    ) {
        items(actividades, key = { it.id }) { actividad ->
            ActividadesListItem(
                actividad = actividad,
                onItemClick = onClick
            )
        }
    }
}


@Composable
private fun ActividadDetail(
    selectedActividad: Actividad,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
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
            Text(
                text = stringResource(selectedActividad.actividadDetails),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
            ReservaButton(selectedActividad, viewModel())
            Box(modifier = Modifier.height(600.dp)){
                val marker = LatLng(selectedActividad.lat, selectedActividad.lng)
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    properties = MapProperties(mapType = MapType.HYBRID),
                    uiSettings = MapUiSettings(zoomControlsEnabled = false)
                    ){
                    Marker(position = marker, title = stringResource(selectedActividad.titleResourceId))

                }
            }
        }
    }
}
@Composable
fun ReservaButton(
    selectedActividad: Actividad,
    viewModel: HomeViewModel,
) {
    var nomActividad = ""
    nomActividad = stringResource(selectedActividad.titleResourceId)

    // Variable para controlar la visibilidad del AlertDialog de confirmación
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                // Mostrar el AlertDialog de confirmación
                mostrarConfirmacion = true
            },
            content = { Text(stringResource(R.string.confirmarRes)) }
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
                        viewModel.crearReservaFirestore(
                            nomActividad,
                            SimpleDateFormat("yyyy-MM-dd").format(Date())
                        )
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


@Composable
fun MyGoogleMaps() {

}








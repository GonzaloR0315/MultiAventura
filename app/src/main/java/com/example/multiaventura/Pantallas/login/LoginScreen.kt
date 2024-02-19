// Definición del paquete donde se encuentra la pantalla de inicio de sesión
package com.example.multiaventura.Pantallas.login

// Importaciones necesarias para la pantalla de inicio de sesión
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.multiaventura.Navegacion.MultiAventuraScreens
import com.example.multiaventura.R

// Pantalla de inicio de sesión
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    // Variable para alternar entre el formulario de inicio de sesión y el de registro
    val showLoginForm = rememberSaveable{
        mutableStateOf(true)
    }
    Surface(modifier = Modifier
        .fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            if(showLoginForm.value){
                // Formulario de inicio de sesión
                Text(stringResource(R.string.iniciaSesion))
                UserForm(
                    isCreateAccount = false
                ){
                    // Callback para iniciar sesión
                        email, password ->
                    Log.d("Login", "Me logueo con $email y $password" )
                    // Llamar al ViewModel para iniciar sesión
                    viewModel.signInWithEmailAndPassword(email, password){
                        navController.navigate(MultiAventuraScreens.HomeScreen.name)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                // Alternar entre el formulario de inicio de sesión y el de registro
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    val pregunta =
                        if (showLoginForm.value) stringResource(R.string.noTienes)
                        else stringResource(R.string.yaTienes)
                    val accion =
                        if (showLoginForm.value) stringResource(R.string.registrate)
                        else stringResource(R.string.iniciaSesion)
                    Text(text = pregunta)
                    // Cambiar entre el formulario de inicio de sesión y el de registro
                    Text(
                        text = accion,
                        modifier = Modifier
                            .clickable{ showLoginForm.value = !showLoginForm.value}
                            .padding(start = 5.dp),
                        color = Color.Blue
                    )
                }
            }
            else{
                // Formulario de registro
                Text(stringResource(R.string.crearCuenta))
                UserForm(
                    isCreateAccount = true
                ){
                    // Callback para crear cuenta
                        email, password ->
                    Log.d("Create", "Creo la cuenta $email y $password" )
                    // Llamar al ViewModel para crear usuario
                    viewModel.createUserWithEmailAndPass(email, password){
                        navController.navigate(MultiAventuraScreens.HomeScreen.name)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                // Alternar entre el formulario de inicio de sesión y el de registro
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    val pregunta =
                        if (showLoginForm.value) stringResource(R.string.noTienes)
                        else stringResource(R.string.yaTienes)
                    val accion =
                        if (showLoginForm.value) stringResource(R.string.registrate)
                        else stringResource(R.string.iniciaSesion)
                    Text(text = pregunta)
                    // Cambiar entre el formulario de inicio de sesión y el de registro
                    Text(
                        text = accion,
                        modifier = Modifier
                            .clickable{ showLoginForm.value = !showLoginForm.value}
                            .padding(start = 5.dp),
                        color = Color.Blue
                    )
                }
            }
        }
    }
}

// Componente del formulario de usuario
@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, pwd ->}
) {
    // Estado para el email y la contraseña
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable {
        mutableStateOf(false)

    }
    // Validar si el email y la contraseña no están vacíos
    val valido = remember(email.value, password.value){
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty()
    }
    // Ocultar el teclado al hacer clic en el botón de iniciar sesión o registrar
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        // Entrada de email
        EmailInput(
            emailState = email
        )
        // Entrada de contraseña
        PasswordInput(
            passwordState = password,
            labelId = stringResource(R.string.clave),
            passwordVisible = passwordVisible
        )
        // Botón de enviar formulario
        SubmintButton(
            textId =
            if (isCreateAccount) stringResource(R.string.crearCuenta)
            else stringResource(R.string.iniciaSesion),
            inputValido = valido
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

// Botón de enviar formulario
@Composable
fun SubmintButton(
    textId: String,
    inputValido: Boolean,
    onClick: () ->Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido
    ){
        Text(
            text = textId,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

// Entrada de email
@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId : String = stringResource(R.string.email)
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

// Entrada de contraseña
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
) {
    // Mostrar o ocultar la contraseña
    val visualTransformation =
        if (passwordVisible.value)
            VisualTransformation.None
        else
            PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = { Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()){
                PasswordVisibleIcon(passwordVisible)
            }
            else null
        }
    )
}

// Entrada de texto genérica
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = {Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

// Icono para mostrar u ocultar la contraseña
@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>
) {
    val image =
        if(passwordVisible.value)
            Icons.Default.VisibilityOff
        else
            Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )

    }
}

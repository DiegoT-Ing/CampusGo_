package com.example.campusgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversityAppTheme {
                UniversityApp()
            }
        }
    }
}

@Composable
fun UniversityAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1976D2),
            secondary = Color(0xFF42A5F5),
            background = Color(0xFFF5F5F5),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.Black,
            onSurface = Color.Black
        ),
        content = content
    )
}

@Composable
fun UniversityApp() {
    val navController = rememberNavController()
    var currentUser by remember { mutableStateOf<User?>(null) }

    NavHost(
        navController = navController,
        startDestination = if (currentUser == null) "login" else "home"
    ) {
        composable("login") {
            LoginScreen(
                onLogin = { user ->
                    currentUser = user
                    navController.navigate("home")
                }
            )
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                user = currentUser
            )
        }
        composable("schedules") {
            SchedulesScreen(navController)
        }
        composable("cafeteria") {
            CafeteriaScreen(navController)
        }
        composable("grades") {
            GradesScreen(navController)
        }
        composable("chat") {
            ChatScreen(navController)
        }
    }
}

// Modelo de datos
data class User(
    val id: String,
    val name: String,
    val career: String,
    val semester: Int
)

data class MenuItem(
    val id: String,
    val name: String,
    val price: Double,
    val description: String
)

data class Grade(
    val subject: String,
    val grade: Double,
    val semester: String
)

data class Schedule(
    val subject: String,
    val time: String,
    val room: String,
    val professor: String
)

data class ChatMessage(
    val user: String,
    val message: String,
    val timestamp: String
)

// Pantalla de Login
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLogin: (User) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1976D2))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.School,
            contentDescription = "Universidad",
            tint = Color.White,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Universidad App",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Simulación de login
                onLogin(User("123", "Juan Pérez", "Ingeniería en Sistemas", 6))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF1976D2)
            )
        ) {
            Text("Iniciar Sesión", fontSize = 16.sp)
        }
    }
}

// Pantalla Principal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, user: User?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1976D2))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    "Bienvenido, ${user?.name ?: "Usuario"}",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${user?.career} - ${user?.semester}° Semestre",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
        }

        // Menú de opciones
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                MenuCard(
                    title = "Horarios",
                    description = "Consulta tus horarios de clases",
                    icon = Icons.Default.Schedule,
                    onClick = { navController.navigate("schedules") }
                )
            }

            item {
                MenuCard(
                    title = "Cafetería",
                    description = "Menú y pedidos de comida",
                    icon = Icons.Default.Restaurant,
                    onClick = { navController.navigate("cafeteria") }
                )
            }

            item {
                MenuCard(
                    title = "Calificaciones",
                    description = "Consulta tus calificaciones",
                    icon = Icons.Default.Assessment,
                    onClick = { navController.navigate("grades") }
                )
            }

            item {
                MenuCard(
                    title = "Chat por Carrera",
                    description = "Interactúa con otros estudiantes",
                    icon = Icons.AutoMirrored.Filled.Chat,
                    onClick = { navController.navigate("chat") }
                )
            }
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// Pantalla de Horarios
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesScreen(navController: NavController) {
    val schedules = listOf(
        Schedule("Programación Móvil", "08:00 - 10:00", "Aula 101", "Dr. García"),
        Schedule("Base de Datos", "10:00 - 12:00", "Aula 102", "Dra. López"),
        Schedule("Ingeniería de Software", "14:00 - 16:00", "Aula 103", "Dr. Martínez"),
        Schedule("Redes", "16:00 - 18:00", "Aula 104", "Ing. Rodríguez")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        TopAppBar(
            title = { Text("Horarios") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1976D2),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(schedules) { schedule ->
                ScheduleCard(schedule)
            }
        }
    }
}

@Composable
fun ScheduleCard(schedule: Schedule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                schedule.subject,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )
            Text(
                "Horario: ${schedule.time}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                "Aula: ${schedule.room}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                "Profesor: ${schedule.professor}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

// Pantalla de Cafetería
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeteriaScreen(navController: NavController) {
    val menuItems = listOf(
        MenuItem("1", "Hamburguesa Clásica", 85.0, "Carne, lechuga, tomate, queso"),
        MenuItem("2", "Quesadillas", 45.0, "Tortilla de harina con queso"),
        MenuItem("3", "Tacos de Pastor", 60.0, "3 tacos con piña y cebolla"),
        MenuItem("4", "Ensalada César", 70.0, "Lechuga, pollo, aderezo césar"),
        MenuItem("5", "Refresco", 25.0, "Coca Cola, Sprite, Fanta"),
        MenuItem("6", "Agua Natural", 15.0, "Agua purificada 500ml")
    )

    var cart by remember { mutableStateOf<List<MenuItem>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        TopAppBar(
            title = { Text("Cafetería") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1976D2),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(menuItems) { item ->
                MenuItemCard(
                    item = item,
                    onAddToCart = { cart = cart + item }
                )
            }
        }

        // Carrito
        if (cart.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1976D2))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Carrito (${cart.size} items)",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Total: $${cart.sumOf { it.price }}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            cart = emptyList()
                            // Aquí iría la lógica para procesar el pedido
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF1976D2)
                        )
                    ) {
                        Text("Realizar Pedido")
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItem, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    item.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    "$${item.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }

            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Text("Agregar")
            }
        }
    }
}

// Pantalla de Calificaciones
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesScreen(navController: NavController) {
    val grades = listOf(
        Grade("Programación Móvil", 9.5, "6° Semestre"),
        Grade("Base de Datos", 8.8, "6° Semestre"),
        Grade("Ingeniería de Software", 9.2, "6° Semestre"),
        Grade("Redes", 8.5, "6° Semestre"),
        Grade("Algoritmos", 9.0, "5° Semestre"),
        Grade("Programación Web", 9.3, "5° Semestre")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        TopAppBar(
            title = { Text("Calificaciones") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1976D2),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        // Promedio general
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1976D2))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Promedio General",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    grades.map { it.grade }.average().format(2),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(grades) { grade ->
                GradeCard(grade)
            }
        }
    }
}

@Composable
fun GradeCard(grade: Grade) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    grade.subject,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    grade.semester,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Text(
                grade.grade.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = when {
                    grade.grade >= 9.0 -> Color(0xFF4CAF50)
                    grade.grade >= 8.0 -> Color(0xFF2196F3)
                    grade.grade >= 7.0 -> Color(0xFFFF9800)
                    else -> Color(0xFFF44336)
                }
            )
        }
    }
}

// Pantalla de Chat
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    var messages by remember { mutableStateOf(listOf(
        ChatMessage("Ana García", "¡Hola! ¿Alguien tiene las notas de programación?", "10:30"),
        ChatMessage("Carlos López", "Sí, te las envío por privado", "10:32"),
        ChatMessage("María Jiménez", "¿Cuándo es el examen de bases de datos?", "10:35"),
        ChatMessage("José Martín", "El próximo viernes a las 2pm", "10:37")
    )) }
    var newMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        TopAppBar(
            title = { Text("Chat - Ing. Sistemas") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1976D2),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageCard(message)
            }
        }

        // Campo de entrada de mensaje
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                placeholder = { Text("Escribe un mensaje...") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newMessage.isNotBlank()) {
                        messages = messages + ChatMessage(
                            "Juan Pérez",
                            newMessage,
                            "Ahora"
                        )
                        newMessage = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        }
    }
}

@Composable
fun MessageCard(message: ChatMessage) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    message.user,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
                Text(
                    message.timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                message.message,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

// Función de extensión para formatear decimales
fun Double.format(digits: Int) = "%.${digits}f".format(this)
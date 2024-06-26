package com.example.app_lemania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_lemania.ui.theme.AppleManiaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppleManiaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    var score by rememberSaveable { mutableStateOf(0) }
    var creditsPerClick by rememberSaveable { mutableStateOf(1) }
    var autoclickers by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)  // Delay de 1 segundo
            score += autoclickers
        }
    }

    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainContent(navController, score, creditsPerClick, autoclickers) { newScore -> score = newScore }
        }
        composable("shop") {
            ShopScreen(navController, score, creditsPerClick, autoclickers) { newCreditsPerClick, newScore, newAutoclickers ->
                creditsPerClick = newCreditsPerClick
                score = newScore
                autoclickers = newAutoclickers
            }
        }
        composable("menu") {
            MenuActivity()
        }
    }
}
var confirmaMenu = false
@Composable
fun MainContent(navController: NavHostController, score: Int, creditsPerClick: Int, autoclickers: Int, updateScore: (Int) -> Unit) {
    // Estado para controlar a visibilidade do botão aleatório
    var showRandomButton by remember { mutableStateOf(false) }

    // LaunchedEffect para exibir o botão aleatório com 40% de chance a cada 10 segundos e para ver se tenho que ir para o menu ou não
    LaunchedEffect(Unit) {
        if (!confirmaMenu) {
            confirmaMenu = true
            navController.navigate("menu")
        }
        while (true) {
            delay(10000L) // Delay de 10 segundos
            if (Random.nextFloat() < 0.4) { // 40% de chance
                showRandomButton = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.navigate("shop") },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Text("Loja de upgrades")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Créditos: $score", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.apple_imagem),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clickable {
                        updateScore(score + creditsPerClick)
                    },
                contentScale = ContentScale.Crop
            )

            // Botão aleatório para adicionar creditos aleatorios de 50 a 500 créditos
            if (showRandomButton) {
                RandomButton {
                    updateScore(score + Random.nextInt(50, 500))
                    showRandomButton = false // Esconde o botão após ser clicado
                }
            }

        }
    }
}
@Composable
fun RandomButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text("Surpresa!")
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppleManiaTheme {
        AppNavigator()
    }
}

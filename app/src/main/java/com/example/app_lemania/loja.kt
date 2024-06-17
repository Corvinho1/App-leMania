package com.example.app_lemania

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ShopScreen(navController: NavHostController, score: Int, creditsPerClick: Int, autoclickers: Int, updateValues: (Int, Int, Int) -> Unit) {
    val upgradeClickPrice = 50 * creditsPerClick
    val upgradeAutoClickerPrice = 100 * (autoclickers + 1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loja de Upgrades", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Preço do upgrade de clique: $upgradeClickPrice créditos", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (score >= upgradeClickPrice) {
                    updateValues(creditsPerClick + 1, score - upgradeClickPrice, autoclickers)
                }
            }
        ) {
            Text("Aumentar créditos por clique")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Preço do upgrade de autoclicker: $upgradeAutoClickerPrice créditos", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (score >= upgradeAutoClickerPrice) {
                    updateValues(creditsPerClick, score - upgradeAutoClickerPrice, autoclickers + 1)
                }
            }
        ) {
            Text("Comprar Autoclicker")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Créditos: $score", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        Text(text = "Créditos por clique: $creditsPerClick", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        Text(text = "Autoclickers: $autoclickers", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("main") }) {
            Text("Voltar")
        }
    }
}

package br.senai.sp.jandira.clientesapp.screens.cliente.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import br.senai.sp.jandira.clientesapp.ui.theme.ClientesAppTheme

@Composable
fun BotaoFlutuante(controleNavegacao: NavHostController?) {
    FloatingActionButton(
        onClick = {
            controleNavegacao!!.navigate("cadastro")
        },
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Botão Adicionar"
        )
    }
}

@Preview
@Composable
private fun BotaoFlutuantePreview() {
    ClientesAppTheme {
        BotaoFlutuante(null)
    }
}
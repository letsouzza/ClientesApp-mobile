package br.senai.sp.jandira.clientesapp.screens.cliente

import android.content.res.Configuration
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.clientesapp.screens.cliente.components.BarraDeTitulo
import br.senai.sp.jandira.clientesapp.screens.cliente.components.BarraInferior
import br.senai.sp.jandira.clientesapp.screens.cliente.components.BotaoFlutuante
import br.senai.sp.jandira.clientesapp.screens.cliente.components.Conteudo
import br.senai.sp.jandira.clientesapp.ui.theme.ClientesAppTheme

@Composable
fun ListaDeClientes(modifier: Modifier = Modifier) {

    var controleNavegacao = rememberNavController()

    Scaffold (
        topBar = {
            BarraDeTitulo()
        },
        bottomBar = {
            BarraInferior(controleNavegacao)
        },
        floatingActionButton = {
            BotaoFlutuante(controleNavegacao)
        },
        content = { padding ->
            NavHost(
                navController = controleNavegacao,
                startDestination = "conteudo"
            ) {
                composable(route = "conteudo") { Conteudo(padding) }
                composable(route = "cadastro") { ClienteForm(padding, controleNavegacao) }
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListaDeClientesPreview(){
    ClientesAppTheme {
        ListaDeClientes()
    }
}
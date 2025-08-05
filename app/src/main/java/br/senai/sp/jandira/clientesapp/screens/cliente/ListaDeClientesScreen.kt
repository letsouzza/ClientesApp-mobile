package br.senai.sp.jandira.clientesapp.screens.cliente

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.clientesapp.screens.cliente.components.BarraDeTitulo
import br.senai.sp.jandira.clientesapp.screens.cliente.components.BarraInferior
import br.senai.sp.jandira.clientesapp.screens.cliente.components.BotaoFlutuante
import br.senai.sp.jandira.clientesapp.screens.cliente.components.Conteudo
import br.senai.sp.jandira.clientesapp.ui.theme.ClientesAppTheme

@Composable
fun ListaDeClientes(modifier: Modifier = Modifier) {
    Scaffold (
        topBar = {
            BarraDeTitulo()
        },
        bottomBar = {
            BarraInferior()
        },
        floatingActionButton = {
            BotaoFlutuante()
        },
        content = { it ->
            Conteudo(it)
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
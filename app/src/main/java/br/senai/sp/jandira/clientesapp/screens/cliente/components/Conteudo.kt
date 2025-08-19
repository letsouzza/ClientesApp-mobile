package br.senai.sp.jandira.clientesapp.screens.cliente.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.clientesapp.model.Cliente
import br.senai.sp.jandira.clientesapp.service.ClienteService
import br.senai.sp.jandira.clientesapp.service.Conexao
import br.senai.sp.jandira.clientesapp.ui.theme.ClientesAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

@Composable
fun Conteudo(paddingValues: PaddingValues, controleNavegacao: NavHostController?) {

    val clienteApi = Conexao().getClienteService()

    var clientes by remember { mutableStateOf(listOf<Cliente>()) }


    LaunchedEffect(Dispatchers.IO) {
        clientes = clienteApi.listarTodos().await()
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ){
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Lista de Clientes"
            )
        }
        LazyColumn {
            items(clientes) { cliente ->
                CardCliente(cliente, clienteApi, controleNavegacao, paddingValues)
            }
        }
    }
}

@Composable
private fun CardCliente(
    cliente: Cliente,
    clienteApi: ClienteService,
    controleNavegacao: NavHostController?,
    paddingValues: PaddingValues,
) {

    var mostrarMensagemDeletar by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = cliente.nome)
                Text(text = cliente.email)
            }
            IconButton(
                onClick = {
                    mostrarMensagemDeletar = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
        if (mostrarMensagemDeletar) {
            AlertDialog(
                onDismissRequest = {
                    mostrarMensagemDeletar = false
                },
                title = {
                    Text(text = "Excluir cliente")
                },
                text = {
                    Text(text = "Tem certeza que deseja excluir o usuário ${cliente.nome}?")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = ""
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            GlobalScope.launch(Dispatchers.IO){
                                val excluirCliente = clienteApi.deletarCliente(cliente).await()
                                mostrarMensagemDeletar = true
                            }
                            controleNavegacao!!.navigate("conteudo")
                        }
                    ) {
                        Text(text = "Sim")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            mostrarMensagemDeletar = false
                        }
                    ) {
                        Text(text = "Não")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun ConteudoPreview() {
    ClientesAppTheme {
        Conteudo(PaddingValues(16.dp), controleNavegacao = null)
    }
}
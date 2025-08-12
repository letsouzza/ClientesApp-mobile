package br.senai.sp.jandira.clientesapp.screens.cliente

import android.content.res.Configuration
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.clientesapp.model.Cliente
import br.senai.sp.jandira.clientesapp.service.Conexao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

@Composable
fun ClienteForm(padding: PaddingValues, controleNavegacao: NavHostController?) {

    var nomeCliente by remember { mutableStateOf("") }
    var emailCliente by remember { mutableStateOf("") }
    var isNomeError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }

    fun validar(): Boolean{
        isNomeError = nomeCliente.length < 3
        isEmailError = !Patterns.EMAIL_ADDRESS.matcher(emailCliente).matches()
        return !isNomeError && !isEmailError
    }

    // Criar uma instância da conexão com a API
    val clienteApi = Conexao().getClienteService()

    // Box mais sofisticado
    Surface(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person"
                )
                Spacer(modifier = Modifier .width(16.dp))
                Text(
                    text = "Novo Cliente",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier .height(16.dp))
            OutlinedTextField(
                value = nomeCliente,
                onValueChange = {
                    nomeCliente = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(text = "Nome do Cliente:")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                isError = isNomeError,
                supportingText = {
                    if(isNomeError){
                        Text(text = "Nome é obrigatório e deve ter no mínimo 3 caracters")
                    }
                },
                trailingIcon = {
                    if(isEmailError){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "")
                    }
                }
            )
            OutlinedTextField(
                value = emailCliente,
                onValueChange = {
                    emailCliente = it
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                label = {
                    Text(text = "Email do Cliente:")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                isError = isEmailError,
                supportingText = {
                    if(isEmailError){
                        Text(text = "Email é obrigatório")
                    }
                },
                trailingIcon = {
                    if(isEmailError){
                        Icon(imageVector = Icons.Default.Info, contentDescription = "")
                    }
                }
            )
            Button(
                onClick = {
                    if (validar()){
                        val cliente = Cliente(
                            id = null,
                            nome = nomeCliente,
                            email = emailCliente
                        )
                        GlobalScope.launch(Dispatchers.IO){
                            val clienteNovo = clienteApi.cadastrarCliente(cliente).await()
                            println("**************${clienteNovo}")
                        }
                        controleNavegacao!!.navigate("conteudo")
                    }else{
                        println("********* Dados incorretos")
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Gravar Cliente")
            }
        }
    }

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ClienteFormPreview() {
    ClienteForm(padding = PaddingValues(0.dp), controleNavegacao = null)
}
package me.rerere.awara.ui.page.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.rerere.awara.R
import me.rerere.awara.ui.component.common.Button
import me.rerere.awara.ui.component.common.ButtonType
import me.rerere.awara.ui.component.common.Spin
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginPage(
    vm: LoginVM = koinViewModel()
) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.login_title),
                    )
                }
            )
        }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(padding)
                .width(IntrinsicSize.Min)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = {
                    Text(text = stringResource(R.string.login_user))
                }
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = stringResource(R.string.login_password))
                }
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                loading = true,
                type = ButtonType.FilledTonal
            ) {
                Text(text = stringResource(R.string.login_button))
            }

            var show by remember {
                mutableStateOf(true)
            }
            Spin(show = show) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Button(onClick = {
                        show = !show
                    }) {
                        Text(text = "Hello World!")
                    }
                }
            }
        }
    }
}
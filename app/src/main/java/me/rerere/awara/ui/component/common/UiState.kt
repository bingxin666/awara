package me.rerere.awara.ui.component.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import me.rerere.awara.R

sealed interface UiState {
    object Initial : UiState // 初始化状态, 什么都不显示
    object Empty : UiState  // 空状态, 表示没有数据
    object Loading : UiState // 加载状态, 表示正在加载数据
    object Success : UiState // 成功状态, 表示加载成功
    class Error(
        val throwable: Throwable? = null,
        val message: @Composable (() -> Unit)? = null
    ) : UiState
}

@Composable
fun UiStateBox(
    modifier: Modifier = Modifier,
    state: UiState,
    emptyStatus: @Composable BoxScope.() -> Unit = { EmptyStatus() },
    loadingStatus: @Composable BoxScope.() -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier
                .align(
                    Alignment.Center
                )
        )
    },
    errorStatus: @Composable BoxScope.(state: UiState.Error, onRetry: () -> Unit) -> Unit = { errorState, onRetry ->
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.connection_error))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        Column(
            modifier = modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .clickable {
                    onRetry()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            com.airbnb.lottie.compose.LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )

            errorState.message?.invoke()
        }
    },
    onErrorRetry: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        when (state) {
            UiState.Initial -> {
                /* nothing */
            }

            UiState.Empty -> {
                emptyStatus()
            }

            UiState.Loading -> {
                content()
                loadingStatus()
            }

            UiState.Success -> {
                content()
            }

            is UiState.Error -> {
                errorStatus(state, onErrorRetry)
            }
        }
    }
}
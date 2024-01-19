package me.rerere.awara.ui.component.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach

@Stable
class SelectOption<T>(
    val value: T,
    val label: @Composable () -> Unit
)

@Composable
fun <T> SelectButton(
    modifier: Modifier = Modifier,
    value: T,
    options: List<SelectOption<T>>,
    onValueChange: (T) -> Unit
) {
    var showDropdown by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        TextButton(
            onClick = { showDropdown = !showDropdown }
        ) {
            options.firstOrNull { it.value == value }?.label?.invoke() ?: Text("None")
            Icon(
                if (showDropdown) Icons.Outlined.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false },
        ) {
            options.fastForEach {
                DropdownMenuItem(
                    onClick = {
                        onValueChange(it.value)
                        showDropdown = false
                    },
                    text = {
                        it.label()
                    }
                )
            }
        }
    }
}
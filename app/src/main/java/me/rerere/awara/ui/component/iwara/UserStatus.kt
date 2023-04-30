package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import me.rerere.awara.data.entity.User
import me.rerere.awara.ui.component.common.Tag
import me.rerere.awara.ui.component.common.TagType
import java.time.Instant

@Composable
fun UserStatus(user: User) {
    val online by remember {
        derivedStateOf {
            Instant.now().epochSecond - (user.seenAt?.epochSecond ?: 0) < 60 * 5
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if(user.role != "user") {
            Tag(
                type = TagType.Info
            ) {
                Text(text = user.role)
            }
        }

        if(user.premium) {
            Tag(
                type = TagType.Info
            ) {
                Text(text = "Premium")
            }
        }

        Tag(
            type = if(online) TagType.Success else TagType.Error
        ) {
            Text(text = if(online) "Online" else "Offline")
        }
    }
}
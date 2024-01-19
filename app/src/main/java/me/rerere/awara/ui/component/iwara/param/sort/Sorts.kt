package me.rerere.awara.ui.component.iwara.param.sort

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R
import me.rerere.awara.ui.component.iwara.param.SortOption

val MediaSortOptions = listOf(
    SortOption(
        name = "date",
        label = {
            Text(stringResource(R.string.sort_date))
        },
        icon = {
            Icon(Icons.Outlined.CalendarMonth, null)
        }
    ),
    SortOption(
        name = "trending",
        label = {
            Text(stringResource(R.string.sort_trending))
        },
        icon = {
            Icon(Icons.Outlined.LocalFireDepartment, null)
        }
    ),
    SortOption(
        name = "popularity",
        label = {
            Text(stringResource(R.string.sort_popularity))
        },
        icon = {
            Icon(Icons.Outlined.Star, null)
        }
    ),
    SortOption(
        name = "views",
        label = {
            Text(stringResource(R.string.sort_views))
        },
        icon = {
            Icon(Icons.Outlined.RemoveRedEye, null)
        }
    ),
    SortOption(
        name = "likes",
        label = {
            Text(stringResource(R.string.sort_likes))
        },
        icon = {
            Icon(Icons.Outlined.Favorite, null)
        }
    )
)
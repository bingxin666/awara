package me.rerere.awara.ui.page.index

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VideoLabel
import androidx.compose.material3.Button
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.rerere.awara.R
import me.rerere.awara.data.source.APIResult
import me.rerere.awara.ui.LocalRouterProvider
import me.rerere.awara.ui.component.common.Avatar
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.ext.plus
import me.rerere.awara.ui.component.iwara.MediaCard
import me.rerere.awara.ui.hooks.rememberWindowSize
import org.koin.androidx.compose.koinViewModel

@Composable
fun IndexPage(
    vm: IndexVM = koinViewModel()
) {
    val windowSizeClass = rememberWindowSize()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded, WindowWidthSizeClass.Medium -> {
            IndexPageTabletLayout(vm)
        }

        WindowWidthSizeClass.Compact -> {
            IndexPagePhoneLayout(vm)
        }
    }
}

@Composable
private fun IndexPageTabletLayout(vm: IndexVM) {
    val router = LocalRouterProvider.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    Row {
        NavigationRail {
            navigations.forEachIndexed { index, navigationPoint ->
                NavigationRailItem(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                    icon = navigationPoint.icon,
                    label = navigationPoint.title
                )
            }
        }
        Box {
            Scaffold { padding ->
                HorizontalPager(
                    pageCount = 3,
                    state = pagerState,
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = WindowInsets.safeContent
                            .only(WindowInsetsSides.Top)
                            .asPaddingValues()
                            .plus(
                                PaddingValues(
                                    top = 56.dp // 硬编码docker search bar高度
                                )
                            )
                    ) {
                        items(100) {
                            Button(onClick = { /*TODO*/ }) {
                                Text("Hi")
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        WindowInsets.safeContent
                            .only(WindowInsetsSides.Top)
                            .asPaddingValues()
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DockedSearchBar(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                    },
                    active = active,
                    onActiveChange = { active = it },
                    leadingIcon = {
                        Avatar(
                            model = "https://iwara.tv/images/default-avatar.jpg",
                            onClick = {
                                router.navigate("login")
                            }
                        )
                    },
                    placeholder = {
                        Text("今天你想搜点什么?")
                    },
                    trailingIcon = {
                        if (active) {
                            IconButton(
                                onClick = { active = false }
                            ) {
                                Icon(Icons.Outlined.Close, "Close")
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    active = true
                                }
                            ) {
                                Icon(Icons.Outlined.Search, "Search")
                            }
                        }
                    }
                ) {}
            }
        }
    }
}

@Composable
private fun IndexPagePhoneLayout(vm: IndexVM) {
    val router = LocalRouterProvider.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchBar(
                    //modifier = Modifier,
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {

                    },
                    active = active,
                    onActiveChange = { active = it },
                    leadingIcon = {
                        Avatar(
                            model = "https://iwara.tv/images/default-avatar.jpg",
                            onClick = {
                                router.navigate("login")
                            }
                        )
                    },
                    placeholder = {
                        Text("今天你想搜点什么?")
                    },
                    trailingIcon = {
                        if (active) {
                            IconButton(
                                onClick = { active = false }
                            ) {
                                Icon(Icons.Outlined.Close, "Close")
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    active = true
                                }
                            ) {
                                Icon(Icons.Outlined.Search, "Search")
                            }
                        }
                    }
                ) {

                }
            }
        },
        bottomBar = {
            NavigationBar {
                navigations.forEachIndexed { index, navigationPoint ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        },
                        icon = navigationPoint.icon,
                        label = navigationPoint.title
                    )
                }
            }
        }
    ) { padding ->
        var counter by remember {
            mutableStateOf(0)
        }
        val videos = vm.state.videos?.results ?: emptyList()
        HorizontalPager(
            pageCount = 3,
            state = pagerState,
        ) {
            Spin(show = videos.isEmpty()) {
                LazyVerticalStaggeredGrid(
                    contentPadding = padding + PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize(),
                    columns = StaggeredGridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp
                ) {
                    item {
                        Button(onClick = { vm.test() }) {
                            Text(
                                "Test: " + me.rerere.awara.data.source.stringResource(
                                    APIResult.Error(
                                        404, "errors.notFound"
                                    )
                                )
                            )
                        }
                    }

                    items(videos) { video ->
                        MediaCard(media = video)
                    }
                }
            }
        }
    }
}


private data class NavigationPoint(
    val title: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
)

private val navigations = listOf(
    NavigationPoint(
        title = {
            Text(stringResource(R.string.index_nav_video))
        },
        icon = {
            Icon(Icons.Outlined.VideoLabel, "Videos")
        },
    ),
    NavigationPoint(
        title = {
            Text(stringResource(R.string.index_nav_image))
        },
        icon = {
            Icon(Icons.Outlined.Image, "Images")
        },
    ),
    NavigationPoint(
        title = {
            Text(stringResource(R.string.index_nav_video))
        },
        icon = {
            Icon(Icons.Outlined.Forum, "Forum")
        },
    ),
)
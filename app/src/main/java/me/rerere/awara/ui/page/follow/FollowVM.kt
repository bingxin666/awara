package me.rerere.awara.ui.page.follow

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.rerere.awara.data.entity.Follower
import me.rerere.awara.data.entity.Following
import me.rerere.awara.data.repo.MediaRepo
import me.rerere.awara.data.repo.UserRepo
import me.rerere.awara.data.source.onError
import me.rerere.awara.data.source.onException
import me.rerere.awara.data.source.onSuccess
import me.rerere.awara.data.source.runAPICatching
import me.rerere.awara.data.source.stringResource
import me.rerere.awara.ui.component.common.UiState

class FollowVM(
    savedStateHandle: SavedStateHandle,
    private val mediaRepo: MediaRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    private val userId = checkNotNull(savedStateHandle.get<String>("userId"))
    var state by mutableStateOf(FollowState())
        private set

    init {
        loadFollowing()
        loadFollower()
    }

    fun jumpToFollowingPage(page: Int) {
        state = state.copy(followingPage = page)
        loadFollowing()
    }

    fun loadFollowing() {
        viewModelScope.launch {
            state = state.copy(followingState = UiState.Loading)
            runAPICatching {
                userRepo.getFollowing(userId = userId, page = state.followingPage - 1)
            }.onSuccess {
                state = if(it.results.isEmpty()) {
                    state.copy(
                        followingState = UiState.Empty,
                        followingList = it.results,
                        followingCount = it.count
                    )
                } else {
                    state.copy(
                        followingState = UiState.Success,
                        followingList = it.results,
                        followingCount = it.count
                    )
                }
            }.onError {
                state = state.copy(followingState = UiState.Error(
                    message = {
                        Text(text = stringResource(error = it))
                    }
                ))
            }.onException {
                state = state.copy(followingState = UiState.Error(
                    message = {
                        Text(text = it.exception.localizedMessage ?: "Unknown Error")
                    }
                ))
            }
        }
    }

    fun jumpToFollowerPage(page: Int) {
        state = state.copy(followerPage = page)
        loadFollower()
    }

    fun loadFollower() {
        viewModelScope.launch {
            state = state.copy(followerState = UiState.Loading)
            runAPICatching {
                userRepo.getFollowers(userId = userId, page = state.followerPage - 1)
            }.onSuccess {
                state = if(it.results.isEmpty()) {
                    state.copy(
                        followerState = UiState.Empty,
                        followerList = it.results,
                        followerCount = it.count
                    )
                } else {
                    state.copy(
                        followerState = UiState.Success,
                        followerList = it.results,
                        followerCount = it.count
                    )
                }
            }.onError {
                state = state.copy(followerState = UiState.Error(
                    message = {
                        Text(text = stringResource(error = it))
                    }
                ))
            }.onException {
                state = state.copy(followerState = UiState.Error(
                    message = {
                        Text(text = it.exception.localizedMessage ?: "Unknown Error")
                    }
                ))
            }
        }
    }

    data class FollowState(
        val followingState: UiState = UiState.Initial,
        val followingPage: Int = 1,
        val followingCount: Int = 0,
        val followingList: List<Following> = emptyList(),
        val followerState: UiState = UiState.Initial,
        val followerPage: Int = 1,
        val followerCount: Int = 0,
        val followerList: List<Follower> = emptyList()
    )
}
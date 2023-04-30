package me.rerere.awara.data.source

import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface UpdateAPI {
    @GET("/")
    suspend fun checkUpdate(): VersionMeta
}

@Serializable
data class VersionMeta(
    val versionCode: Int,
    val versionName: String,
    val changes: String
)
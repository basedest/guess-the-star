
package com.basedest.guess_the_star.data

import retrofit2.http.GET

data class Celebrity(val image_url: String, val name: String)

interface CelebrityApi {
    @GET("/")
    suspend fun getCelebrities(): List<Celebrity>
}
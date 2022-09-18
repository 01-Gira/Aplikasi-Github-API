package network


import data.SearchUserResponse
import data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("users")
    @Headers("Authorization: Bearer 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")
    fun getListUsers(
    ): Call<List<UserResponse>>

    @GET("users/{user}")
    @Headers("Authorization: Bearer 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")
    fun getDetailUser(
        @Path("user") login: String
    ): Call<UserResponse>

    @GET("search/users")
    @Headers("Authorization: Bearer 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")
    fun getSearchUsers(
        @Query("q")
        query: String
    ): Call<SearchUserResponse>

    @GET("users/{user}/followers")
    @Headers("Authorization: Bearer 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")
    fun getFollowerUser(
        @Path("user")
        login: String
    ): Call<List<UserResponse>>

    @GET("users/{user}/following")
    @Headers("Authorization: Bearer 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")
    fun getFollowingUser(
        @Path("user")
        login: String
    ): Call<List<UserResponse>>
}
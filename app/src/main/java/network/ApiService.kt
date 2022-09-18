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
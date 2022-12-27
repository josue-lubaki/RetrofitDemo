package ca.josue.retrofitdemo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumService {

    // https://jsonplaceholder.typicode.com/albums
    @GET("/albums")
    suspend fun getAlbums(): Response<Album>

    // https://jsonplaceholder.typicode.com/albums?userId=1
    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId : Int): Response<Album>

    // https://jsonplaceholder.typicode.com/albums/1
    @GET("/albums/{id}")
    suspend fun getAlbum(@Path("id") albumId : Int): Response<AlbumItem>
}
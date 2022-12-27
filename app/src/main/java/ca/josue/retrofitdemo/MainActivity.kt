package ca.josue.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_view)

        val albumService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        val response : LiveData<Response<Album>> = liveData {
            val result = albumService.getSortedAlbums(3)
            emit(result)
        }

        val pathResponse : LiveData<Response<AlbumItem>> = liveData {
            val result = albumService.getAlbum(3)
            emit(result)
        }

        pathResponse.observe(this) {
            val albumItem = it.body()
            if (albumItem != null) {
               Toast.makeText(this, albumItem.title, Toast.LENGTH_SHORT).show()
            }
        }

        response.observe(this) {
            val albumList = it.body()?.listIterator()
            if (albumList == null) {
                println("Error while fetching data")
                return@observe
            }

            while (albumList.hasNext()) {
                val albumItem = albumList.next()
                val result = """
                    ID: ${albumItem.id}
                    Title: ${albumItem.title}
                    User ID: ${albumItem.userId}
                """.trimIndent() + "\n\n\n"

                textView.append(result)
            }
        }
    }
}
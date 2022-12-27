package ca.josue.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text_view = findViewById<TextView>(R.id.text_view)

        val albumService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        val response : LiveData<Response<Album>> = liveData {
            val result = albumService.getAlbums()
            emit(result)
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

                text_view.append(result)
            }
        }
    }
}
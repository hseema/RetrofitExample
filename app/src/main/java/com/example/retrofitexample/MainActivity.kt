package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitexample.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val retService:AlbumService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        val responseLiveData:LiveData<Response<Albums>> = liveData{
            val response:Response<Albums> = retService.getAlbums()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumsList:MutableListIterator<AlbumsItem>? = it.body()?.listIterator()
            if(albumsList!=null){
                while(albumsList.hasNext()){
                    val albumsItem:AlbumsItem = albumsList.next()
                    Log.i("RetrofitTag",albumsItem.userId.toString())
                    val result:String =  " "+"Albums Title: ${albumsItem.title}"+"\n"+
                                         " "+"Albums Id: ${albumsItem.id}"+"\n"+
                                         " "+"Albums UserId: ${albumsItem.userId}"+"\n\n\n"
                    binding.textView.append(result)
                }
            }
        })
    }
}
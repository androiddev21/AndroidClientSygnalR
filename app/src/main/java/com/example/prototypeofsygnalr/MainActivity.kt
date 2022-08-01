package com.example.prototypeofsygnalr

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.signalr.HubConnectionBuilder
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var job: Job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "url"
        val hubConnection = HubConnectionBuilder.create(url)
            .withAccessTokenProvider(Single.defer {
                Single.just(
                    "token"
                )
            })
            .build()

        val event = "event"
        hubConnection.on(
            event, { event ->
                Log.d("SygnalR test", event)
            }, String::class.java
        )

        scope.launch(Dispatchers.Main){
            hubConnection.start()
        }
    }
}
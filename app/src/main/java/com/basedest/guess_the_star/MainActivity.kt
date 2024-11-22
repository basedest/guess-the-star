package com.basedest.guess_the_star


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

import coil.compose.rememberAsyncImagePainter

import com.basedest.guess_the_star.data.Celebrity

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.basedest.guess_the_star.data.CelebrityApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(CelebrityApi::class.java)

        setContent {
            MaterialTheme {
                CelebrityGuessingGame(api)
            }
        }
    }
}

@Composable
fun CelebrityGuessingGame(api: CelebrityApi) {
    var celebrities by remember { mutableStateOf<List<Celebrity>>(emptyList()) }
    var currentCelebrity by remember { mutableStateOf<Celebrity?>(null) }
    var guess by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        celebrities = api.getCelebrities()
        currentCelebrity = celebrities.random()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        currentCelebrity?.let { celebrity ->
            Image(
                painter = rememberAsyncImagePainter(celebrity.image_url),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = guess,
                onValueChange = { guess = it },
                label = { Text("Enter Celebrity Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                result = if (guess.text.equals(celebrity.name, ignoreCase = true)) {
                    "Correct! It's ${celebrity.name}"
                } else {
                    "Wrong! It's ${celebrity.name}"
                }
                currentCelebrity = celebrities.random()
                guess = TextFieldValue("")
            }) {
                Text("Confirm")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(result)
        }
    }
}
package com.TwitterClone.twitterclone

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.TwitterClone.Sayfa2
import com.TwitterClone.twitterclone.ui.theme.InstagramCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneTheme {
             Scaffold(content = {
                 SayfaGeçişleri()
             })
              }
            }
        }
    }
@Composable
fun SayfaGeçişleri() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "sayfa1"){
        composable("sayfa1"){
            Sayfa1(navController = navController)
        }
        composable("sayfa2"){
            Sayfa2(navController = navController)
        }
    }
}
@Composable
fun Sayfa1(navController: NavController){
    val ka = remember { mutableStateOf("")}
    val sifre = remember { mutableStateOf("")}
    val auth = Firebase.auth

    Scaffold(topBar = {}) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value =ka.value , onValueChange = {ka.value=it})
            TextField(value = sifre.value, onValueChange ={sifre.value=it} )
            Row(modifier = Modifier
                .fillMaxWidth()
                , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Button(onClick = {  auth.signInWithEmailAndPassword(ka.value.trim(),sifre.value.trim())
                    .addOnCompleteListener{task->
                        if(task.isSuccessful){
                            auth.currentUser
                            navController.navigate("sayfa2")
                           Log.e("ss","ok")
                        }else{
                            Log.e("ss","failed")
                        } }

                }, modifier = Modifier.padding(end = 5.dp)) {
                    Text(text = "SIGN IN")
                }
                Button(onClick = {
                    auth.createUserWithEmailAndPassword(ka.value.trim(),sifre.value.trim())
                        .addOnCompleteListener{task->
                            if(task.isSuccessful){
                        Log.e("ss","success")
                    }else{
                        Log.e("ss","failed")
                    } }
                                 },modifier = Modifier.padding(start = 5.dp)) {
                    Text(text = "SIGN UP")
                }
            }

        }

    }

    }



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InstagramCloneTheme {

    }
}
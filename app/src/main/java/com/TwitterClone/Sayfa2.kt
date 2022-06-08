package com.TwitterClone

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sariaydinalparslan.instagramclone.R

@Composable
fun Sayfa2(navController: NavController) {
    val db = Firebase.firestore

    val kullanıcıidValue= remember { mutableStateOf("")}
    val tweetValue= remember { mutableStateOf("")}

    val recipes= remember { mutableStateListOf(Veri()) }
    val isSaved= remember { mutableStateOf(false)}


    RecipeService.getVeri(recipes)
    Column(modifier = Modifier
        .padding(all = 20.dp)
        .fillMaxWidth()
    ) {
        nicknameText(fieldValue = kullanıcıidValue, label ="ID" )
        TweetText(fieldValue = tweetValue, label = "Tweet")
        Button(onClick = {
            val recipe = Veri(
                id = kullanıcıidValue.value,
                tweet =tweetValue.value,

            )
            db.collection("recipes").add(recipe).addOnCompleteListener {
                if (it.isSuccessful){
                    Log.e("tamam","tamam")
                    isSaved.value=true
                    kullanıcıidValue.value=TextFieldValue().toString()
                    tweetValue.value=TextFieldValue().toString()
                }else{
                    Log.e("tamam","olmadı")
                }
            }
        }) {
            Text(text = "Write A Tweet")
            kullanıcıidValue.value=""
            tweetValue.value=""
        }
        if (isSaved.value){
            RecipeService.getVeri(recipes)
            isSaved.value=false
        }
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn{
            items(recipes) { item: Veri ->
                RecipeListItem(recipe= item)
            }
        }
    }
}
@Composable
fun RecipeListItem(recipe : Veri){
    Card(modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxHeight(),
        shape = RoundedCornerShape(8.dp),

    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row() {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_person_24), contentDescription ="Profile Photo"  )
                Spacer(modifier = Modifier.padding(8.dp))
                Column(modifier = Modifier.padding(all = 2.dp).fillMaxWidth()) {
                    Text(text = recipe.id)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = recipe.tweet)
                    Spacer(modifier = Modifier.padding(8.dp))
                }

            }

        }

    }
}

@Composable
fun TweetText(
    fieldValue: MutableState<String>,
    label:String,
    singleLine:Boolean=true)
{
    OutlinedTextField(label={ Text(text = label)},
        modifier = if (singleLine){
        Modifier.fillMaxWidth()}
        else{
            Modifier
                .fillMaxWidth()
                .height(140.dp) },
        singleLine=singleLine,
        value =fieldValue.value , onValueChange = {fieldValue.value=it})

}
@Composable
fun nicknameText(
    fieldValue: MutableState<String>,
    label:String,
    singleLine:Boolean=true)
{
    OutlinedTextField(label={ Text(text = label)}, colors = TextFieldDefaults.outlinedTextFieldColors(
        colorResource(id = R.color.black)),
        modifier = Modifier.size(80.dp),
        singleLine=singleLine,
        value =fieldValue.value , onValueChange = {fieldValue.value=it})

}

fun <T>SnapshotStateList<T>.updateList(newList: List<T>){
    clear()
    addAll(newList)
}

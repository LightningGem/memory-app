package com.example.memory_app.data.levels.resources

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.memory_app.R
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class LevelsResourcesHolderImpl @Inject constructor
    (@ApplicationContext context : Context) : LevelsResourcesHolder {

    companion object {
        const val COLLECTION_PATH = "levels"
        const val KEY_CARDS_URIS = "cardImagesUris"
        const val KEY_DIFFICULTY = "difficulty"
        const val KEY_FACE_OFF_URI = "faceOffImageUri"
        const val KEY_ICON_URI = "levelIconImageUri"
    }

    private fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(resourceId))
            .appendPath(getResourceTypeName(resourceId))
            .appendPath(getResourceEntryName(resourceId))
            .build()
    }

    private val localResources : List<Resources> = listOf(
        Resources(
            levelName = "Pets",
            difficulty = 0,
            cardImagesUris = listOf(
                R.drawable.pets_card1,
                R.drawable.pets_card2,
                R.drawable.pets_card3,
                R.drawable.pets_card4,
                R.drawable.pets_card5,
                R.drawable.pets_card6,
            ).map { context.resourceUri(it) },
            faceOffImageUri = context.resourceUri(R.drawable.pets_face_off_image),
            levelIconImageUri = context.resourceUri(R.drawable.pets_icon)
        ),
        Resources(
            levelName = "Health",
            difficulty = 1,
            cardImagesUris = listOf(
                R.drawable.health_card1,
                R.drawable.health_card2,
                R.drawable.health_card3,
                R.drawable.health_card4,
                R.drawable.health_card5,
                R.drawable.health_card6,
                R.drawable.health_card7,
            ).map { context.resourceUri(it) },
            faceOffImageUri = context.resourceUri(R.drawable.health_face_off_image),
            levelIconImageUri = context.resourceUri(R.drawable.health_icon)
        ),
        Resources(
            levelName = "Nature",
            difficulty = 2,
            cardImagesUris = listOf(
                R.drawable.nature_card1,
                R.drawable.nature_card2,
                R.drawable.nature_card3,
                R.drawable.nature_card4,
                R.drawable.nature_card5,
                R.drawable.nature_card6,
                R.drawable.nature_card7,
                R.drawable.nature_card8,
                R.drawable.nature_card9,
                R.drawable.nature_card10,
                R.drawable.nature_card11,
            ).map { context.resourceUri(it) },
            faceOffImageUri = context.resourceUri(R.drawable.nature_face_off_image),
            levelIconImageUri = context.resourceUri(R.drawable.nature_icon)
        )
    )

    private lateinit var remoteResources: List<Resources>

    override fun getLevelResources(levelName : String) : Resources =
        try { localResources.first { it.levelName == levelName} }
        catch (e : NoSuchElementException) { remoteResources.first { it.levelName == levelName} }

    override fun getAllLevelsResources(remote : Boolean) : Flow<List<Resources>> {
        if(!remote) return flow { emit(localResources) }
        return flow {
            val resources = mutableListOf<Resources>()
            var result : List<Resources>? = null
            var exception : Exception? = null

            Firebase.firestore.collection(COLLECTION_PATH)
                .get(Source.SERVER)
                .addOnSuccessListener { documents -> documents.forEach { document ->
                    resources.add(Resources(
                        levelName = document.id,
                        difficulty = document.getDouble(KEY_DIFFICULTY)!!.toInt(),
                        cardImagesUris = (document.get(KEY_CARDS_URIS)
                                as List<String>).map { it.toUri() },
                        faceOffImageUri = document.getString(KEY_FACE_OFF_URI)!!.toUri(),
                        levelIconImageUri = document.getString(KEY_ICON_URI)!!.toUri() ))
                }
                    result = resources.sortedBy { it.difficulty }
                }
                .addOnFailureListener { exception = it }
                .await()

            remoteResources = result ?: throw exception!!
            emit(remoteResources)
        }
    }

}
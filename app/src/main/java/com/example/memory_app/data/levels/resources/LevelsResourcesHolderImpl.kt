package com.example.memory_app.data.levels.resources

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.memory_app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelsResourcesHolderImpl @Inject constructor
    (@ApplicationContext context : Context) : LevelsResourcesHolder {

    private fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(resourceId))
            .appendPath(getResourceTypeName(resourceId))
            .appendPath(getResourceEntryName(resourceId))
            .build()
    }

    private val localResources : List<Resources> = listOf(
        Resources("Basics", listOf(
            context.resourceUri(R.drawable.test_card0),
            context.resourceUri(R.drawable.test_card1),
            context.resourceUri(R.drawable.test_card2),
            context.resourceUri(R.drawable.test_card3)
            ),
            context.resourceUri(R.drawable.face_off_image),
            context.resourceUri(R.drawable.test_card1)
        ),
        Resources("Furniture", listOf(
            context.resourceUri(R.drawable.test_card4),
            context.resourceUri(R.drawable.test_card5),
            context.resourceUri(R.drawable.test_card6),
            context.resourceUri(R.drawable.test_card7),
            context.resourceUri(R.drawable.test_card8)
            ),
            context.resourceUri(R.drawable.face_off_image),
            context.resourceUri(R.drawable.test_icon1)
        ),
        Resources("Sport", listOf(
            context.resourceUri(R.drawable.test_card9),
            context.resourceUri(R.drawable.test_card10),
            context.resourceUri(R.drawable.test_card11),
            context.resourceUri(R.drawable.test_card12),
            context.resourceUri(R.drawable.test_card13),
            context.resourceUri(R.drawable.test_card14)
            ),
            context.resourceUri(R.drawable.face_off_image),
            context.resourceUri(R.drawable.test_icon2)
        )
    )

    private var currentSourceIsRemote = false
    private lateinit var remoteResources: List<Resources>


    override fun getLevelResources(levelName : String) : Resources =
        if (!currentSourceIsRemote) localResources.first {it.levelName == levelName}
        else remoteResources.first {it.levelName == levelName}


    override fun getAllLevelsResources(remote : Boolean) : Flow<List<Resources>> {
        currentSourceIsRemote = remote

        if(!remote) return flow { emit(localResources) }
        return flow {
            throw Exception("Not implemented yet!")
            // remoteResources = ...
            // emit(remoteResources)
        }
    }
}
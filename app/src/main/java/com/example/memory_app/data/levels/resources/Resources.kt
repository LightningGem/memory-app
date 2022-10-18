package com.example.memory_app.data.levels.resources

import android.net.Uri

data class Resources(val levelName : String,
                     val cardImagesUris : List<Uri>,
                     val faceOffImageUri : Uri,
                     val levelIconImageUri : Uri)

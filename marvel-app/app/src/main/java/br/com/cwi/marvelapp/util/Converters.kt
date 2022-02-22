package br.com.cwi.marvelapp.util

import androidx.room.TypeConverter
import br.com.cwi.marvelapp.data.model.local.ThumbnailLocal
import br.com.cwi.marvelapp.util.SerializationExtension.jsonToObject
import br.com.cwi.marvelapp.util.SerializationExtension.objectToJson

object Converters {

    class ThumbnailConverter {
        @TypeConverter
        fun toObject(json: String): ThumbnailLocal? {
            return json.jsonToObject<ThumbnailLocal>()
        }

        @TypeConverter
        fun toJson(obj: ThumbnailLocal): String? {
            return obj.objectToJson()
        }
    }
}
package br.com.cwi.marvelapp.data.source.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.cwi.marvelapp.data.model.local.CharacterItemLocal
import br.com.cwi.marvelapp.util.Converters

@Database(entities = [CharacterItemLocal::class], version = 2)
@TypeConverters(Converters.ThumbnailConverter::class)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun getItemDao(): CharacterDao

    companion object {
        private const val DATABASE_NAME = "marvel-db"
        fun create(application: Application): MarvelDatabase {
            return Room.databaseBuilder(
                application,
                MarvelDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
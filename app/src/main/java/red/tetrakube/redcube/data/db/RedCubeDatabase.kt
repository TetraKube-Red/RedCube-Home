package red.tetrakube.redcube.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import red.tetrakube.redcube.data.db.datasource.HubDao
import red.tetrakube.redcube.data.db.entities.HubEntity
import red.tetrakube.redcube.data.db.entities.RoomEntity

@Database(
    entities = [
        HubEntity::class,
        RoomEntity::class
    ],
    version = 1,
    autoMigrations = [
    ]
)
abstract class RedCubeDatabase : RoomDatabase() {
    abstract fun hubRepository(): HubDao

    companion object {
        private const val DATABASE_FILE_NAME = "home_kit_red.db"

        @Volatile
        private var HOME_KIT_RED_DATABASE_INSTANCE: RedCubeDatabase? = null

        fun getInstance(context: Context): RedCubeDatabase {
            synchronized(this) {
                var instance = HOME_KIT_RED_DATABASE_INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RedCubeDatabase::class.java,
                        DATABASE_FILE_NAME
                    )
                        .build()

                    HOME_KIT_RED_DATABASE_INSTANCE = instance
                }
                return instance
            }
        }
    }
}
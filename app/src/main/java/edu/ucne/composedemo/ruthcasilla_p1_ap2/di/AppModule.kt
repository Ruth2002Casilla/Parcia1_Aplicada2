package edu.ucne.composedemo.ruthcasilla_p1_ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.database.VentaDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideVentaDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            VentaDb::class.java,
            "Venta.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideVentaDao(ventaDb: VentaDb) = ventaDb.VentasDao()

}
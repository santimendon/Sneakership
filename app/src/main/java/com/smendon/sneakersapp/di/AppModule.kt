package com.smendon.sneakersapp.di

import android.app.Application
import androidx.room.Room
import com.smendon.sneakersapp.branch.BranchHelper
import com.smendon.sneakersapp.data.datasource.local.AppDatabase
import com.smendon.sneakersapp.data.datasource.remote.RemoteData
import com.smendon.sneakersapp.data.repository.SneakersRepositoryImpl
import com.smendon.sneakersapp.domain.repository.SneakersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteData(): RemoteData {
        return RemoteData()
    }

    @Provides
    @Singleton
    fun provideBranchHelper(app: Application): BranchHelper {
        return BranchHelper(app)
    }

    @Provides
    @Singleton
    fun provideRepository(
        remoteData: RemoteData,
        database: AppDatabase,
        branchHelper: BranchHelper
    ): SneakersRepository {
        return SneakersRepositoryImpl(remoteData, database.appDao, branchHelper)
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
}
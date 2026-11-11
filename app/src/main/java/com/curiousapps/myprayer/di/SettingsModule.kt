package com.curiousapps.myprayer.di

import com.curiousapps.myprayer.repository.SettingsRepository
import com.curiousapps.myprayer.repository.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SettingsModule {

    @Binds
    fun bindSettingsRepository(
        implementation: SettingsRepositoryImpl
    ): SettingsRepository
}

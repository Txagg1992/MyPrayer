package com.curiousapps.myprayer.di

import com.curiousapps.myprayer.repository.RosaryRepository
import com.curiousapps.myprayer.repository.RosaryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RosaryModule {

    @Binds
    fun bindRosaryRepository(
        implementation: RosaryRepositoryImpl
    ): RosaryRepository
}
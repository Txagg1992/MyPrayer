package com.curiousapps.myprayer.di

import com.curiousapps.myprayer.repository.SaintRepository
import com.curiousapps.myprayer.repository.SaintRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SaintModule {

    @Binds
    fun bindSaintRepository(
        implementation: SaintRepositoryImpl
    ): SaintRepository
}

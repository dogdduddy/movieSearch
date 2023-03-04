package com.dogdduddy.moviesearch.viewmodel

import com.dogdduddy.moviesearch.model.remote.movieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {
    @Binds
     abstract fun bindMovieRepository(movieRepositoryImpl: movieRepositoryImpl): movieRepository
}
/*
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.injection.module

import com.sanjay.gutenberg.data.repository.GutenbergDataSource
import com.sanjay.gutenberg.data.repository.local.GutenbergLocalDataSource
import com.sanjay.gutenberg.data.repository.remote.GutenbergRemoteDataSource
import com.sanjay.gutenberg.injection.annotations.Local
import com.sanjay.gutenberg.injection.annotations.Remote
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Sanjay Sah on 14/11/2017.
 */

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Local
    fun providesLocalDataSource(localDataSource: GutenbergLocalDataSource): GutenbergDataSource = localDataSource

    @Provides
    @Singleton
    @Remote
    fun providesRemoteDataSource(remoteDataSource: GutenbergRemoteDataSource): GutenbergDataSource = remoteDataSource

}
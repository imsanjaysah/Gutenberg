package com.sanjay.gutenberg.injection.module

import com.sanjay.gutenberg.data.repository.FakeGutenbergRepository
import com.sanjay.gutenberg.data.repository.GutenbergDataSource
import com.sanjay.gutenberg.data.repository.local.GutenbergLocalDataSource
import com.sanjay.gutenberg.data.repository.remote.GutenbergRemoteDataSource
import dagger.Module


class TestRepositoryModule() : RepositoryModule() {
    override fun providesLocalDataSource(localDataSource: GutenbergLocalDataSource): GutenbergDataSource =
        FakeGutenbergRepository()

    override fun providesRemoteDataSource(remoteDataSource: GutenbergRemoteDataSource): GutenbergDataSource =
        FakeGutenbergRepository()
}
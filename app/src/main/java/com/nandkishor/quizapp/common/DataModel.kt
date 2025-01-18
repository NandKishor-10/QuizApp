package com.nandkishor.quizapp.common

import com.nandkishor.quizapp.data.remote.QuizApi
import com.nandkishor.quizapp.data.repository.QuizRepositoryImpl
import com.nandkishor.quizapp.domian.repository.QuizRepository
import com.nandkishor.quizapp.domian.usecases.GetQuizzesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModel {

    @Provides
    @Singleton
    fun provideQuizApi(): QuizApi{
        return Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }
    @Provides
    @Singleton
    fun provideQuizRepo(quizApi: QuizApi): QuizRepository {
        return QuizRepositoryImpl(quizApi)
    }

    @Provides
    @Singleton
    fun provideGetQuizzesUseCases(quizRepository: QuizRepository): GetQuizzesUseCases{
        return GetQuizzesUseCases(quizRepository)
    }
}
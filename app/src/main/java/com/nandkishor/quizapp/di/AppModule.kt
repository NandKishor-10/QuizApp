package com.nandkishor.quizapp.di

import com.nandkishor.quizapp.data.remote.QuizApi
import com.nandkishor.quizapp.data.repository.QuizRepositoryImpl
import com.nandkishor.quizapp.domain.usecase.GetQuizzesUseCases
import com.nandkishor.quizapp.data.repository.QuizRepository
import com.nandkishor.quizapp.presentation.viewmodel.QuizViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    single { QuizApi(get()) }

    single<QuizRepository> { QuizRepositoryImpl(get()) }

    factory { GetQuizzesUseCases(get()) }

    viewModel { QuizViewModel(get()) }

}
package com.alexzh.company.contacts

import android.content.Context
import com.alexzh.company.contacts.data.source.TeamDataRepository
import com.alexzh.company.contacts.data.source.TeamDataSource
import com.alexzh.company.contacts.data.source.TeamRepository
import com.alexzh.company.contacts.data.source.local.ContactsDatabase
import com.alexzh.company.contacts.data.source.local.LocalTeamDataSource
import com.alexzh.company.contacts.data.source.remote.ContactsApiService
import com.alexzh.company.contacts.data.source.remote.RemoteTeamDataSource
import com.alexzh.company.contacts.teams.TeamsViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    internal object App {
        fun provideAppSchedulersProvider(): SchedulersProvider {
            return AppSchedulersProvider()
        }

        fun provideDatabase(context: Context): ContactsDatabase {
            return ContactsDatabase.getInstance(context)
        }
    }

    internal object Teams {
        fun provideTeamsViewModel(context: Context): TeamsViewModel {
            return TeamsViewModel(Data.provideTeamDataRepository(context), App.provideAppSchedulersProvider())
        }
    }

    internal object Data {

        private fun provideLocalTeamsDataSource(context: Context): TeamDataSource {
            return LocalTeamDataSource(App.provideDatabase(context))
        }

        private fun provideRemoteTeamsDataSource(): TeamDataSource {
            return RemoteTeamDataSource(Api.provideContactApiService())
        }

        fun provideTeamDataRepository(context: Context): TeamRepository {
            return TeamDataRepository(
                    provideLocalTeamsDataSource(context),
                    provideRemoteTeamsDataSource())
        }
    }

    internal object Api {
        private const val BASE_URL = "https://raw.githubusercontent.com/AlexZhukovich/CompanyContacts/master/"

        private fun provideHttpClient(): OkHttpClient {
            return OkHttpClient()
        }

        private fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
        }

        private fun provideContactsApiService(retrofit: Retrofit): ContactsApiService {
            return retrofit.create(ContactsApiService::class.java)
        }

        fun provideContactApiService(): ContactsApiService {
            return provideContactsApiService(
                    provideRetrofit(
                            provideHttpClient()))
        }
    }
}
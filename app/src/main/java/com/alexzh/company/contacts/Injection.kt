package com.alexzh.company.contacts

import com.alexzh.company.contacts.data.source.TeamDataRepository
import com.alexzh.company.contacts.data.source.TeamDataSource
import com.alexzh.company.contacts.data.source.TeamRepository
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
    }

    internal object Teams {
        fun provideTeamsViewModel(): TeamsViewModel {
            return TeamsViewModel(Data.provideTeamDataRepository(), App.provideAppSchedulersProvider())
        }
    }

    internal object Data {

        private fun provideLocalTeamsDataSource(): TeamDataSource {
            return LocalTeamDataSource()
        }

        private fun provideRemoteTeamsDataSource(): TeamDataSource {
            return RemoteTeamDataSource(Api.provideContactApiService())
        }

        fun provideTeamDataRepository(): TeamRepository {
            return TeamDataRepository(
                    provideLocalTeamsDataSource(),
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
package com.alexzh.company.contacts

import android.content.Context
import com.alexzh.company.contacts.data.source.*
import com.alexzh.company.contacts.data.source.local.ContactsDatabase
import com.alexzh.company.contacts.data.source.local.LocalEmployeeDataSource
import com.alexzh.company.contacts.data.source.local.LocalTeamDataSource
import com.alexzh.company.contacts.data.source.remote.ContactsApiService
import com.alexzh.company.contacts.data.source.remote.RemoteEmployeeDataSource
import com.alexzh.company.contacts.data.source.remote.RemoteTeamDataSource
import com.alexzh.company.contacts.employees.EmployeesViewModel
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

    internal object Employees {
        fun provideEmployeesViewModel(context: Context): EmployeesViewModel {
            return EmployeesViewModel(Data.provideEmployeeDataRepository(context), App.provideAppSchedulersProvider())
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

        private fun provideLocalEmployeeDataSource(context: Context): EmployeeDataSource {
            return LocalEmployeeDataSource(App.provideDatabase(context))
        }

        private fun provideRemoteEmployeeDataSource(): EmployeeDataSource {
            return RemoteEmployeeDataSource(Api.provideContactApiService())
        }

        fun provideEmployeeDataRepository(context: Context): EmployeeRepository {
            return EmployeeDataRepository(
                    provideLocalEmployeeDataSource(context),
                    provideRemoteEmployeeDataSource()
            )
        }
    }

    internal object Api {
        private const val BASE_URL = "https://raw.githubusercontent.com/foobaz42/CompanyContacts/master/server-data/"

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
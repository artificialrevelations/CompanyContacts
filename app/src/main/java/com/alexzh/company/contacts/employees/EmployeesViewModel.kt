package com.alexzh.company.contacts.employees

import androidx.lifecycle.*
import com.alexzh.company.contacts.common.UiState
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EmployeesViewModel(
        private val employeeRepository: EmployeeRepository
): ViewModel() {
    private val _employees = MutableLiveData<UiState<List<Employee>>>()
    val employee: LiveData<UiState<List<Employee>>>
        get() = _employees

    fun loadEmployees(teamId: Long) {
        _employees.value = UiState(isLoading = true)
        viewModelScope.launch {
            employeeRepository.fetchEmployeesByTeamId(teamId)
                .catch {
                    it.printStackTrace()
                    _employees.value = UiState(error = it)
                }
                .collect {

                    _employees.value = UiState(data = it)
                }
        }
    }
}
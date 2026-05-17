package com.dmm.recetario.domain.use_cases.user

import android.util.Log
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.repository.UserRepository
import com.dmm.recetario.domain.model.User
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetUsersUseCase @Inject constructor (
    private val repository: UserRepository,
    private val dao: UserDAO
) {
    suspend operator fun invoke(scope: CoroutineScope): List<User> {
        var users = emptyList<User>()

        try {
            users = repository.getAllUsers()
        } catch (e: APIException) {
            Log.w("GetUsersUseCase", "${e.message}")
        }

        if (users.isNotEmpty()) {
            scope.launch {
                dao.saveUsers(users.map { it.toEntity() })
            }
        } else {
            users = dao.getUsers().map { it.toDomain() }
        }

        return users
    }
}
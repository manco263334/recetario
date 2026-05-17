package com.dmm.recetario.domain.use_cases.user

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.repository.UserRepository
import com.dmm.recetario.domain.model.User
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UpdateUserUseCase @Inject constructor (
    private val repository: UserRepository,
    private val dao: UserDAO
) {
    suspend operator fun invoke(id: String, data: User, scope: CoroutineScope): User? {
        var user: User?

        try {
            user = repository.updateUser(id, data)
        } catch (e: APIException) {
            Log.w("UpdateUserUseCase", "${e.message}")
            return null
        }

        if (user.isNotNull()) {
            scope.launch {
                dao.saveUsers(listOf(user.toEntity()))
            }
        }

        return user
    }
}
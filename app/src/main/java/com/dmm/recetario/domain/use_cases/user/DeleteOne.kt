package com.dmm.recetario.domain.use_cases.user

import android.util.Log
import com.dmm.recetario.core.utils.handler.APIException
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteUserUseCase @Inject constructor (
    private val repository: UserRepository,
    private val dao: UserDAO
) {
    suspend operator fun invoke(id: String, scope: CoroutineScope): Boolean {
        try {
            repository.deleteUser(id)
        } catch (e: APIException) {
            Log.w("DeleteUserUseCase", "${e.message}")
            return false
        }

        scope.launch {
            dao.deleteUser(id)
        }

        return true
    }
}
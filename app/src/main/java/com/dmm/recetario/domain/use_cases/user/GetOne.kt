package com.dmm.recetario.domain.use_cases.user

import android.util.Log
import com.dmm.recetario.core.utils.extension.isNotNull
import com.dmm.recetario.core.utils.mapper.toDomain
import com.dmm.recetario.core.utils.mapper.toEntity
import com.dmm.recetario.data.local.database.dao.UserDAO
import com.dmm.recetario.data.repository.UserRepository
import com.dmm.recetario.domain.model.User
import com.google.android.gms.common.api.ApiException
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetUserUseCase @Inject constructor (
    private val repository: UserRepository,
    private val dao: UserDAO
) {
    suspend operator fun invoke(id: String, scope: CoroutineScope): User? {
        var user: User? = null

        try {
           user = repository.getUser(id)
        } catch (e: ApiException) {
            Log.w("GetUserUseCase", "${e.message}")
        }

        if (user.isNotNull()) {
            scope.launch {
                dao.saveUsers(listOf(user.toEntity()))
            }
        } else {
            user = dao.getUser(id)?.toDomain()
        }

        return user
    }
}
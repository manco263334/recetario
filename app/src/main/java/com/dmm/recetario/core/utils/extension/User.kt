package com.dmm.recetario.core.utils.extension

import com.dmm.recetario.domain.model.AnonymousUser
import com.dmm.recetario.domain.model.User
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun User?.isNeitherNullNorAnonymous(): Boolean {
    contract {
        returns(true) implies (
            this@isNeitherNullNorAnonymous != null &&
            this@isNeitherNullNorAnonymous !is AnonymousUser
        )
    }

    return this != null && this !is AnonymousUser
}
package com.dmm.recetario.core.utils.extension

import com.dmm.recetario.domain.model.AnonymousUser
import com.dmm.recetario.domain.model.User
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun User?.isNotAnonymous(): Boolean {
    contract {
        returns(true) implies (this@isNotAnonymous != null && this@isNotAnonymous !is AnonymousUser)
    }

    return this !is AnonymousUser
}
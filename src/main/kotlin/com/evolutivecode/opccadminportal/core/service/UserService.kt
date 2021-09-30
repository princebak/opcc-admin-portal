package com.evolutivecode.opccadminportal.core.service

import com.evolutivecode.opccadminportal.core.domain.User
import com.evolutivecode.opccadminportal.core.domain.Credential

interface UserService{
    fun create(credential: Credential, t:User): User?
    fun findAll() : List<User>
    fun findById(id:String):User?
    fun update(credential: Credential, t:User):User?
    fun count(): Long
}
package com.evolutivecode.opccadminportal.infra.dto.auth

class UpdatePasswordDto(
        val username: String,
        val password: String,
        val code: String,
        val profile: String
)

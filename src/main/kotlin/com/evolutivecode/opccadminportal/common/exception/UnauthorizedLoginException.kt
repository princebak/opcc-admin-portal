package com.evolutivecode.opccadminportal.common.exception

import org.springframework.security.core.AuthenticationException

class UnauthorizedLoginException(msg: String?) : AuthenticationException(msg)

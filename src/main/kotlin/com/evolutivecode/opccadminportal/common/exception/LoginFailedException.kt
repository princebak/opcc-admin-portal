package com.evolutivecode.opccadminportal.common.exception

import org.springframework.security.core.AuthenticationException

class LoginFailedException(msg: String?) : AuthenticationException(msg)

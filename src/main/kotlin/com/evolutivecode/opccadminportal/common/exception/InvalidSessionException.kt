package com.evolutivecode.opccadminportal.common.exception

import org.springframework.security.core.AuthenticationException

class InvalidSessionException(msg: String?) : AuthenticationException(msg)

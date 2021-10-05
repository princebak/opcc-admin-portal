package com.evolutivecode.opccadminportal.common.exception

import org.springframework.security.core.AuthenticationException

class InvalidMemberException(msg: String?) : AuthenticationException(msg)

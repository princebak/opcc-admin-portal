package com.evolutivecode.opccadminportal.common.util

import com.evolutivecode.opccadminportal.common.util.Constant.Companion.X_API_KEY
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.X_APP_ID
import com.evolutivecode.opccadminportal.common.util.Constant.Companion.X_JWT
import com.evolutivecode.opccadminportal.core.domain.Credential
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

class HttpEntityFactory {
    companion object{
        const val USERNAME = "username"
        const val NAME = "name"

        fun buildUserEntity(credential: Credential): HttpEntity<String?>{
            val headers = HttpHeaders()
            headers.set(X_JWT,credential.token)
            headers.set(X_API_KEY,credential.access);

            return HttpEntity(headers)
        }

        fun buildUserEntityAndIdentity(username: String, credential: Credential): HttpEntity<String?>{
            var headers = HttpHeaders()
            headers.set(X_JWT,credential.token)
            headers.set(X_API_KEY,credential.access);
            headers.set(USERNAME, username)

            return HttpEntity(headers)
        }
        fun buildUserEntityWithJustEmail(username: String, fieldName : String = "username"): HttpEntity<String?>{
            var headers = HttpHeaders()
            headers.set(fieldName, username)

            return HttpEntity(headers)
        }

        fun buildNoUserEntity(): HttpEntity<String?>{
            val headers = HttpHeaders()
            headers.set(X_APP_ID, "reading")
            return HttpEntity(headers)
        }

        fun buildUserEntityWithField(fieldName: String, fieldValue:String): HttpEntity<String?>{
            val headers = HttpHeaders()
            headers.set(fieldName, fieldValue)

            return HttpEntity(headers)
        }

    }
}

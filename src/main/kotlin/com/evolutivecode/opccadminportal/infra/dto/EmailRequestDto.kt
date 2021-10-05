package com.evolutivecode.opccadminportal.infra.dto

import com.evolutivecode.opccadminportal.application.form.ContactForm


class EmailRequestDto {

    companion object {
        fun mapToEmailRequestDto(contactForm: ContactForm, emailDest: String): EmailRequestDto {
            val emailRequestDto = EmailRequestDto()
            emailRequestDto.from = contactForm.email!!
            emailRequestDto.to = emailDest
            emailRequestDto.name = contactForm.name!!
            emailRequestDto.phone = contactForm.phone!!
            emailRequestDto.message = contactForm.message!!
            emailRequestDto.subject = contactForm.subject!!

            return emailRequestDto
        }
    }

    lateinit var from: String
    lateinit var to: String
    lateinit var subject: String
    lateinit var name: String
    lateinit var phone: String
    lateinit var message: String
}

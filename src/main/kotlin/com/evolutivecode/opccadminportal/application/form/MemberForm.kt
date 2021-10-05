package com.evolutivecode.opccadminportal.application.form

import org.springframework.web.multipart.MultipartFile

class MemberForm {
    var type: String? = null
    var id : String? = null
    var firstName : String? = null
    var lastName : String? = null
    var name : String? = null
    var principalSector : String? = null
    var secondarySector : String? = null
    var sector : String? = null
    var phone :String? = null
    var email : String? = null
    var linkedin : String? = null
    var fb : String?= null
    var youtube : String? = null
    var twitter : String?=  null
    var firstLineAddress : String? = null
    var province: String? = null
    var website : String? = null
    var description : String? = null
    var picture : MultipartFile? = null

}

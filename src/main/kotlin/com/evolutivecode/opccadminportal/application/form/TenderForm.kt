package com.evolutivecode.opccadminportal.application.form

import org.springframework.web.multipart.MultipartFile

class TenderForm {

    var id : String? = null
    var title : String? = null
    var tags : String? = null
    var description : String? = null
    var sector : String? = null
    //contact
    var phone : String? = null
    var contactEmail : String? = null

    //address
    var address : String? = null
    var province : String? = null

    //images
    var backgroundImage : MultipartFile? = null
    var cover : MultipartFile? = null


}

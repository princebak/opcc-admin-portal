package com.evolutivecode.opccadminportal.core.domain

import java.time.LocalDate

class Tender {
    var id : String? = null
    var member : PortalUser? = null
    var title : String? = null
    var description  : String? = null
    var sector :String? = null
    var contact : Contact? = Contact()
    var expires : LocalDate? = null
    var metadata : Metadata = Metadata()
    var status : String? = null
    fun dayElapsed() : Int{
        return 0
    }

}

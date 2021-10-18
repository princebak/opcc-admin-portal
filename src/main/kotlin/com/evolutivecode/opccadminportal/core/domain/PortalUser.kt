package com.evolutivecode.opccadminportal.core.domain


class PortalUser {
    var id: String? = null
    var name : String? = null
    var username: String? = null
    var description: String? = null
    var principalSector : String? = null
    var contact: Contact = Contact()
    var metadata : Metadata = Metadata()
    var status : String? = null

}

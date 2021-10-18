package com.evolutivecode.opccadminportal.core.domain

class Donation {
    var id:String? = null
    var amount:Double? = null
    var email: String? = null
    var clientName: String? = null
    var status: String? = null
    var currency: String? = null
    var metadata : Metadata = Metadata()
}

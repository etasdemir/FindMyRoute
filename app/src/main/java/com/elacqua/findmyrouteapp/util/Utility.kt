package com.elacqua.findmyrouteapp.util

import java.security.MessageDigest

object Utility {
    val String.md5: String
        get() {
            val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }
}
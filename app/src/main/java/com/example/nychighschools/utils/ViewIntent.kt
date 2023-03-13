package com.example.nychighschools.utils

/**
 * Capabilities of the view
 */
sealed class ViewIntent {
    object SCHOOLS: ViewIntent()
    object SatSCORES: ViewIntent()
    data class SchoolSCORE(val dbn: String): ViewIntent()
}
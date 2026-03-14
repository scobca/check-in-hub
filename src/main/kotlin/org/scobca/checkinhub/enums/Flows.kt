package org.scobca.checkinhub.enums

enum class Flows(private val alias: String) {
    FIRST("1 (11:00-13:00)"),
    SECOND("2 (14:00-16:00)"),
    THIRD("3 (17:00-19:00)");

    companion object {
        fun fromAlias(alias: String): Flows? {
            return Flows.entries.find { it.alias == alias }
        }
    }
}
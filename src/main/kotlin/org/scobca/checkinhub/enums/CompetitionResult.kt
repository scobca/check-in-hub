package org.scobca.checkinhub.enums

enum class CompetitionResult(private val alias: String) {
    FIRST("1 место"),
    SECOND("2 место"),
    THIRD("3 место");

    companion object {
        fun CompetitionResult.getAlias() = this.alias
    }
}
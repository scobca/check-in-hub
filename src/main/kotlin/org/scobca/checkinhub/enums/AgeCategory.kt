package org.scobca.checkinhub.enums

enum class AgeCategory(private val alias: String) {
    SCHOOL_1("7-8 лет"),
    SCHOOL_2("9-11 лет"),
    SCHOOL_3("12-13 лет");

    companion object {
        fun AgeCategory.getAlias() = this.alias
    }
}
package com.mazroid.chesstest.util

class AppConstant {
    companion object{
        const val base_url= "https://followchess.com/"

        fun getYear(value: String): String {

            var t: String? = null
            val lastIndex = value.lastIndexOf(
                string = "-",
                startIndex = value.count(),
                ignoreCase = true
            )
            try {
                t = value.substring(lastIndex, value.length)
                t = t.removePrefix("-")

            } catch (e: java.lang.IndexOutOfBoundsException) {
            }

            return t ?: value
        }

        fun getDashCount(value: String): String {

            var t: String? = null
            return t ?: value
        }
    }


}
package com.jpnacaratti.modtruck.utils

class MessageBuilder(private val randomLength: Int) {
    private val fragments = mutableListOf<Fragment>()
    private var firstFragment: Fragment? = null
    private var lastFragment: Fragment? = null

    fun addFragment(fragment: String) {
        val idStartRegex = Regex("^\\d{$randomLength}")
        val idEndRegex = Regex("\\d{$randomLength}$")

        val idStart = idStartRegex.find(fragment)?.value
        val idEnd = idEndRegex.find(fragment)?.value

        val cleanFragment = fragment.replace(idStartRegex, "").replace(idEndRegex, "")

        if (idStart == null) {
            firstFragment = Fragment(null, idEnd, cleanFragment)
            return
        }

        if (idEnd == null) {
            lastFragment = Fragment(idStart, null, cleanFragment)
            return
        }

        fragments.add(Fragment(idStart, idEnd, cleanFragment))
    }

    fun build(): String {
        val message = StringBuilder()

        firstFragment?.let { message.append(it.message) }

        val clonedFragments = fragments.toMutableList()

        var currentIdEnd = firstFragment?.idEnd
        while (currentIdEnd != null) {
            val nextFragment = clonedFragments.find { it.idStart == currentIdEnd }
            if (nextFragment != null) {
                message.append(nextFragment.message)
                currentIdEnd = nextFragment.idEnd
                clonedFragments.remove(nextFragment)
            } else {
                break
            }
        }

        lastFragment?.let { message.append(it.message) }

        return message.toString()
    }

    fun reset() {
        fragments.clear()
        firstFragment = null
        lastFragment = null
    }
}

data class Fragment(val idStart: String?, val idEnd: String?, val message: String)
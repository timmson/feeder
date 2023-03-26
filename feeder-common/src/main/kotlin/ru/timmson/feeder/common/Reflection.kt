package ru.timmson.feeder.common

fun <T : Any> injectValue(obj: T, fieldName: String, fieldValue: Any): T =
    obj.apply {
        javaClass.getDeclaredField(fieldName).let {
            it.isAccessible = true
            it.set(obj, fieldValue)
            it.isAccessible = false
        }
    }

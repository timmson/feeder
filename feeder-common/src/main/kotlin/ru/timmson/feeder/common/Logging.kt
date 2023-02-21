package ru.timmson.feeder.common

import java.util.logging.Logger

inline fun <reified T : Any> logger() = Logger.getLogger(T::class.java.name)
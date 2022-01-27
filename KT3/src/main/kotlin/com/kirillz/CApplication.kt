package com.kirillz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.kirillz.model.CUser
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@SpringBootApplication
class CApplication

fun main(args: Array<String>) {
    runApplication<CApplication>(*args)
}

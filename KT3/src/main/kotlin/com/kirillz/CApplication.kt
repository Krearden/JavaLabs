package com.kirillz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@SpringBootApplication
@ComponentScan("com.kirillz.repositories")
@ComponentScan("com.kirillz.rest")
class CApplication

fun main(args: Array<String>) {
    runApplication<CApplication>(*args)
}

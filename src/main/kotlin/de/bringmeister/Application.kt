package de.bringmeister

import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun getKlaxon(): Klaxon = Klaxon()
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

package demo

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TemplateApplication

fun main(args: Array<String>) {
    runApplication<TemplateApplication>(*args)
}
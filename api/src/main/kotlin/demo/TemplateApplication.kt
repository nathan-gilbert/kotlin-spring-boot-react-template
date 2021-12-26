package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TemplateApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<TemplateApplication>(*args)
}

package games.dotwo

import games.dotwo.plugins.configureHTTP
import games.dotwo.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    Config.initConfig(environment.config)
    configureHTTP()
    configureRouting()
}

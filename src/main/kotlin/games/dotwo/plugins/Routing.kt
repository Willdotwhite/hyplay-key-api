package games.dotwo.plugins

import games.dotwo.Config
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*

const val forwardApiRoot = "api.hyplay.com"

internal fun downstreamUrl(block: URLBuilder.() -> Unit): String = URLBuilder()
    .apply(block)
    .also { it.protocol = URLProtocol.HTTPS }
    .buildString()

private val httpClient = HttpClient(CIO)

fun Application.configureRouting() {
    routing {
        get("/{...}") {
            val uri = call.request.local.uri
            val hyplayApiKey = Config.getString("ktor.application.hyplayApiKey")

            val downstreamResponse: HttpResponse = httpClient.get(
                downstreamUrl {
                    host = forwardApiRoot
                    path(uri)
                    parameters.appendAll(call.request.queryParameters)
                }
            ) {
                headers {
                    // TODO: What's the most robust way to handle this? Someone has definitely solved this before.
                    val disallowedHttp2Headers = listOf("host", "connection", "accept-encoding", "origin")
                    val validHeaders = call.request.headers.entries().filter { (key, _) -> !disallowedHttp2Headers.contains(key.lowercase()) }

                    val stringValues = StringValuesBuilderImpl()

                    // Add secret key value
                    stringValues.append("x-app-authorization", hyplayApiKey)

                    // Add existing headers
                    validHeaders.map { stringValues.append(it.key, it.value.first()) }
                    appendAll(stringValues)
                }
            }

            val stringBody: String = downstreamResponse.body()
            return@get call.respond(HttpStatusCode.OK, stringBody)
        }
    }
}

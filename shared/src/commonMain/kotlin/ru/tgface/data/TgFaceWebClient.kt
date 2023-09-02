package ru.tgface.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import io.github.aakira.napier.Napier
import ru.tgface.data.models.SignInRequest
import ru.tgface.data.models.SignInResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpStatusCode
import ru.tgface.data.models.BotListItemResponse

class TgFaceWebClient {

    private val baseUrl = "https://tgface.ru/api/v1"
    private val client = HttpClient {
        install(Logging) {
            level = LogLevel.BODY
            logger = object : Logger {
                override fun log(message: String) {
                    println("HTTP = $message")
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val prefs: Settings = Settings()

    suspend fun signIn(email: String, password: String): Boolean = try {
        val response = client.post("$baseUrl/auth/signin") {
            contentType(ContentType.Application.Json)
            setBody(SignInRequest(email = email, password = password))
        }.body<SignInResponse>()
//        Napier.d("Saving token in prefs = ${response.token}", tag = TAG)
        println("Saving token in prefs = ${response.token}")
        prefs[TOKEN_KEY] = response.token
        true
    } catch (e: Throwable) {
        prefs.remove(TOKEN_KEY)
        false
    }

    suspend fun getBotList(): List<BotListItemResponse> {
        val response = client.get("$baseUrl/bot") {
            addAuthHeader()
            contentType(ContentType.Application.Json)
        }

        if (response.status == HttpStatusCode.Unauthorized) {
            println("Unauthorized")
        }

        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            listOf()
        }
    }

    private fun HttpRequestBuilder.addAuthHeader() {
        bearerAuth(prefs[TOKEN_KEY, ""])
    }

    fun hasToken() = prefs[TOKEN_KEY, ""].isNotEmpty()

    companion object {
        private const val TAG = "TgFaceWebClient"
        private const val TOKEN_KEY = "token"
    }
}

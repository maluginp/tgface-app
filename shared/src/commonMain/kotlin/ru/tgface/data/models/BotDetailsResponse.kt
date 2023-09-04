package ru.tgface.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BotDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("numberOfSubscribers")
    val numberOfSubscribers: Int,
    @SerialName("templates")
    val templates: List<TemplateDto>,
)

@Serializable
data class TemplateDto(
    @SerialName("id")
    val id: Int,
    @SerialName("guid")
    val guid: String,
    @SerialName("name")
    val name: String,
    @SerialName("document")
    val document: DocumentDto,
    @SerialName("type")
    val type: Int,
)

@Serializable
data class DocumentDto(
    @SerialName("guid")
    val guid: String,
    @SerialName("name")
    val name: String,
    @SerialName("photo")
    val photo: String?,
    @SerialName("message")
    val message: String?,
    @SerialName("file")
    val file: String?
)

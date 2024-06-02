package br.pucpr.authserver.noticias.controller.requests

import br.pucpr.authserver.noticias.Noticia
import jakarta.validation.constraints.NotBlank

data class CreateNoticiaRequest(
        @field:NotBlank
        val titulo: String?,
        @field:NotBlank
        val corpo: String?,
        @field:NotBlank
        val pathImg: String?,
        val userIds: Set<Long>?
) {
    fun toNoticia() = Noticia(
            titulo = titulo!!,
            corpo = corpo!!,
            pathImg = pathImg!!,
            userIds = userIds?.toMutableSet() ?: mutableSetOf()
    )
}

package br.pucpr.authserver.noticias.controller.requests

import jakarta.validation.constraints.NotBlank

data class PatchNoticiaRequest(
        @field:NotBlank
        val titulo: String?,
        @field:NotBlank
        val corpo: String?,
        @field:NotBlank
        val pathImg: String?,
        val userIds: Set<Long>?
)

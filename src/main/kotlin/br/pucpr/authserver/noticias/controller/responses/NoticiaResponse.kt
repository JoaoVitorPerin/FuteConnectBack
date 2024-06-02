package br.pucpr.authserver.noticias.controller.responses

import br.pucpr.authserver.noticias.Noticia

data class NoticiaResponse(
        val id: Long,
        val titulo: String,
        val corpo: String,
        val pathImg: String,
        val userIds: Set<Long>
) {
    constructor(noticia: Noticia) : this(noticia.id!!, noticia.titulo, noticia.corpo, noticia.pathImg, noticia.userIds)
}

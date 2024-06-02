package br.pucpr.authserver.noticias

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NoticiaRepository : JpaRepository<Noticia, Long> {
    fun findNoticiaById(id: Long): Noticia?
}
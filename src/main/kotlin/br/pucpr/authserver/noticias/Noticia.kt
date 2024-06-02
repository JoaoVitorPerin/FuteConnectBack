package br.pucpr.authserver.noticias

import jakarta.persistence.*

@Entity
@Table(name = "TblNoticia")
class Noticia(
    @Id @GeneratedValue
    var id: Long? = null,

    var titulo: String = "",

    var corpo: String = "",

    var pathImg: String = "",

    @ElementCollection
    @CollectionTable(
            name = "NoticiaUser",
            joinColumns = [JoinColumn(name = "idNoticia")]
    )
    @Column(name = "idUser")
    var userIds: MutableSet<Long> = mutableSetOf()
)
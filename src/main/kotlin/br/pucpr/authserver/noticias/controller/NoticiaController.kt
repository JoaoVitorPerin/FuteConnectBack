package br.pucpr.authserver.noticias.controller

import br.pucpr.authserver.noticias.NoticiaService
import br.pucpr.authserver.noticias.controller.requests.CreateNoticiaRequest
import br.pucpr.authserver.noticias.controller.requests.PatchNoticiaRequest
import br.pucpr.authserver.noticias.controller.responses.NoticiaResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/noticias")
class NoticiaController(val service: NoticiaService) {

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun insert(@Valid @RequestBody request: CreateNoticiaRequest): ResponseEntity<NoticiaResponse> {
        val noticia = service.insert(request.toNoticia())
        return ResponseEntity.status(HttpStatus.CREATED).body(NoticiaResponse(noticia))
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun update(
            @PathVariable id: Long,
            @Valid @RequestBody request: PatchNoticiaRequest
    ): ResponseEntity<NoticiaResponse> {
        val noticia = service.update(id, request.titulo!!, request.corpo!!, request.pathImg!!)
        if (request.userIds != null) {
            noticia.userIds.clear()
            noticia.userIds.addAll(request.userIds)
        }
        return ResponseEntity.ok(NoticiaResponse(noticia))
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    fun list(): ResponseEntity<List<NoticiaResponse>> {
        val noticias = service.findAll().map { NoticiaResponse(it) }
        return ResponseEntity.ok(noticias)
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    fun getById(@PathVariable id: Long): ResponseEntity<NoticiaResponse> {
        val noticia = service.findByIdOrNull(id)
        return noticia?.let { ResponseEntity.ok(NoticiaResponse(it)) }
                ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return if (service.delete(id)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

package br.pucpr.authserver.noticias

import br.pucpr.authserver.exception.BadRequestException
import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class NoticiaService(
        val repository: NoticiaRepository,
        val userRepository: UserRepository
) {

    fun insert(noticia: Noticia): Noticia {
        // Valida se os IDs dos usuários existem
        noticia.userIds.forEach { userId ->
            if (!userRepository.existsById(userId)) {
                throw BadRequestException("User ID $userId does not exist")
            }
        }

        return repository.save(noticia)
                .also { log.info("Noticia inserted: {}", it.id) }
    }

    fun update(id: Long, titulo: String, corpo: String, pathImg: String): Noticia {
        val noticia = findByIdOrThrow(id)
        if (noticia.titulo == titulo && noticia.corpo == corpo && noticia.pathImg == pathImg) {
            throw BadRequestException("No changes detected")
        }
        noticia.titulo = titulo
        noticia.corpo = corpo
        noticia.pathImg = pathImg
        return repository.save(noticia)
    }

    fun findAll(): List<Noticia> = repository.findAll()

    fun findByIdOrNull(id: Long) = repository.findById(id).getOrNull()

    private fun findByIdOrThrow(id: Long): Noticia =
            findByIdOrNull(id) ?: throw NotFoundException(id)

    fun delete(id: Long): Boolean {
        val noticia = findByIdOrNull(id) ?: return false
        repository.delete(noticia)
        log.info("Noticia deleted: {}", noticia.id)
        return true
    }

    companion object {
        private val log = LoggerFactory.getLogger(NoticiaService::class.java)
    }
}

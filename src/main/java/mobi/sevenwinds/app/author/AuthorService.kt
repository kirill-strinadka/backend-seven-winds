package mobi.sevenwinds.app.author

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object AuthorService {
    suspend fun addAuthor(body: AuthorRecordRequest): AuthorRecord = withContext(Dispatchers.IO) {
        transaction {
            val entity = AuthorEntity.new {
                this.fullName = body.fullName
                this.createdAt = DateTime.now()
            }
            return@transaction entity.toResponse()
        }
    }

    suspend fun getAuthors(): List<AuthorSimpleRecord> = withContext(Dispatchers.IO) {
        transaction {
            AuthorEntity.all().map { it.toSimpleResponse() }
        }
    }
}

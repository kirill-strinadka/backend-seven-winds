package mobi.sevenwinds.app.author

import com.papsign.ktor.openapigen.annotations.type.string.length.Length
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.path.normal.post
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import org.joda.time.DateTime

fun NormalOpenAPIRoute.author() {
    route("/author") {
        route("/add").post<Unit, AuthorRecord, AuthorRecordRequest>(info("Добавить автора")) { param, body ->
            respond(AuthorService.addAuthor(body))
        }

        get<Unit, List<AuthorSimpleRecord>>(info("Получить всех авторов")) {
            respond(AuthorService.getAuthors())
        }
    }
}

data class AuthorRecordRequest(
    @Length(min = 1, max = 255) val fullName: String,
)

data class AuthorRecord(
    @Length(min = 1, max = 255) val fullName: String,
    val createdAt: DateTime
)

// чтобы не выводить весь объект пользователю в json
data class AuthorSimpleRecord(
    @Length(min = 1, max = 255) val fullName: String,
    val createdAt: String
)



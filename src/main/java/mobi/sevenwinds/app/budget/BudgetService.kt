package mobi.sevenwinds.app.budget

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mobi.sevenwinds.app.author.AuthorEntity
import mobi.sevenwinds.app.author.AuthorTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object BudgetService {
    suspend fun addRecord(body: BudgetRecord): BudgetRecord = withContext(Dispatchers.IO) {
        transaction {
            val entity = BudgetEntity.new {
                this.year = body.year
                this.month = body.month
                this.amount = body.amount
                this.type = body.type
                this.author = body.author?.let { AuthorEntity.findById(it) }
            }
            return@transaction entity.toResponse()
        }
    }

    suspend fun getYearStats(param: BudgetYearParam): BudgetYearStatsResponse = withContext(Dispatchers.IO) {
        transaction {
            val baseQuery = BudgetTable
                .join(AuthorTable, JoinType.LEFT, additionalConstraint = { BudgetTable.author eq AuthorTable.id })
                .select { BudgetTable.year eq param.year }

            val query = tryFilterQueryByAuthorName(param, baseQuery);

            val total = query.count()
            val allData = BudgetEntity.wrapRows(query).map { it.toResponseWithAuthor() }
            val sumByType = allData.groupBy { it.type.name }.mapValues { it.value.sumOf { v -> v.amount } }


            val resultLimitedQuery = query
                .orderBy(BudgetTable.month to SortOrder.ASC, BudgetTable.amount to SortOrder.DESC)
                .limit(param.limit, param.offset)

            val limitedData = BudgetEntity.wrapRows(resultLimitedQuery).map { it.toResponseWithAuthor() }

            return@transaction BudgetYearStatsResponse(
                total = total,
                totalByType = sumByType,
                items = limitedData
            )
        }
    }

    fun tryFilterQueryByAuthorName(param: BudgetYearParam, baseQuery: Query): Query {
        if (!param.authorName.isNullOrEmpty()) {
            return baseQuery.andWhere {
                AuthorTable.fullName.lowerCase() like "%${param.authorName.toLowerCase()}%"
            }
        } else {
            return baseQuery
        }
    }

}
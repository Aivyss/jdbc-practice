package com.example.database_test2.domain

import com.example.database_test2.connection.DataSourceConnection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.sql.SQLException

/**
 * hikari pool를 이용한 커넥션
 */
@Repository
class MemberRepositoryV1 (
    @Qualifier("hikari") private val datasourceConnection: DataSourceConnection
) {

    fun removeAll() {
        val sql = "DELETE FROM MEMBER WHERE 1=1"
        val conn = datasourceConnection.getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.executeUpdate()

        datasourceConnection.close(pstmt, conn)
    }

    fun save(member: Member): Member {
        val sql = "INSERT INTO MEMBER(member_id, money) VALUES (?, ?)"
        val conn = datasourceConnection.getConnection()
        val pstmt = conn.prepareStatement(sql)

        pstmt.setString(1, member.memberId)
        pstmt.setInt(2, member.money)
        pstmt.executeUpdate()

        datasourceConnection.close(pstmt, conn)

        return member
    }

    fun update(memberId: String, money: Int) {
        val sql = "UPDATE MEMBER SET MONEY = ? WHERE 1=1 AND MEMBER_ID = ?"
        val conn = datasourceConnection.getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, money)
        pstmt.setString(2, memberId)
        pstmt.executeUpdate()

        datasourceConnection.close(pstmt, conn)
    }

    fun deleteOne(memberId: String) {
        val sql = "DELETE FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = datasourceConnection.getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, memberId)
        pstmt.executeUpdate()

        datasourceConnection.close(pstmt, conn)
    }

    fun findById(memberId: String): Member? {
        val sql = "SELECT * FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = datasourceConnection.getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, memberId)


        val rs = pstmt.executeQuery()

        return try {
            if (rs.next()) {
                Member(rs.getString("member_id"), rs.getInt("money"))
            } else {
                null
            }
        } catch (e: SQLException) {
            null
        } finally {
            datasourceConnection.close(pstmt, conn, rs)
        }
    }

    fun manyRepeatClose() {
        for (i: Int in 0 until  1500) {
            val conn = datasourceConnection.getConnection()
            val pstmt = conn.prepareStatement("select * from member")

            datasourceConnection.close(pstmt, conn)
        }
    }
}
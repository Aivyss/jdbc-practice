package com.example.database_test2.domain

import com.example.database_test2.connection.ConnectionManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.sql.SQLException

@Repository
class MemberRepositoryV2(
    @Qualifier("hikariManager") private val connectionManager: ConnectionManager,
) {
    private val conn = connectionManager.getConnection()

    fun removeAll() {
        val sql = "DELETE FROM MEMBER WHERE 1=1"
        val pstmt = conn.prepareStatement(sql)
        pstmt.executeUpdate()
    }

    fun save(member: Member): Member {
        val sql = "INSERT INTO MEMBER(member_id, money) VALUES (?, ?)"
        val pstmt = conn.prepareStatement(sql)

        pstmt.setString(1, member.memberId)
        pstmt.setInt(2, member.money)
        pstmt.executeUpdate()

        return member
    }

    fun update(memberId: String, money: Int) {
        val sql = "UPDATE MEMBER SET MONEY = ? WHERE 1=1 AND MEMBER_ID = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, money)
        pstmt.setString(2, memberId)
        pstmt.executeUpdate()
    }

    fun deleteOne(memberId: String) {
        val sql = "DELETE FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, memberId)
        pstmt.executeUpdate()
    }

    fun findById(memberId: String): Member? {
        val sql = "SELECT * FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
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
        }
    }
}
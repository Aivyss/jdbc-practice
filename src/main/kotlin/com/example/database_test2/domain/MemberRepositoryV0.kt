package com.example.database_test2.domain

import com.example.database_test2.connection.DataSourceConnection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.sql.SQLException

/**
 * jdbc DriverManager 사용
 */
@Repository
class MemberRepositoryV0(
    @Qualifier("driverManager") private val datasource: DataSourceConnection
) {

    fun removeAll() {
        val sql = "DELETE FROM MEMBER WHERE 1=1"
        val conn = datasource.getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.executeUpdate()

        datasource.close(pstmt, conn)
    }

    fun save(member: Member): Member {
        val sql = "INSERT INTO MEMBER(member_id, money) VALUES (?, ?)"
        val conn = datasource.getConnection()
        val pstmt = conn.prepareStatement(sql)

        pstmt.setString(1, member.memberId)
        pstmt.setInt(2, member.money)
        pstmt.executeUpdate()

        datasource.close(pstmt, conn)

        return member
    }

    fun update(memberId: String, money: Int) {
        val sql = "UPDATE MEMBER SET MONEY = ? WHERE 1=1 AND MEMBER_ID = ?"
        val conn = datasource.getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, money)
        pstmt.setString(2, memberId)
        pstmt.executeUpdate()

        datasource.close(pstmt, conn)
    }

    fun deleteOne(memberId: String) {
        val sql = "DELETE FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = datasource.getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, memberId)
        pstmt.executeUpdate()

        datasource.close(pstmt, conn)
    }

    fun findById(memberId: String): Member? {
        val sql = "SELECT * FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = datasource.getConnection()
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
            datasource.close(pstmt, conn, rs)
        }
    }
}
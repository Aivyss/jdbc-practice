package com.example.database_test2.domain

import com.example.database_test2.connection.DatabaseClosable
import com.example.database_test2.connection.DatabaseConnection.closeV0
import com.example.database_test2.connection.DatabaseConnection.getConnectionWithDriverManager
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

data class Member(val memberId: String, val money: Int)

/**
 * jdbc DriverManager 사용
 */
class MemberRepositoryV0 {
    fun removeAll() {
        val sql = "DELETE FROM MEMBER WHERE 1=1"
        val conn = getConnectionWithDriverManager()
        val pstmt = conn.prepareStatement(sql)
        pstmt.executeUpdate()

        closeV0(pstmt, conn)
    }

    fun save(member: Member): Member {
        val sql = "INSERT INTO MEMBER(member_id, money) VALUES (?, ?)"
        val conn = getConnectionWithDriverManager()
        val pstmt = conn.prepareStatement(sql)

        pstmt.setString(1, member.memberId)
        pstmt.setInt(2, member.money)
        pstmt.executeUpdate()

        closeV0(pstmt, conn)

        return member
    }

    fun update(memberId: String, money: Int) {
        val sql = "UPDATE MEMBER SET MONEY = ? WHERE 1=1 AND MEMBER_ID = ?"
        val conn = getConnectionWithDriverManager()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, money)
        pstmt.setString(2, memberId)
        pstmt.executeUpdate()

        closeV0(pstmt, conn)
    }

    fun deleteOne(memberId: String) {
        val sql = "DELETE FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = getConnectionWithDriverManager()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, memberId)
        pstmt.executeUpdate()

        closeV0(pstmt, conn)
    }

    fun findById(memberId: String): Member? {
        val sql = "SELECT * FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = getConnectionWithDriverManager()
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
            closeV0(pstmt, conn, rs)
        }
    }
}

/**
 * DataSource를 이용한 커넥션
 */
class MemberRepositoryV1 (private val dataSource: DataSource): DatabaseClosable {
    private val logger = LoggerFactory.getLogger(MemberRepositoryV1::class.java)

    private fun getConnection(): Connection {
        logger.info("=================[GET CONNECTION]=================")
        val conn = dataSource.getConnection()
        logger.info("connection = $conn, class = ${conn::class.java}")

        return conn
    }

    fun removeAll() {
        val sql = "DELETE FROM MEMBER WHERE 1=1"
        val conn = getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.executeUpdate()

        close(pstmt, conn)
    }

    fun save(member: Member): Member {
        val sql = "INSERT INTO MEMBER(member_id, money) VALUES (?, ?)"
        val conn = getConnection()
        val pstmt = conn.prepareStatement(sql)

        pstmt.setString(1, member.memberId)
        pstmt.setInt(2, member.money)
        pstmt.executeUpdate()

        close(pstmt, conn)

        return member
    }

    fun update(memberId: String, money: Int) {
        val sql = "UPDATE MEMBER SET MONEY = ? WHERE 1=1 AND MEMBER_ID = ?"
        val conn = getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, money)
        pstmt.setString(2, memberId)
        pstmt.executeUpdate()

        close(pstmt, conn)
    }

    fun deleteOne(memberId: String) {
        val sql = "DELETE FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = getConnection()
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, memberId)
        pstmt.executeUpdate()

        close(pstmt, conn)
    }

    fun findById(memberId: String): Member? {
        val sql = "SELECT * FROM MEMBER WHERE 1=1 AND MEMBER_ID = ?"
        val conn = getConnection()
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
            close(pstmt, conn, rs)
        }
    }
}
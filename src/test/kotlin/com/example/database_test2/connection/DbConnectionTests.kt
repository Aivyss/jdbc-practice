package com.example.database_test2.connection

import com.example.database_test2.domain.Member
import com.example.database_test2.domain.MemberRepositoryV0
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DbConnectionTests {
    private val memberRepository = MemberRepositoryV0()

    @Test
    fun `db connection test`() {
        // * when
        val connection = DatabaseConnection.getConnectionWithDriverManager()

        // * then
        assertThat(connection).isNotNull
    }

    @Test
    fun `datasource test - DriverManagerDataSource`() {
        // * when
        val connection = DatabaseConnection.getConnectionWithDriverManagerDataSource()

        // * then
        assertThat(connection).isNotNull
    }
    
    @Test
    fun `datasource test - Hikari connection pool`() {
        // * when
        val connection = DatabaseConnection.getConnectionWithHikariConnectionPool()

        // * then
        assertThat(connection).isNotNull

    }

    @Test
    fun `create, select test`() {
        // * create
        memberRepository.removeAll()

        val memberId = "id_test"
        val member = Member(memberId, 10000)

        // * when
        val saveMember = memberRepository.save(member)
        val findMember = memberRepository.findById(memberId)

        // * then
        assertThat(saveMember).isSameAs(member)
        assertThat(findMember).isEqualTo(member)
    }

    @Test
    fun `update test`() {
        // * given
        memberRepository.removeAll()

        val memberId = "id_test"
        val member = Member(memberId, 10000)
        memberRepository.save(member)

        // * when
        val updateMoney = 20000
        memberRepository.update(memberId, updateMoney)

        // * then
        val findMember = memberRepository.findById(memberId)
        assertThat(findMember).isNotNull
        assertThat(findMember?.money).isEqualTo(updateMoney)
    }

    @Test
    fun `delete test`() {
        // * given
        memberRepository.removeAll()

        val memberId = "id_test"
        val member = Member(memberId, 10000)
        memberRepository.save(member)

        // * when
        memberRepository.deleteOne(memberId)

        // * then
        assertThat(memberRepository.findById(memberId)).isNull()
    }
}
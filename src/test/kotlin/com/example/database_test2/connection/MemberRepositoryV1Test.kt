package com.example.database_test2.connection

import com.example.database_test2.domain.Member
import com.example.database_test2.domain.MemberRepositoryV1
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberRepositoryV1Test {
    @Autowired
    private lateinit var memberRepository: MemberRepositoryV1

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
        Assertions.assertThat(saveMember).isSameAs(member)
        Assertions.assertThat(findMember).isEqualTo(member)
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
        Assertions.assertThat(findMember).isNotNull
        Assertions.assertThat(findMember?.money).isEqualTo(updateMoney)
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
        Assertions.assertThat(memberRepository.findById(memberId)).isNull()
    }
}
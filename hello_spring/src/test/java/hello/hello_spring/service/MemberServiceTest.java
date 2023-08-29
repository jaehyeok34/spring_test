package hello.hello_spring.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    
    
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring3");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void overlapMemberException() {
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        try {
            memberService.join(member1);
            memberService.join(member2);
            fail();
        } catch(Exception e) {
            System.out.println("중복 생성 막음");
        }

        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

    @Test
    void testJoin() {
    }
}

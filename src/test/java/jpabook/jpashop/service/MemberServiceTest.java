package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    //@Rollback(value = false) insert문을 보고 싶다면 롤백을 false 로 줘야한다. (플러시를 안하기 때문) 혹은 em 주입하고 수동으로 플러시 해주면 된다.
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.find(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when  junit5 부터는 expected 를 이렇게 테스트 해야한다.
        Throwable e = assertThrows(IllegalArgumentException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });

        //then
        assertEquals("이미 존재하는 회원입니다.", e.getMessage());
        //fail("예외가 발생해야 한다.");   // fail 은 이 코드를 타면 실패를 발생시킴 .
    }

}
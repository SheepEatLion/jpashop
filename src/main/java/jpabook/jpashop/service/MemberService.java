package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;  //필드에 오토와이어드는 테스트코드 작성에 어려움.

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member){
        // EXCEPTION 멤버의 네임을 유니크제약 조건으로 걸어줘야한다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.find(memberId);
    }
}

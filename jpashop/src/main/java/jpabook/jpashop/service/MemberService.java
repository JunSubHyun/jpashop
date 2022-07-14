package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    
    //@AllArgsConstructor
    //생성자 필드를 자동으로 생성시켜주는 어노테이션
    
    //@RequiredArgsConstructor
    //final이 있는 필드만 가지고 생성자 필드를 생성시켜주는 어노테이션
    
//    @Autowired  생성자 인젝션 생성으로 필요없어짐 -> 대신 final 로 변경 (final 로 하면 에러체크 가능)
    private final MemberRepository memberRepository;

//        //세터 인젝션
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//        // 장점 : 테스트 코드에서 Mock 같은 걸 직접 주입할 수 있다.
//
//        //단점 : 어플리케이션 실행 중간에 수정가능하다.
    //    //요즘 잘 안씀
//    }

    //요즘 많이씀
    //생성자 인젝션
    //자동으로  Autowired  을 해줌
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     */
    @Transactional  //메서드에 설정하면 이게 우선권을 가진다
    public Long join(Member member){
        //읽기가 아닌 쓰기에는 readOnly = true 를 두면 안됨

        validateDulidcateMember(member);        //중복 회원 검증
        
        memberRepository.save(member);
        
        return member.getId();
    }

    /**
     * 중복 회원 검증
     */
    private void validateDulidcateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 단일 회원 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}

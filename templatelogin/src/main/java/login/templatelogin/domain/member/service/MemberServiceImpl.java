package login.templatelogin.domain.member.service;

import login.templatelogin.domain.member.Member;
import login.templatelogin.domain.member.dto.MemberInfoDto;
import login.templatelogin.domain.member.dto.MemberSignUpDto;
import login.templatelogin.domain.member.dto.MemberUpdateDto;
import login.templatelogin.domain.member.repository.MemberRepository;
import login.templatelogin.domain.member.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);
        if (memberRepository.findByUsername(memberSignUpDto.username()).isPresent()) {

            throw new Exception("이미 존재하는 회원입니다.");
        }
        memberRepository.save(member);


    }

    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member updateMember = memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(memberUpdateDto.age().isPresent()){
            updateMember.updateAge(memberUpdateDto.age().get());
        }
        if(memberUpdateDto.name().isPresent()){
            updateMember.updateName(memberUpdateDto.name().get());
        }
        if(memberUpdateDto.nickName().isPresent()){
            updateMember.updateName(memberUpdateDto.nickName().get());
        }
        //memberUpdateDto.age().ifPresent(updateMember::updateAge); 이런식으로 바꿀수 있음

    }

    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));
        if(!member.matchPassword(passwordEncoder,checkPassword)){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        member.updatePassword(passwordEncoder, toBePassword);

    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));
        if(!member.matchPassword(passwordEncoder,checkPassword)){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        memberRepository.delete(member);

    }

    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member member = memberRepository.findById(id).orElseThrow(() -> new Exception("회원이 없습니다."));
        return MemberInfoDto.of(member);
    }

    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 없습니다."));
        return MemberInfoDto.of(member);

    }
}

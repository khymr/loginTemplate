package login.templatelogin.domain.member.service;

import login.templatelogin.domain.member.dto.MemberInfoDto;
import login.templatelogin.domain.member.dto.MemberSignUpDto;
import login.templatelogin.domain.member.dto.MemberUpdateDto;

/**
 *회원 가입
 * 정보 수정
 * 회원 탈퇴(withdraw)
 * 정보 조회
 */
public interface MemberService {

    void signUp(MemberSignUpDto memberSignUpDto) throws Exception;

    void update(MemberUpdateDto memberUpdateDto) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    MemberInfoDto getInfo(Long id) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;


}

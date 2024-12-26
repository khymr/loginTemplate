package login.templatelogin.domain.member.dto;

import login.templatelogin.domain.member.Member;

public record MemberInfoDto(String name,
                            String nickName,
                            String username,
                            Integer age) {
    public static MemberInfoDto of(Member member){
        return new MemberInfoDto(member.getName(), member.getNickName(), member.getUsername(), member.getAge());
    }

}

package com.aen.connecttomysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersService {

    private final MemberRepository memberRepository;

    @Autowired
    public MembersService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MembersEntity addMember(MembersEntity member) {
        return memberRepository.save(member);
    }
}

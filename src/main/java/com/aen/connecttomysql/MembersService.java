package com.aen.connecttomysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembersService {

    private final MemberRepository memberRepository;

    @Autowired
    public MembersService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MembersEntity> getAllMembers() {
        List<MembersEntity> membersList = new ArrayList<>();
        memberRepository.findAll().forEach(membersList::add);
        return membersList;
    }

    public MembersEntity addMember(MembersEntity member) {
        return memberRepository.save(member);
    }
}

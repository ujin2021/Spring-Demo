package com.jpademo.service;

import com.jpademo.domain.Member;
import com.jpademo.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findMembers() {
        return memberRepository.findAll(); // findAll은 원래 있는거니까 바로 적어준다
    }

    public Optional<Member> findOne(String memberName) { // optional 은 null 처리해주는 것
        return memberRepository.findByName(memberName);
    }
}

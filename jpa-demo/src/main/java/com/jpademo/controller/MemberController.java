package com.jpademo.controller;

import com.jpademo.domain.Member;
import com.jpademo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController // rest api
@RequestMapping("members") // 공통 url
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/{memberName}")
    public ResponseEntity<Member> getMemberByName(@PathVariable String memberName) {
        Optional<Member> member = memberService.findOne(memberName);
        return new ResponseEntity<Member>(member.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.findMembers();
        return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
    }
}

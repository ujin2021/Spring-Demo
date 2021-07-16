package com.jpademo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Member {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // key 생성을 DB에게 위임
    private long id;

    private String name;
}

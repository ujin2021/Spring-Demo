package com.example.jwtdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity // db table과 1:1로 매핑되는 객체
@Table(name = "user") // table명을 user로 지정
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore // serialize, deserialize 무시
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가
    private Long id;

    @Column(name = "user_id", length = 50, unique = true)
    private String userId;

    @JsonIgnore
    @Column(name = "password", length = 100)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<com.example.jwtdemo.entity.Authority> authorities;
}

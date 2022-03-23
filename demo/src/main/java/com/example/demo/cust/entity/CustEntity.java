package com.example.demo.cust.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // Builder annotation 사용 시 필요
@Getter
@Builder
@Table(name = "CUST_TB")
public class CustEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

}

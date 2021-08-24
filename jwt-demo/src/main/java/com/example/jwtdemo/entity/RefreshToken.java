package com.example.jwtdemo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String key; // userId
    private String value; // refresh token

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}

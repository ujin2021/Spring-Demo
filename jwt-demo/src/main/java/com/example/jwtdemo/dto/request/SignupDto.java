package com.example.jwtdemo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String userId;

    // JsonProperty access 참고 : https://eglowc.tistory.com/28
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // deserialize 시 에만 접근 가능
    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}

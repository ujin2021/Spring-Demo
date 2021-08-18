package com.example.jwtdemo.repository;

import com.example.jwtdemo.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities") // Lazy조회가 아닌 Eager조회로 authorities를 같이 가져온다
    Optional<User> findOneWithAuthoritiesByUserId(String userId); // username을 기준으로 user의 권한정보도 같이 가져온다
}

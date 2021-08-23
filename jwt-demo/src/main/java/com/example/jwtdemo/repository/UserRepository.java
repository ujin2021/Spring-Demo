package com.example.jwtdemo.repository;

import com.example.jwtdemo.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // join해서 한번의 query로 load할 때 사용
    @EntityGraph(attributePaths = "authorities") // Lazy조회가 아닌 Eager조회로 authorities를 같이 가져온다
    Optional<User> findOneWithAuthoritiesByUserId(String userId); // userId를 기준으로 user의 권한정보도 같이 가져온다
    Optional<User> findByUserId(String userId);
}

/*
Lazy, Eager 조회 차이 (fetch)
참고 :  https://endless-learn-code.tistory.com/22
Lazy : 데이터를 가져다 쓰는 시점에서 쿼리를 실행
    - 사용하지 않는 필드를 조회하는 API를 호출할땐 쿼리가 실행되지 않는다
Eager : 데이터를 가져올때 조인해서 가져온다.
    - findAll method엔 적용되지 않는다
    - @EntityGraph annotation을 써야한다
*/
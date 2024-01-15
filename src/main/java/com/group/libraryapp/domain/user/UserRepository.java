package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //repository 어노테이션 없이, 그냥 JpaRepository 상속한 것만으로 스피링 빈으로 관리가 된다

    Optional<User> findByName(String name);
    //이름을 기준으로 들어온 유저가 존재하면 유저 객체(반환타입)를, 존재하지 않으면 null을 반환
    //find : 1개의 데이터만 가져옴, 따라서 반환되는 타입이 User 인 것임
    //By : 뒤에 붙는 필드이름으로 SLEECT 쿼리의 WHERE 문이 작성된다.

}

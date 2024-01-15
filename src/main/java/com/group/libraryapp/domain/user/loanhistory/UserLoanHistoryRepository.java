package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

    boolean existsByBookNameAndIsReturn(String name, boolean isReturn);
    //해당 함수 실행 시
    //select * from user_loan_history where book_name = ? and is_return = ?

    //대출기록을 찾는 함수 생성
    Optional<UserLoanHistory> findByUserIdAndBookName(long userId, String bookName);
}

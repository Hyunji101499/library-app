package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id = null;

    //1. saveUser API가 사용되어 user가 저장되어야 하면 User 객체를 만들어서 실제 List에 저장
    @Column(nullable = false, length = 20, name = "name")//name varchar(20)
    private String name;
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();
    //유저와 연결된 유저 대출기록까지 이어지고, 디비상에서 유저가 삭제될 때 UserLoanHistory가 삭제됨

    protected User(){}

    //user 객체를 만들 때 생성자에 넣어주자
    public User(String name, Integer age) throws IllegalAccessException {

        if(name == null || name.isBlank()){
            throw new IllegalAccessException(String.format("잘못된 name이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Long getId() { return id;}

    public Integer getAge() {
        return age;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void removeOneHistory(){
        userLoanHistories.removeIf(history -> "책1".equals(history.getBookName()));
    }

    public void loanBook(String bookName){
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName){
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))//해당 책 이름을 가진 기록을 찾아주자
                .findFirst()//파라미터로 받은 값과 같은 것이 걸리는 첫 번째를 가져와서
                .orElseThrow(IllegalArgumentException::new);//findFirst결과가 옵셔널이므로 예외처리 가능
       targetHistory.doReturn();
    }
}

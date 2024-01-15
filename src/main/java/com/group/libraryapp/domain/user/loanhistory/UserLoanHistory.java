package com.group.libraryapp.domain.user.loanhistory;
import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  = null;
    // User와 UserLoanHistory가 서로를 알도록 객체지향적으로 바꿔주자
    //@JoinColumn(nullable = false)
    @ManyToOne
    private User user;
    // private Long userId;
    private String bookName;
    private boolean isReturn;
    //boolean field를 사용하면 db에 0이 들어가면 false, 1이 들어가면 true

//    public UserLoanHistory(Long user_id, String bookName, boolean isReturn) {
//        this.user_id = user_id;
//        this.bookName = bookName;
//        this.isReturn = isReturn;
//    }
    //public UserLoanHistory(Long userId, String bookName) {
    public UserLoanHistory(User user, String bookName) {
        //this.userId = userId;
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }

    protected UserLoanHistory(){

    }

    public void doReturn(){
        this.isReturn =  true;
    }

    public String getBookName() {
        return this.bookName;
    }
}

package com.group.libraryapp.service.book;

//import com.group.libraryapp.repository.book_tmp.BookRepository;
import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BookService {

    //private final BookMemoryRepository bookMemoryRepository
    //        = new BookMemoryRepository();
    //private final BookMySqlRepository bookMySqlRepository
    //        = new BookMySqlRepository();

    //private final BookRepository bookRepository = new BookMemoryRepository();
    //private final BookRepository bookRepository = new BookMySqlRepository();
    //인터페이스 사용 시 어떤 구현체를 사용할 것인지만 변경해주면 됨

    //어노테이션 사용, 생성자를 통해 bookRepository를 주입 받음
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository,UserRepository userRepository) {

        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository =  userLoanHistoryRepository;
        this.userRepository =  userRepository;
    }
    //spring 빈을 통해 주입 받게 해줌
    @Transactional
    public void saveBook(@RequestBody BookCreateRequest request){
        //bookMemoryRepository.saveBook();
        //bookMySqlRepository.saveBook();

       // bookRepository.saveBook();
        bookRepository.save(new Book(request.getName()));
        //만들어준 생성자에 가져온 이름을 넣어 북 객체를 만들어주고
        //만들어진 북 객체를 sql 작성 없이 save에 넣어주면 됨
    }
    @Transactional
    public void loanBook(BookLoanRequest request){
        //현재 대출중인지 상태 확인(이미 대출 된 상태라면 막아주자)
        //1.책 정보를 가져옴(book_name을 기준으로)

        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);
        //책이 없는 경우는 예외처리

        //2.대출기록 정보를 확인해서 대출중인지 확인
        //대출 정보 : user_loan_repository가 필요 , 의존성 추가
        //3.대출중이라면 예외를 발생시킴
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
        //false : 대여중인 상태 -> 해당 인자 값을 줬을 때 전체 결과가 true
            throw new IllegalArgumentException("이미 대출되어있는 책 입니다.");
        }

        //4.유저 정보를 가져온다.(대출되지 않았다면, 정보를 가져와 대출기록을 만들어주는 것)
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        //유저도 존재하며 책도 있으며, 현재 대출중인 상태도 아님
        //리팩토링
        user.loanBook(book.getName());

        //5.유저 정보와 책 정보를 기반으로 UserLoanHistory를 저장
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));
        //userLoanHistoryRepository.save(new UserLoanHistory(user.getId(), book.getName()));
        // userLoanHistoryRepository.save(new UserLoanHistory(user.getId(), book.getName(), false));
        //처음 빌리는 값이므로 false 입력, 해당 객채를 저장해주자
        //그럼 Spring Data JPA가 이 정보를 SQL로 바꿔 저장해줄 것
    }

    @Transactional
    public void returnBook(BookReturnRequest request){
        //유저 정보를 찾아주자.
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
        System.out.println("Hello");

        ////유저 아이디와 주어지는 책 이름을 통해 대출기록을 찾아주자.
       // UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
       //         .orElseThrow(IllegalArgumentException::new);

       // //해당 대출기록을 반납처리 해주자.(UserLoanHistory의 isreturn을 true 처리)
       // history.doReturn();
       // //userLoanHistoryRepository.save(history);
       // //원래하면 변경된 객채의 값을 다시 업데이트 해줘야하지만, @Transactional을 사용해 영속성 켄텍스트가 존재하고
       // //변경감지가능으로 UserLoanHistory(@Entity) 객체에 변경이 일어나면 자동으로 업데이트 해줌

        //<리팩토링>
        user.returnBook(request.getBookName());
        //유저서비스에서는 유저만을 가지고 데이터를 처리할 수 있어짐
    }
}

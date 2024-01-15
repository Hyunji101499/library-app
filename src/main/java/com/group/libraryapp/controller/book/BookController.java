package com.group.libraryapp.controller.book;

import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.group.libraryapp.service.book.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    //private final BookService bookService = new BookService();

    //@Service어노테이션 사용 -> 둘 다 빈이므로 컨테이너가
    // 생성자를 통해 스프링 빈인 북서비스를 주입해줌
    public final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/book")
    public void saveBook(@RequestBody BookCreateRequest request){

        //BookCreateRequest를 매개변수로 써서 Http Body JSON을 통해 들어온 name이 BookCreateRequest의 name과
        //맵핑되도록 해줌, 그리고 책을 만들 떄 해당 request를 전달해줌
        bookService.saveBook(request);
    }

    @PostMapping("/book/loan")
    public void loanBook(@RequestBody BookLoanRequest request){
        bookService.loanBook(request);
    }

    @PutMapping("/book/return")
    public void returnBook(@RequestBody BookReturnRequest request){
    //BookLoanRequest가 유저이름과 책이름을 똑같이 받으므로 잘 동작할 것 하지만 유연성을 위해 DTO 생성
        bookService.returnBook(request);
    }

}

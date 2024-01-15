package com.group.libraryapp.repository.book_tmp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
@Primary
@Repository
public class BookMySqlRepository implements BookRepository{

    //public void saveBook(){
    //
    //}
    @Override
    public void saveBook() {
        System.out.println("BookMySqlRepository");
    }
}

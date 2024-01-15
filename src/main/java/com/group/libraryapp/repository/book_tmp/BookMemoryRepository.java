package com.group.libraryapp.repository.book_tmp;

import org.springframework.stereotype.Repository;

//@Primary
@Repository
public class BookMemoryRepository implements BookRepository{

    //private final List<Book> books = new ArrayList<Book>();
    //public void saveBook(){
        //books.add(new Book());
    //}

    @Override
    public void saveBook() {
        System.out.println("BookMemoryRepository");
    }
}

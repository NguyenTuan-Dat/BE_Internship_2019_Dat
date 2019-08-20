package com.dat.book_management.service;

import com.dat.book_management.models.Book;
import com.dat.book_management.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooksEnabled(Boolean enabled){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Book> bookList = bookRepository.findByEnabled(enabled);
        return bookList;
    }

    public Book postBook(Book book){
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());
        bookRepository.save(book);
        return book;
    }

    public Book getBook(int id){
        Book book = bookRepository.findById(id).get();
        return book;
    }

    public List<Book> getMyBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return bookRepository.findByUserEmail(currentPrincipalName);
    }

    public Book updateBook(Book book){
        book.setUpdatedAt(new Date());
        book = bookRepository.save(book);
        return book;
    }

    public void deteleBook(int id){
        bookRepository.deleteById(id);
    }

    public List<Book> search(String key){
        List<Book> booksByTitle = bookRepository.findByTitleContaining(key);
        List<Book> booksByAuthor = bookRepository.findByAuthorContaining(key);
        List<Book> result = new ArrayList<>();
        result.addAll(booksByTitle);
        for(Book book : booksByAuthor){
            Boolean add = true;
            for(Book book1 : booksByTitle) {
                if(book == book1){
                    add = false;
                    break;
                }
            }
            if(add) result.add(book);
        }

        return result;
    }
}
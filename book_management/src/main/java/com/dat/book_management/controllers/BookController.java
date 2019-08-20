package com.dat.book_management.controllers;

import com.dat.book_management.models.Book;
import com.dat.book_management.models.Comment;
import com.dat.book_management.service.BookService;
import com.dat.book_management.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Book> getBooksEnabled(){
        return bookService.getBooksEnabled(true);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/disable-books")
    public List<Book> getDisableBooks(){
        return bookService.getBooksEnabled(false);
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String key){
        return bookService.search(key);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id){
        return bookService.getBook(id);
    }

    @GetMapping("/user")
    public List<Book> getMyBooks(){
        return bookService.getMyBooks();
    }

    @PostMapping
    public Book postBook(@Valid @RequestBody Book book){
        return bookService.postBook(book);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

//    @PutMapping("/books")
//    public void enableBook(@RequestBody List<Book> books){
//        for(Book book : books){
//            bookRepository.save(book);
//        }

//    }

    @DeleteMapping
    public void deteleBook(@RequestParam int id){
        bookService.deteleBook(id);
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam int id){
        return commentService.getComments(id);
    }

    @PostMapping("/comments")
    public Comment postComment(@RequestBody Comment comment){
        return commentService.postComment(comment);
    }
}

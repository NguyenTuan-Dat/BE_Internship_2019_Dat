package com.dat.book_management.controllers;

import com.dat.book_management.DTO.BookDTO;
import com.dat.book_management.DTO.CommentDTO;
import com.dat.book_management.models.Book;
import com.dat.book_management.models.Comment;
import com.dat.book_management.service.BookService;
import com.dat.book_management.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @GetMapping("/getPage")
    public List<Book> getPage(@RequestParam int page, @RequestParam int items, @RequestParam String sortBy){
        List<Book> bookList = new ArrayList<>();
        List<Book> books = getBooksEnabled();
        if(sortBy.equals("author")){
            bookService.sortByAuthor(books);
        }if(sortBy.equals("title")){
            bookService.sortByTitle(books);
        }if(sortBy.equals("year")){
            bookService.sortByYear(books);
        }
        int n = (page+1)*items;
        if(n>books.size()) n=books.size();
        for(int i=page*items; i<n; i++) {
            bookList.add(books.get(i));
        }


        return bookList;
    }

    @PostMapping
    public Book postBook(@Valid @RequestBody BookDTO book){
        return bookService.postBook(book);
    }

    @PutMapping
    public Book updateBook(@Valid @RequestBody BookDTO book){
        return bookService.updateBook(book);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/enable")
    public void enableBook(@Valid @RequestBody List<BookDTO> books){
        bookService.enableBook(books);
    }

    @DeleteMapping
    public void deteleBook(@RequestParam int id){
        bookService.deteleBook(id);
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam int id){
        return commentService.getComments(id);
    }

    @PostMapping("/comments")
    public Comment postComment(@Valid @RequestBody CommentDTO comment){
        return commentService.postComment(comment);
    }
}

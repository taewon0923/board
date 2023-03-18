package com.project.board.author.service;

import com.project.board.author.domain.Author;
import com.project.board.author.service.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public void save(Author author){
        authorRepository.save(author);
    }

    public Author findByEmail(String email){
        return authorRepository.findByEmail(email);
    }

    public Optional<Author> findById(Long id) throws Exception {
        // Day 35
//        try{
//            return authorRepository.findById(id).orElseThrow(Exception::new);
//        }catch(Exception e){
//            throw new Exception("Not Found Exception");
//        }

        return authorRepository.findById(id);
    }

    public List<Author> findAllFetchJoin(){
        return authorRepository.findAllFetchJoin();
    }



}

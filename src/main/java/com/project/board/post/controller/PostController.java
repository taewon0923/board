package com.project.board.post.controller;

import com.project.board.author.domain.Author;
import com.project.board.author.service.AuthorService;
import com.project.board.post.domain.Post;
import com.project.board.post.domain.PostDto;
import com.project.board.post.service.PostService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PostController {

    private final PostService postService;
    private final AuthorService authorService;

    @Autowired
    public PostController(PostService postService, AuthorService authorService) {
        this.postService = postService;
        this.authorService = authorService;
    }

    @GetMapping("/post/list")
    public String postList(Model model){
        model.addAttribute("posts", this.postService.findByScheduled());
        return "/post/postList";
    }

    @GetMapping("/post/postCreateform")
    public String postCreateform(){
        return "/post/postCreate";
    }

    @PostMapping("/post/create")
    public String create(PostDto postDto){
        LocalDateTime dateTime = LocalDateTime.now();
        if(postDto.getScheduled()!=null){
            String str = postDto.getScheduledTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime=LocalDateTime.parse(str,formatter);
        }
        Author author = authorService.findByEmail(postDto.getEmail());

        Post post = Post.builder().title(postDto.getTitle())
                .contents(postDto.getContents())
                .author(author)
                .scheduled(postDto.getScheduled())
                .scheduledTime(dateTime)
                .build();

        postService.save(post);

        return "redirect:/post/list";
    }

    @GetMapping("/post/findById")
    public String postDetail(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("post", this.postService.findById(id));

        return "/post/postDetail";
    }

    @GetMapping("/post/Delete")
    public String postDelete(@RequestParam(value = "id")Long id){
        this.postService.delete(id);
        return "redirect:/post/list";
    }

    // ??????????????? delete ????????? ?????? ??? @GetMaipping??? ???????????? ?????? @DeleteMapping??? ????????????
    // ?????? ??? ???????????? ?????????????????? ??????????????? ????????? get???????????? ??????????????? ?????? ????????? DeleteMapping??????
    // ?????? ?????? ????????? ?????? ????????? ?????? ?????? ?????? ?????? ?????????????????? ????????? ?????? ????????? ?????? ???????????? ?????? ??????
//    @DeleteMapping("/post/delete")
//    public String postDelete(@RequestParam(value = "id")Long id){
//        this.postService.delete(id);
//        return "redirect:/";
//    }

}

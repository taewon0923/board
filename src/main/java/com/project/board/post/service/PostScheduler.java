package com.project.board.post.service;


import com.project.board.post.domain.Post;
import com.project.board.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class PostScheduler {
    private final PostRepository postRepository;

    public PostScheduler(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @Scheduled(cron="0 0/1 * * * *") //초 분 시 일 월 요일
    public void postSchedule(){
//        1.현재 예약돼있는 건들은 sheduled에 checked이므로 checked인 건들만 조회
        //2.현재시간보다 sheduled뒤처진 post건들은 scheduled를 null로 세팅한다
//          특정건들에 대해서 repository.save for문을 돌려가면서
//        3.repository.save 에서 기존에 데이터가 있으면 update 없으면 알아서 insert가 된다
        Post post=postRepository.findById(1L).orElse(null);
        post.getScheduledTime().isBefore(LocalDateTime.now());
        postRepository.save(post);
        List<Post> posts=postRepository.findByScheduled("checked");
        for(Post a : posts){
            if(a.getScheduledTime().isBefore(LocalDateTime.now())){
                a.setScheduled(null);
                postRepository.save(a);
            }
        }
    }
}

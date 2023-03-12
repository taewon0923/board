package com.project.board.post.service;

import com.project.board.post.domain.Post;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostScheduler {
    @Scheduled(cron="0 0/1 * * * *") //초 분 시 일 월 요일
    public void postSchedule(){
        //현재시간보다 뒤처진 post건들은 scheduled를 null로 세팅한다
    }
}

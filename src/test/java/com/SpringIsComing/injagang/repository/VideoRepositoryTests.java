package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Video;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class VideoRepositoryTests {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private InterviewRepository interviewRepository;

    @Test
    public void insertVideos() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Interview interview = interviewRepository.findById((long)i).get();

            Video video = Video.builder()
                    .videoName("Sample video..."+i)
                    .videoURL("/test.mp4")
                    .interview(interview)
                    .date(LocalDateTime.now())
                    .build();

            videoRepository.save(video);
        });
    }
}

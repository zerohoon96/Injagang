package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//면접 영상 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIDEO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INTERVIEW_ID")
    private Interview interview;

    @OneToMany(mappedBy = "video")
    @Builder.Default
    private List<InterviewFeedback> feedbacks = new ArrayList<>();

    private String videoURL; //영상 url

    private String videoName; //영상 제목

    private LocalDateTime date;

    public void setVideoName(String str) {
        this.videoName = str;
    }
}

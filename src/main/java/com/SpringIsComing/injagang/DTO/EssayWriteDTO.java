package com.SpringIsComing.injagang.DTO;

import com.SpringIsComing.injagang.Entity.EssayContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayWriteDTO {
    private Long id;
    private String essayTitle;
    private String templateTitle;
    private Integer access;
    //tc : template contents, dc : direct contents
    private List<EssayContent> tc, dc;
}

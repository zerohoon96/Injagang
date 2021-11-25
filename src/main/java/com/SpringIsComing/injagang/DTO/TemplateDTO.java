package com.SpringIsComing.injagang.DTO;

import com.SpringIsComing.injagang.Entity.EssayTemplateContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDTO {
    private List<String> templateTitle;
    private List<List<String>> questions;
}

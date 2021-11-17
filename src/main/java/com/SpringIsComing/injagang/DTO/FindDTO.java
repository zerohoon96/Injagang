package com.SpringIsComing.injagang.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FindDTO {

    @NotEmpty(message = "이메일을 다시 확인해주세요.")
    private String email;

}

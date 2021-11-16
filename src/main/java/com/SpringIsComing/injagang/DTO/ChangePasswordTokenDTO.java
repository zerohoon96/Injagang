package com.SpringIsComing.injagang.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordTokenDTO {


    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String passwordCheck;

}

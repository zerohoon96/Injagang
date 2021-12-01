package com.SpringIsComing.injagang.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordTokenDTO {


    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}"
            ,message = "숫자, 영어, 특수기호 포함 8~20자입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String passwordCheck;

}

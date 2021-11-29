package com.SpringIsComing.injagang.DTO;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RegisterDTO {

    @NotEmpty(message = "아이디를 입력해주세요")
    private String loginId;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}"
            ,message = "비밀번호는 숫자, 영어, 특수기호 포함 8~20자입니다.")
    private String password;
    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String passwordCheck;
    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    @NotEmpty(message = "닉네임을 입력해주세요")
    @Min(value = 2,message = "닉네임은 2~10자 입니다.")
    @Max(value = 10,message = "닉네임은 2~10자 입니다.")
    private String nickname;
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;
}

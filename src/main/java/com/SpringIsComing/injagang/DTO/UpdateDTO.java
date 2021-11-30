package com.SpringIsComing.injagang.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateDTO {

    @NotEmpty(message = "현재 비밀번호를 입력해주세요")
    private String curPassword;

    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}"
            ,message = "숫자, 영어, 특수기호 포함 8~20자입니다.")
    private String newPassword;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String passwordCheck;

    private String name;

    @NotEmpty(message = "닉네임을 입력해주세요")
    @Size(max = 15,min = 2,message = "닉네임은 2~15자 입니다.")
    private String nickname;

    private String email;
}

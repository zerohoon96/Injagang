package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.ChangePasswordTokenDTO;
import com.SpringIsComing.injagang.DTO.FindDTO;
import com.SpringIsComing.injagang.DTO.LoginDTO;
import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Friend;
import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Service.*;
import com.SpringIsComing.injagang.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final EssayService essayService;
    private final InterviewService interviewService;
    private final FriendService friendService;
    private final AuthTokenService authTokenService;

    /**
     * 마이페이지
     */
    @GetMapping("/mypage/{nickname}")
    public String myPage(@SessionAttribute("loginSession") String nickname, @PathVariable("nickname") String curNickname
                            ,Model model) {


        Member loginMember = memberService.findByNickname(nickname);
        Member targetMember = memberService.findByNickname(curNickname);

        boolean friendCheck = friendService.isFriend(loginMember.getId(), targetMember.getId());

        if (nickname == curNickname) {
            friendCheck = true;
        }

        model.addAttribute("essayList",essayService.findEssays(loginMember).stream()
                                    .map(e -> essayService.toMypageEssayDTO(e)).collect(Collectors.toList()));
        model.addAttribute("interviewList",interviewService.findInterviews(loginMember).stream()
                .map(i -> interviewService.toMypageInterViewDTO(i)).collect(Collectors.toList()));
        model.addAttribute("friendList",friendService.findFriends(loginMember.getId()).stream()
                .map(f -> friendService.toMypageDTO(f,"쓰레기")).collect(Collectors.toList()));

        if (friendCheck) {
            model.addAttribute("isFriend", true);

            return "mypage/mypage";
        }

        model.addAttribute("isFriend", false);

        return "mypage/mypage";
    }

    /**
     * 마이페이지에서 수정 버튼 눌렀을때
     * */
    @GetMapping("/mypage/update/{memberId}")
    public void memberUpdateStart() {

    }

    /**
     * 회원 정보 수정 화면에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/mypage/update/{memberId}")
    public void memberUpdateEnd() {
        //입력 확인 후 객체 변경
    }

    /**
     * login 화면
     **/
    @GetMapping("/login")
    public String loginStart(Member member) {

        return "mypage/login";

    }

    /**
     * login 화면에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/login")
    public String loginEnd(@Valid @ModelAttribute("member")LoginDTO loginDTO, BindingResult bindingResult
                            , HttpServletRequest request
                           ) {
        //입력 확인 후 마이페이지로 이동

        if (bindingResult.hasErrors()) {
            return "mypage/login";
        }

        Member loginMember = memberService.login(loginDTO.getLoginId(), loginDTO.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디나 비밀번호를 다시 확인해주세요");
            return "mypage/login";
        }

        if (!loginMember.isAuth()) {
            bindingResult.reject("notAuth","인증되지 않은 계정입니다.");
            return "mypage/login";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_SESSION, loginMember.getNickname());



        return "redirect:/mypage";

    }

    /**
     * 회원가입 화면
     * */
    @GetMapping("/register")
    public String registerStart(@ModelAttribute("member")RegisterDTO registerDTO) {

        return "mypage/reg";
    }

    /**
     * 회원가입에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/register")
    public String registerEnd(@Valid @ModelAttribute("member")RegisterDTO registerDTO, BindingResult bindingResult
                                ,HttpServletRequest request
    ) {
        //입력 확인 후 객체 추가

        if (bindingResult.hasErrors()) {
            return "mypage/reg";
        }

        if(!registerDTO.getPassword().equals(registerDTO.getPasswordCheck())){
            bindingResult.rejectValue("password","passwordCheck","비밀번호가 다릅니다.");
            return "mypage/reg";
        }

        if (!memberService.loginDuplicateCheck(registerDTO.getLoginId())) {
            bindingResult.rejectValue("loginId","duplicateLoginId","아이디가 중복됩니다.");
        }

        if (memberService.nicknameDuplicateCheck(registerDTO.getNickname())) {
            bindingResult.rejectValue("nickname","duplicateNickname","닉네임이 중복됩니다.");
        }

        if (memberService.emailDuplicateCheck(registerDTO.getEmail())) {
            bindingResult.rejectValue("email","duplicateEmail","이메일이 중복됩니다.");
        }

        Long savedId = memberService.save(registerDTO);

        authTokenService.createEmailAuthToken(registerDTO.getLoginId(), registerDTO.getEmail());

//        HttpSession session = request.getSession();
//
//        session.setAttribute(SessionConst.LOGIN_SESSION, savedId);

        return "redirect:/auth/complete";

    }


    @GetMapping("/find")
    public String findStart(@ModelAttribute("findEmail") FindDTO findDTO){

        return "mypage/findIdAndPassword";
    }

    @PostMapping("/find")
    public String findEnd(@Valid @ModelAttribute("findEmail") FindDTO findDTO, BindingResult bindingResult) {

        Member find = memberService.findByEmail(findDTO.getEmail());


        if (find == null) {
            bindingResult.reject("notFindEmail","등록된 이메일이 아닙니다.");
            return "mypage/findIdAndPassword";

        }

        authTokenService.findEmailAuthToken(find.getLoginId(), find.getEmail());

        return "redirect:/auth/complete";

    }

    @GetMapping("/change/password")
    public String changePasswordStart(@Valid @RequestParam String token,
                                      @ModelAttribute("passwords") ChangePasswordTokenDTO changePasswordTokenDTO){

        return "mypage/changePasswordToken";

    }

    @PostMapping("/change/password")
    public String changePasswordEnd(@Valid @ModelAttribute("passwords") ChangePasswordTokenDTO changePasswordTokenDTO
                                    , BindingResult bindingResult, @RequestParam String token) {


        if (bindingResult.hasErrors()) {
            return "mypage/changePasswordToken";
        }

        if (!changePasswordTokenDTO.getPassword().equals(changePasswordTokenDTO.getPasswordCheck())) {
            bindingResult.rejectValue("password","passwordCheck","비밀번호가 다릅니다.");
            return "mypage/changePasswordToken";
        }

        Member findMember = memberService.confirmEmailForPassword(token);
        memberService.changePassword(findMember.getId(), changePasswordTokenDTO.getPassword());

        return "redirect:/login";

    }

    @GetMapping("/auth/complete")
    public String authComplete(){

        return "mypage/mail";
    }


    @PostConstruct
    public void init(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("@naver.com");//자신의 이메일을 넣어서 테스트해보세욤
        registerDTO.setName("황재환");
        registerDTO.setLoginId("zxcv0069");
        registerDTO.setNickname("test");
        registerDTO.setPassword("test");
        registerDTO.setPasswordCheck("test");

        memberService.save(registerDTO);

    }



}